package acc.br.petiscai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;
    private BigDecimal subtotal;

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, BigDecimal subtotal) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }
}

