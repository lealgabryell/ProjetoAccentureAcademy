package acc.br.petiscai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data //Getter, Setter, EqualsAndHashCode e ToString
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @OneToOne(cascade = {CascadeType.PERSIST}) //Ao criar uma referencia no estoque, se nao existir um produto desse o
    // software cria-o e salva na tabela de produtos
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;
}
