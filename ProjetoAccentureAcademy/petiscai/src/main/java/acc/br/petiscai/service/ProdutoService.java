package acc.br.petiscai.service;

import acc.br.petiscai.dto.ProdutoDto;
import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public String save(ProdutoDto produtoDto) {
        try {
            Produto produto = new Produto(produtoDto);
            produtoRepository.save(produto);
            return "Produto salvo com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Produto> findAll() {
        try {
            return produtoRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Produto findById(Long id) {
        try {
            return produtoRepository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public String update(Long id, ProdutoDto produtoDto) {
        if (!checaId(id)) {
            return "Id não existente";
        }
        try {
            Produto produto = new Produto(produtoDto);
            produto.setId(id);
            produtoRepository.save(produto);
            return "Produto atualizado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public boolean checaId(Long id) {
        return produtoRepository.findById(id).isPresent();
    }

    public String delete(Long id) {
        if (!checaId(id)) {
            return "Id não existente!";
        }
        try {
            produtoRepository.deleteById(id);
            return "Produto deletado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
