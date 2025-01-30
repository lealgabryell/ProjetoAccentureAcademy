package acc.br.petiscai.entity;

import acc.br.petiscai.dto.ClienteDto;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;
    private String endereco;

    @Column(nullable = false)
    private int idade;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean admin = false;



    public Cliente(ClienteDto clienteDto) {
        this.cpf = clienteDto.getCpf();
        this.nome = clienteDto.getNome();
        this.email = clienteDto.getEmail();
        this.telefone = clienteDto.getTelefone();
        this.endereco = clienteDto.getEndereco();
        this.idade = clienteDto.getIdade();
        this.password = clienteDto.getPassword();
    }



    public void setPassword(String password) {
        // Garante que a senha será sempre criptografada
        if (!password.startsWith("$2a$")) {
            this.password = new BCryptPasswordEncoder().encode(password);
        } else {
            this.password = password;
        }
    }


}
