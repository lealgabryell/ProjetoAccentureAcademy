package acc.br.petiscai.controller;

import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.createProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {
        return produtoService.getProdutoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.updateProduto(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Integer id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}