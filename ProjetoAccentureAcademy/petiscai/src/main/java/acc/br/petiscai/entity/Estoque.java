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

}
