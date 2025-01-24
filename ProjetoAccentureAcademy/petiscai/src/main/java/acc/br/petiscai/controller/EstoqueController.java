package acc.br.petiscai.controller;

import acc.br.petiscai.dto.ProdutoDto;
import acc.br.petiscai.entity.Estoque;
import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.service.EstoqueService;
import acc.br.petiscai.service.ProdutoService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    String c = "";
    @Autowired
    ProdutoService produtoService;
    @Autowired
    EstoqueService estoqueService;


    @PutMapping("/saida/{id}")
    public ResponseEntity<String> saida(@PathVariable List<Long> id) {

        for (Long i : id) {
            Produto produto = this.produtoService.findById(i);
            c = this.estoqueService.saida(produto);
        }

        if(c.contains("Estoque atualizado devido a venda realizada.") || c.contains("Devido ao estoque zerado, o produto saiu do nosso banco de dados!")) {
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/entrada/{id}")
    public ResponseEntity<String> entrada(@PathVariable List<Long> id) {

        for (Long i : id) {
            Produto produto = this.produtoService.findById(i);
            c = this.estoqueService.entrada(produto);
        }

        if(c.contains("Estoque de" + id.size() + " atualizado devido a compra realizada.")) {
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> lista = this.estoqueService.findAll();
        if(lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

}
