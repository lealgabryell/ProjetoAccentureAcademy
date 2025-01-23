package acc.br.petiscai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acc.br.petiscai.entity.Categoria;
import acc.br.petiscai.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria criarNova(Categoria nova) {
        return categoriaRepository.save(nova);
    }

    public Categoria alterar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAllByOrderByNomeAsc();
    }

    public Categoria apagarCategoria(Integer id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null) {
            categoriaRepository.delete(categoria);
        }
        return categoria;
    }

}
