package acc.br.petiscai.repository;

import acc.br.petiscai.dto.ClienteDto;
import acc.br.petiscai.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {

    public Cliente findByCpf(String cpf);
    // Verificar se existe um cliente com o cpf informado
    public boolean existsByCpf(String cpf);
    // Verificar se existe um cliente com o email informado
    public boolean existsByEmail(String email);
}
