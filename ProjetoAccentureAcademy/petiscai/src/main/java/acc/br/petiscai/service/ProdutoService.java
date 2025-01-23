package acc.br.petiscai.service;

import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto createProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> getProdutoById(Integer id) {
        return produtoRepository.findById(id);
    }

    public Produto updateProduto(Integer id, Produto produto) {
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public void deleteProduto(Integer id) {
        produtoRepository.deleteById(id);
    }
}