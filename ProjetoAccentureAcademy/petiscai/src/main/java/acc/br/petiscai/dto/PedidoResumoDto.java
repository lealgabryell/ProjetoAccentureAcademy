package acc.br.petiscai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResumoDto {
    private Long idPedido;
    private String nomeCliente;
    private BigDecimal subtotal;
    private String pagamento;

}
