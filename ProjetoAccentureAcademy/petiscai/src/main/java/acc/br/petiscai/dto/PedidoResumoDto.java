package acc.br.petiscai.dto;

import java.math.BigDecimal;

public class PedidoResumoDto {
    private Long idPedido;
    private String nomeCliente;
    private BigDecimal subtotal;

    // Construtor com parâmetros
    public PedidoResumoDto(Long idPedido, String nomeCliente, BigDecimal subtotal) {
        this.idPedido = idPedido;
        this.nomeCliente = nomeCliente;
        this.subtotal = subtotal;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    // Getters
    public String getNomeCliente() {
        return nomeCliente;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
