package acc.br.petiscai.service;


import acc.br.petiscai.entity.Produto;
import acc.br.petiscai.repository.EstoqueRepository;
import acc.br.petiscai.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    EstoqueRepository estoqueRepository;

    public String saida(Produto produto){
        try{
            produto.setQuantidade(produto.getQuantidade()-1);
                this.produtoRepository.save(produto);
                return "Estoque atualizado devido a venda realizada.";
        }catch(Exception e){
            return e.getMessage();
        }
    }
    public String entrada(Produto produto){
        try{
            produto.setQuantidade(produto.getQuantidade()+1);
            this.produtoRepository.save(produto);
            return "Estoque atualizado devido a compra realizada.";
        }catch(Exception e){
            return e.getMessage();
        }
    }
    public List<Produto> findAll(){
        try{
            return this.estoqueRepository.findProdutosDisponiveis();
        }catch(Exception e){
            return null;
        }
    }
}
