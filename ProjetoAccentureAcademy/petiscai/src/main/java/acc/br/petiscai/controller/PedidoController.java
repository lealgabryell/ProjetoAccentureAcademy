package acc.br.petiscai.controller;

import acc.br.petiscai.dto.PedidoDto;
import acc.br.petiscai.dto.PedidoResumoDto;
import acc.br.petiscai.entity.Pedido;
import acc.br.petiscai.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation; // Caso esteja usando SpringDoc para Swagger
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
}
