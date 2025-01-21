package acc.br.petiscai.repository;

import acc.br.petiscai.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepoitory extends JpaRepository<Pedido, Long> {
}
