package acc.br.petiscai.controller;

import acc.br.petiscai.dto.PedidoDto;
import acc.br.petiscai.dto.PedidoResumoDto;
import acc.br.petiscai.entity.ItemPedido;
import acc.br.petiscai.entity.Pedido;
import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.enums.StatusPedido;
import acc.br.petiscai.repository.PedidoRepository;
import acc.br.petiscai.repository.ProdutoRepository;
import acc.br.petiscai.service.PedidoService;
import org.springframework.ui.Model;
import io.swagger.v3.oas.annotations.Operation; // Caso esteja usando SpringDoc para Swagger

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/adicionar-produto")
    @PreAuthorize("hasRole('USER')")
    public String adicionarProdutoAoPedido(
            @RequestParam Long produtoId,
            @RequestParam int quantidade,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        try {
            String emailCliente = userDetails.getUsername(); // Obtém o email do cliente logado
            pedidoService.adicionarProdutoAoPedido(emailCliente, produtoId, quantidade);


        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
            return "erro"; // Página de erro (opcional)
        }
        return "/api/pedido"; 
    }

    @Operation(summary = "Cria um novo pedido para um cliente")
    @PostMapping("/create")
    public ResponseEntity<String> criarPedido(@RequestBody PedidoDto pedidoDto) {
        String resultado = pedidoService.criarPedido(pedidoDto);
        if (resultado.contains("Pedido criado com sucesso")) {
            return new ResponseEntity<>(resultado, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um pedido pelo ID")
    @GetMapping("/findById/{id}")
    public ResponseEntity<PedidoResumoDto> findById(@PathVariable Long id) {
        PedidoResumoDto pedido = pedidoService.findById(id);
        if (pedido == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        }
    }

    @Operation(summary = "Lista todos os pedidos")
    @GetMapping("/findAll")
    public ResponseEntity<List<PedidoResumoDto>> buscarTodos() {
        List<PedidoResumoDto> lista = pedidoService.buscarResumoPedidos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String verPedido(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String emailCliente = userDetails.getUsername();
        Pedido pedido = pedidoService.buscarPedidoAbertoPorCliente(emailCliente);
        model.addAttribute("pedido", pedido);
        return "meu-pedido"; 
    }
    
    @PostMapping("/pagar")
    @PreAuthorize("hasRole('USER')")
    public String pagarPedido(@AuthenticationPrincipal UserDetails userDetails) {
        String emailCliente = userDetails.getUsername(); // Obtém o e-mail do cliente autenticado

        try {
            pedidoService.pagarPedido(emailCliente); // Chama o serviço para processar o pagamento
            return "redirect:/meu-pedido?status=pago"; // Redireciona com status "pago"
        } catch (Exception e) {
            e.printStackTrace(); // Loga a exceção para depuração
            return "redirect:/meu-pedido?status=erro"; // Redireciona em caso de erro
        }
    }

    @PostMapping("/cancelar")
@PreAuthorize("hasRole('USER')")
public String cancelarPedido(@AuthenticationPrincipal UserDetails userDetails) {
    String emailCliente = userDetails.getUsername(); // Obtém o e-mail do cliente autenticado

    // Busca o pedido ativo do cliente
    Pedido pedido = pedidoRepository.findByClienteEmailAndStatus(emailCliente, StatusPedido.ABERTO)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou já finalizado"));

    // Itera sobre os itens do pedido e devolve as quantidades ao estoque
    for (ItemPedido item : pedido.getItens()) {
        Produto produto = item.getProduto();
        produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
        produtoRepository.save(produto); // Atualiza o estoque no banco de dados
    }

    // Atualiza o status do pedido para "CANCELADO"
    pedido.setStatus(StatusPedido.CANCELADO);
    pedidoRepository.save(pedido);

    return "redirect:/meu-pedido?status=cancelado"; // Redireciona com status "cancelado"
}


}
