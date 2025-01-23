package acc.br.petiscai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import acc.br.petiscai.entity.Categoria;
import acc.br.petiscai.service.CategoriaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> recuperarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> adicionarNova(@RequestBody Categoria nova) {
        Categoria res = categoriaService.criarNova(nova);
        if (res != null) {
            return ResponseEntity.status(201).body(res);
        }
        return ResponseEntity.badRequest().build();
        
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categoria> alterarCategora(@RequestBody Categoria categoria, @PathVariable Integer id) {
        categoria.setId(id);
        Categoria res = categoriaService.alterar(categoria);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<?> apagarCategoria(@PathVariable Integer id) {
        Categoria res = categoriaService.apagarCategoria(id);
        if (res != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
