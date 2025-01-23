package acc.br.petiscai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import acc.br.petiscai.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    //Faz a pesquisa de categorias por ordem alfabética
    public List<Categoria> findAllByOrderByNomeAsc();
}
