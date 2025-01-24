package acc.br.petiscai.service;

import acc.br.petiscai.dto.ProdutoDto;
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

    public String save(ProdutoDto produtoDto) {
        try{
            Produto produto = new Produto(produtoDto);
            this.produtoRepository.save(produto);
            return "Produto salvo com sucesso!";
        }catch(Exception e){
            return e.getMessage();
        }
    }

    public List<Produto> findAll() {
        try {
            return produtoRepository.findAll();
        }catch(Exception e){
            return null;
        }
    }

    public Produto findById(Long id) {
        try {
            return produtoRepository.findById(id).get();
        }catch(Exception e){
            return null;
        }
    }

    public String update(Long id, ProdutoDto produtoDto) {
        if(checaId(id)) {
            try {
                Produto produto = new Produto(produtoDto);
                produto.setId(id);
                this.produtoRepository.save(produto);
                return "Produto atualizado com sucesso!";
            } catch (Exception e) {
                return e.getMessage();
            }
        }else{
            return "Id não existente";
        }
    }

    public boolean checaId(Long id) { //verifica se existe o Id passado pelo usuario
        return this.produtoRepository.findById(id).isPresent();
    }

    public String delete(Long id) {
        if(checaId(id)) {
            try {
                this.produtoRepository.deleteById(id);
                return "Produto deletado com sucesso!";
            }catch(Exception e){
                return e.getMessage();
            }
        }else{
            return "Id não existente!";
        }
    }
}