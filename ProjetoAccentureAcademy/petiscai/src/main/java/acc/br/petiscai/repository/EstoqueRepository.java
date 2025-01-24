package acc.br.petiscai.repository;

import acc.br.petiscai.entity.Estoque;
import acc.br.petiscai.entity.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    @Query("SELECT e.produto FROM Estoque e WHERE e.disponivel = true")
    List<Produto> findProdutosDisponiveis();

}
