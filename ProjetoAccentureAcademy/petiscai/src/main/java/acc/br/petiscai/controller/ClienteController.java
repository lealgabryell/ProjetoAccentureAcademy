package acc.br.petiscai.controller;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Operation(summary = "Cria um novo cliente")
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ClienteDto clienteDto) {
        return this.clienteService.save(clienteDto);
    }

    @Operation(summary = "Altera informacoes de um cliente")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody ClienteDto clienteDto, @PathVariable Long id) {
        return this.clienteService.update(clienteDto, id);
    }

    @Operation(summary = "Exclui um cliente")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        String c = this.clienteService.deleteById(id);

        if (c.contains("Cliente deletado com sucesso!")) {
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        } else if (c.contains("Cliente não encontrado")) {
            return new ResponseEntity<>(c, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Encontra todos os clientes salvos")
    @GetMapping("/findAll")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> lista = this.clienteService.findAll();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @Operation(summary = "Encontra um cliente pelo seu Id")
    @GetMapping("/findById/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable long id) {
        Cliente c = this.clienteService.findById(id);

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(c, HttpStatus.OK);
        }

    }

}
