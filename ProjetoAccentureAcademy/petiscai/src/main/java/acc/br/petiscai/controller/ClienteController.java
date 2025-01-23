package acc.br.petiscai.controller;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import acc.br.petiscai.service.ClienteService;
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

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ClienteDto clienteDto) {
        String c = this.clienteService.save(clienteDto);

        if (c.contains("Cliente salvo com sucesso!")) {
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        } else if (c.contains("Cliente menor de idade!")) {
            return new ResponseEntity<>(c, HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id) {
        String c = this.clienteService.update(clienteDto, id);

        if (c.contains("Cliente" + id + " alterado com sucesso!")) {
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        } else if (c.contains("Cliente não encontrado")) {
            return new ResponseEntity<>(c, HttpStatus.NOT_FOUND);
        }else if(c.contains("Cliente menor de idade!")){
            return new ResponseEntity<>(c, HttpStatus.NOT_ACCEPTABLE);
        }else {
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }

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

    @GetMapping("/findAll")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> lista = this.clienteService.findAll();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

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
