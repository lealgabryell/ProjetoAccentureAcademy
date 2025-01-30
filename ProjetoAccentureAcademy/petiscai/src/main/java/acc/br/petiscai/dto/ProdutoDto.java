package acc.br.petiscai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoDto {
    private String nome;
    private String tipo;
    private Double preco;
    private Integer quantidade;

    
}
