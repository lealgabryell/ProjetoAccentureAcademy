package acc.br.petiscai.controller;


import acc.br.petiscai.dto.ProdutoDto;
import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.service.ProdutoService;
import org.springframework.ui.Model;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produtos")  
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Encontra todos os produtos")
    @GetMapping
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "produtos";
    }

    @Operation(summary = "Encontra um produto pelo seu Id")
    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
    Produto produto = produtoService.findById(id);
    if (produto == null) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
    return new ResponseEntity<>(produto, HttpStatus.OK);
    }
    }
    
    @Operation(summary = "Cria um novo produto")
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@ModelAttribute ProdutoDto produtoDto,
            RedirectAttributes redirectAttributes) {
        String resultado = produtoService.save(produtoDto);

        if (resultado.contains("Produto salvo com sucesso!")) {
            redirectAttributes.addFlashAttribute("success", resultado);
        } else {
            redirectAttributes.addFlashAttribute("error", resultado);
        }
        return "redirect:/produtos";
    }

    @Operation(summary = "Delet um produto passando seu Id")
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String resultado = produtoService.delete(id);
        redirectAttributes.addFlashAttribute("mensagem", resultado);
        return "redirect:/produtos";
    }
    
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Produto produto = produtoService.findById(id);
        if (produto == null) {
            return "redirect:/produtos";
        }
        model.addAttribute("produto", produto);
        return "editarProduto";
    }

    @Operation(summary = "Altera informacoes de produto passando seu id")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id, @ModelAttribute ProdutoDto produtoDto,
            RedirectAttributes redirectAttributes) {
        String resultado = produtoService.update(id, produtoDto);
        redirectAttributes.addFlashAttribute("mensagem", resultado);
        return "redirect:/produtos";
    }
}




//     @Operation(summary = "Encontra todos os produtos")
//     @GetMapping
//     public String listarProdutos(Model model) {
//         List<Produto> produtos = produtoService.findAll();
//         model.addAttribute("produtos", produtos);
//         return "produtos";
//     }
//     public ResponseEntity<List<Produto>> findAll() {
//         List<Produto> produtos = produtoService.findAll();
//         if (produtos == null || produtos.isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         } else {
//             return new ResponseEntity<>(produtos, HttpStatus.OK);
//         }
//     }


