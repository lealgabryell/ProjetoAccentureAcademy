package acc.br.petiscai.repository;

import acc.br.petiscai.dto.PedidoResumoDto;
import acc.br.petiscai.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p.cliente.nome " +
            "FROM Pedido p " +
            "JOIN p.itens ip " +
            "GROUP BY p.cliente.nome")
    List<String> findClienteNome();

    @Query("SELECT p.id " +
            "FROM Pedido p " +
            "JOIN p.itens ip " +
            "WHERE p.cliente.nome = :nomeCliente " +  // Filtrando pelo nome do cliente
            "GROUP BY p.id")
    List<Long> findClienteIdByNome(@Param("nomeCliente") String nomeCliente);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.nome = :nomeCliente")
    List<Pedido> findByClienteNome(String nomeCliente);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :idCliente")
    List<Pedido> findByClienteId(Long idCliente);

    @Query("SELECT p.pagamento.status FROM Pedido p WHERE p.id = :idPedido")
    boolean getStatusPagamento(Long idPedido);

}
