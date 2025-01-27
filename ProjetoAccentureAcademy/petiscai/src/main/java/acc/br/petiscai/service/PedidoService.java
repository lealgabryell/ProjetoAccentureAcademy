package acc.br.petiscai.service;

import acc.br.petiscai.dto.PedidoDto;
import acc.br.petiscai.dto.ItemPedidoDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.entity.ItemPedido;
import acc.br.petiscai.entity.Pedido;
import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.repository.ClienteRepository;
import acc.br.petiscai.repository.PedidoRepository;
import acc.br.petiscai.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
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
    private EstoqueService estoqueService;

    @Transactional
    public String criarPedido(PedidoDto pedidoDto) {

        // 1) Buscar cliente
        Optional<Cliente> optCliente = clienteRepository.findById(pedidoDto.getClienteId());
        if (optCliente.isEmpty()) {
            return "Cliente não encontrado.";
        }
        Cliente cliente = optCliente.get();

        // 2) Criar objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItens(new ArrayList<>());
        BigDecimal total = BigDecimal.ZERO;

        // 3) Validar itens e montar o pedido
        for (ItemPedidoDto item : pedidoDto.getItens()) {
            Optional<Produto> optProduto = produtoRepository.findById(item.getProdutoId());
            if (optProduto.isEmpty()) {
                return "Produto não encontrado: " + item.getProdutoId();
            }
            Produto produto = optProduto.get();

            // Verificar se é bebida alcoólica e cliente é menor de idade
            if (ehBebidaAlcoolica(produto) && cliente.getIdade() < 18) {
                return "Cliente menor de idade não pode comprar bebidas alcoólicas.";
            }

            // Verificar estoque
            if (produto.getQuantidade() < item.getQuantidade()) {
                return "Estoque insuficiente para o produto: " + produto.getNome();
            }

            // Atualizar estoque
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoRepository.save(produto);

            // Calcular subtotal
            BigDecimal subtotal = BigDecimal.valueOf(produto.getPreco())
                    .multiply(BigDecimal.valueOf(item.getQuantidade()));

            // Montar ItemPedido
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setSubtotal(subtotal);
            itemPedido.setPedido(pedido);

            // Adicionar ao pedido
            pedido.getItens().add(itemPedido);

            // Incrementar total
            total = total.add(subtotal);
        }

        // 4) Setar total e salvar pedido
        pedido.setTotal(total);
        pedidoRepository.save(pedido); // Devido ao cascade, itens também serão salvos

        return "Pedido criado com sucesso! ID: " + pedido.getId() + " | Total: " + total;
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    private boolean ehBebidaAlcoolica(Produto produto) {
        return produto.getTipo() != null && produto.getTipo().toLowerCase().contains("alco");
    }
}

