package acc.br.petiscai.controller;

import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.service.EstoqueService;
import acc.br.petiscai.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EstoqueService estoqueService;


    @PutMapping("/saida")
    public ResponseEntity<String> saida(@RequestBody Long id) {
        String resposta = "";
            Produto produto = produtoService.findById(id);
            if (produto == null) {
                resposta = "Produto com ID " + id + " não encontrado!";
                return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
            }
            resposta = estoqueService.saida(produto);
            // Se quiser tratar cada produto individualmente, pode acumular as respostas.

        // Verifica a última mensagem retornada pelo service
        if (resposta.contains("Estoque atualizado devido a venda realizada.") 
         || resposta.contains("Devido ao estoque zerado, o produto saiu do nosso banco de dados!")) {
            return new ResponseEntity<>(resposta, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/entrada")
    public ResponseEntity<String> entrada(@RequestBody List<Long> ids) {
        String resposta = "";
        for (Long id : ids) {
            Produto produto = produtoService.findById(id);
            if (produto == null) {
                resposta = "Produto com ID " + id + " não encontrado!";
                return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
            }
            resposta = estoqueService.entrada(produto);
        }
        if (resposta.contains("Estoque atualizado devido a compra realizada.")) {
            return new ResponseEntity<>(resposta, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
        }
    }


}
