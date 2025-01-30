package acc.br.petiscai.service;

import acc.br.petiscai.dto.PedidoDto;
import acc.br.petiscai.dto.ItemPedidoDto;
import acc.br.petiscai.dto.PedidoResumoDto;
import acc.br.petiscai.entity.*;
import acc.br.petiscai.repository.ClienteRepository;
import acc.br.petiscai.repository.PagamentoRepository;
import acc.br.petiscai.repository.PedidoRepository;
import acc.br.petiscai.repository.ProdutoRepository;
import acc.br.petiscai.enums.StatusPedido;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public void adicionarProdutoAoPedido(String emailCliente, Long produtoId, int quantidade) throws Exception {
        // Buscar o cliente pelo e-mail
        Cliente cliente = clienteRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new Exception("Cliente não encontrado com o e-mail: " + emailCliente));

        // Buscar o pedido ativo do cliente (ou criar um novo)
        Pedido pedido = pedidoRepository.findByClienteIdAndStatus(cliente.getId(), StatusPedido.ABERTO)
                .orElseGet(() -> criarNovoPedido(cliente));

        // Buscar o produto pelo ID
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new Exception("Produto não encontrado"));

        // Verificar estoque
        if (produto.getQuantidade() < quantidade) {
            throw new Exception("Estoque insuficiente para o produto: " + produto.getNome());
        }

        // Calcular subtotal do item
        BigDecimal subtotal = BigDecimal.valueOf(produto.getPreco()).multiply(BigDecimal.valueOf(quantidade));
        pedido.setTotal(pedido.getTotal().add(subtotal));

        // Criar e adicionar o item ao pedido
        ItemPedido itemPedido = new ItemPedido(pedido, produto, quantidade, subtotal);
        pedido.getItens().add(itemPedido);

        // Atualizar estoque do produto
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produtoRepository.save(produto);

        // Salvar o pedido atualizado (devido ao cascade, os itens também serão salvos)
        pedidoRepository.save(pedido);

    }

    private Pedido criarNovoPedido(Cliente cliente) {
        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente); // Associa o cliente ao pedido
        novoPedido.setStatus(StatusPedido.ABERTO);
        novoPedido.setItens(new ArrayList<>());
        novoPedido.setTotal(BigDecimal.ZERO);
        return pedidoRepository.save(novoPedido); // Salva o pedido no banco
    }

    @Transactional
    public String criarPedido(PedidoDto pedidoDto) {

        Optional<Cliente> optCliente = clienteRepository.findById(pedidoDto.getClienteId()); // 1) Buscar cliente
        if (optCliente.isEmpty()) {
            return "Cliente não encontrado.";
        } else {
            Cliente cliente = optCliente.get();

            Pedido pedido = new Pedido(); // 2) Criar objeto Pedido
            pedido.setCliente(cliente);
            pedido.setItens(new ArrayList<>());
            BigDecimal total = BigDecimal.ZERO;

            for (ItemPedidoDto item : pedidoDto.getItens()) { // 3) Validar itens e montar o pedido
                Optional<Produto> optProduto = produtoRepository.findById(item.getProdutoId());
                if (optProduto.isEmpty()) {
                    return "Produto não encontrado: " + item.getProdutoId();
                }
                Produto produto = optProduto.get();

                if (produto.getQuantidade() < item.getQuantidade()) { // Verificar estoque
                    return "Estoque insuficiente para o produto: " + produto.getNome();
                }

                produto.setQuantidade(produto.getQuantidade() - item.getQuantidade()); // Atualizar estoque
                produtoRepository.save(produto);

                BigDecimal subtotal = BigDecimal.valueOf(produto.getPreco()) // Calcular subtotal
                        .multiply(BigDecimal.valueOf(item.getQuantidade()));

                ItemPedido itemPedido = new ItemPedido(); // Montar ItemPedido
                itemPedido.setProduto(produto);
                itemPedido.setQuantidade(item.getQuantidade());
                itemPedido.setSubtotal(subtotal);
                itemPedido.setPedido(pedido);

                pedido.getItens().add(itemPedido); // Adicionar ao pedido

                total = total.add(subtotal); // Incrementar total
            }

            Pagamento pagamento = new Pagamento(); // Pagamento inicializado, sempre com status FALSE

            pedido.setTotal(total); // 4) Setar total e salvar pedido
            pedido.setPagamento(pagamento); // 5) Setar status do Pagamento no Pedido
            pagamento.setPedido(pedido); // 6) Setar Pedido no objeto Pagamento

            this.pagamentoRepository.save(pagamento);// Salva pagamento no bd
            this.pedidoRepository.save(pedido); // Devido ao cascade, itens também serão salvos

            return "Pedido criado com sucesso! ID: " + pedido.getId() + " | Total: " + total
                    + "\n Aguardando pagamento...";
        }
    }

    // public PedidoResumoDto findById(Long id) {
    //     try {
    //         Pedido pedido = this.pedidoRepository.findById(id).get();
    //         Long idPedido = pedido.getId();
    //         String nomeCliente = pedido.getCliente().getNome();
    //         BigDecimal subtotal = calcularSubtotalPorPedido(idPedido);
    //         String pagamento = "Aguardando pagamento...";
    //         if (this.pedidoRepository.getStatusPagamento(idPedido)) {
    //             pagamento = "Pago";
    //         }
    //         return new PedidoResumoDto(idPedido, nomeCliente, subtotal, pagamento);
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }

    public PedidoResumoDto findById(Long id) {
        try {
            Pedido pedido = pedidoRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Pedido não encontrado."));

            boolean statusPagamento = pedidoRepository.getStatusPagamento(id); 
            String pagamento = statusPagamento ? "Pago" : "Aguardando pagamento...";

            BigDecimal subtotal = calcularSubtotalPorPedido(id);

            return new PedidoResumoDto(pedido.getId(), pedido.getCliente().getNome(), subtotal, pagamento);
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro para depuração
            return null;
        }
    }

    public List<PedidoResumoDto> buscarResumoPedidos() { // organizado por: cliente que fez o primeiro pedido pela
                                                         // primeira vez

        List<PedidoResumoDto> pedidosResumo = new ArrayList<>();

        try {
            List<String> listaClientes = pedidoRepository.findClienteNome(); // encontra o nome dos clientes que tem
                                                                             // pedido registrado
            for (String nomeCliente : listaClientes) {
                List<Long> listaIdPedido = pedidoRepository.findClienteIdByNome(nomeCliente); // encontra o id do
                                                                                              // cliente
                for (Long idPedido : listaIdPedido) {
                    String pagamento = "";
                    if (this.pedidoRepository.getStatusPagamento(idPedido)) {
                        pagamento = "Pago";
                    } else {
                        pagamento = "Aguardando pagamento...";
                    }
                    BigDecimal subtotal = calcularSubtotalPorPedido(idPedido); // calcula o subtotal a partir do nome
                                                                               // encontrado
                    pedidosResumo.add(new PedidoResumoDto(idPedido, nomeCliente, subtotal, pagamento));
                }
            }
            return pedidosResumo;
        } catch (Exception e) {
            return null;
        }
    }


    private BigDecimal calcularSubtotalPorPedido(Long pedidoId) {

        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId); // Buscar o pedido pelo ID

        if (pedidoOptional.isEmpty()) { // Se o pedido não for encontrado, retorna zero
            return BigDecimal.ZERO;
        }

        Pedido pedido = pedidoOptional.get();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemPedido item : pedido.getItens()) {

            BigDecimal precoUnitario = BigDecimal.valueOf(item.getProduto().getPreco());// Convertendo o preço de Double
                                                                                        // para BigDecimal

            BigDecimal quantidade = new BigDecimal(item.getQuantidade());// Convertendo a quantidade de Integer para
                                                                         // BigDecimal

            BigDecimal itemSubtotal = precoUnitario.multiply(quantidade);

            subtotal = subtotal.add(itemSubtotal);
        }

        return subtotal;
    }

    private List<Pedido> buscarPedidosPorCliente(String nomeCliente) {
        return pedidoRepository.findByClienteNome(nomeCliente);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPedidoAbertoPorCliente(String emailCliente) {
        return pedidoRepository.findByClienteEmailAndStatus(emailCliente, StatusPedido.ABERTO)
                .orElseThrow(() -> new IllegalStateException("Nenhum pedido aberto encontrado para este cliente."));
    }

    @Transactional
    public void pagarPedido(String emailCliente) {
        Pedido pedido = buscarPedidoAbertoPorCliente(emailCliente); // Busca o pedido aberto do cliente

        if (pedido.getItens().isEmpty()) {
            throw new IllegalStateException("Não é possível pagar um pedido vazio.");
        }

        // Atualiza o status do pedido para "PAGO"
        pedido.setStatus(StatusPedido.PAGO);

        // Atualiza o status do pagamento associado ao pedido
        if (pedido.getPagamento() != null) {
            pedido.getPagamento().setStatus(true); // Define o pagamento como concluído
            pagamentoRepository.save(pedido.getPagamento()); // Salva no banco
        } else {
            throw new IllegalStateException("Nenhum pagamento associado ao pedido.");
        }

        pedidoRepository.save(pedido); // Salva o pedido atualizado no banco
    }

    @Transactional
    public void cancelarPedido(String emailCliente) {
        Pedido pedido = buscarPedidoAbertoPorCliente(emailCliente);
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido); // Salva as alterações no banco
    }

}
