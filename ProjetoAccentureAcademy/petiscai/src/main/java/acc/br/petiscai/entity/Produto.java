package acc.br.petiscai.entity;

import acc.br.petiscai.dto.ProdutoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private Double preco;
    @Column(nullable = true)
    private Integer quantidade;


    public Produto(ProdutoDto produtoDto) {
        this.nome = produtoDto.getNome();
        this.tipo = produtoDto.getTipo();
        this.preco = produtoDto.getPreco();
        this.quantidade = produtoDto.getQuantidade();
    }
}
