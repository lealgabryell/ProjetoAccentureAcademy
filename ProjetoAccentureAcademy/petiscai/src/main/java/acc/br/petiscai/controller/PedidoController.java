package acc.br.petiscai.controller;

import acc.br.petiscai.dto.PedidoDto;
import acc.br.petiscai.entity.Pedido;
import acc.br.petiscai.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation; // Caso esteja usando SpringDoc para Swagger
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Cria um novo pedido para um cliente")
    @PostMapping("/create")
    public ResponseEntity<String> criarPedido(@RequestBody PedidoDto pedidoDto) {
        String resultado = pedidoService.criarPedido(pedidoDto);
        if(resultado.startsWith("Pedido criado com sucesso")) {
            return ResponseEntity.ok(resultado);
        } else {
            // Aqui você poderia lidar com diferentes códigos de status
            return ResponseEntity.badRequest().body(resultado);
        }
    }

    @Operation(summary = "Busca um pedido pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if(pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedido);
    }

    @Operation(summary = "Lista todos os pedidos")
    @GetMapping("/findAll")
    public ResponseEntity<List<Pedido>> buscarTodos() {
        List<Pedido> lista = pedidoService.buscarTodos();
        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
}
