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
        if(clienteService.checaIdade(clienteDto)) {
            String c = this.clienteService.save(clienteDto);
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Cliente menor de idade", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {

        String c = this.clienteService.deleteById(id);
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> lista = this.clienteService.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
