package acc.br.petiscai.controller;

import acc.br.petiscai.dto.ProdutoDto;
import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ProdutoDto produtoDto) {
        String c = produtoService.save(produtoDto);
        if(c.contains("Produto salvo com sucesso!")){
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> produtos = produtoService.findAll();

        if(produtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Produto produto = this.produtoService.findById(id);

        if(produto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(produto, HttpStatus.OK);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ProdutoDto produtoDto) {
        String c = produtoService.update(id, produtoDto);

        if(c.contains("Produto atualizado com sucesso!")){
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }else if (c.contains("Id não existente")){
            return new ResponseEntity<>(c, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String c = this.produtoService.delete(id);

        if(c.contains("Produto deletado com sucesso!")){
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }else if(c.contains("Id não existente!")){
            return new ResponseEntity<>(c, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(c, HttpStatus.BAD_REQUEST);
        }
    }
}