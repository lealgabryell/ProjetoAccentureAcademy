package acc.br.petiscai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private int idade;
    private String password;
   
}
