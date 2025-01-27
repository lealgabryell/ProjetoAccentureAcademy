package acc.br.petiscai.entity;

import acc.br.petiscai.dto.ClienteDto;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Primary;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private int idade;

    public Cliente(String cpf, String nome, String email, String telefone, String endereco, int idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.idade = idade;
    }

    public Cliente(ClienteDto clienteDto) {
        this.cpf = clienteDto.getCpf();
        this.nome = clienteDto.getNome();
        this.email = clienteDto.getEmail();
        this.telefone = clienteDto.getTelefone();
        this.endereco = clienteDto.getEndereco();
        this.idade = clienteDto.getIdade();
    }
    public Cliente(int id, ClienteDto clienteDto) {
        this.id = id;
        this.cpf = clienteDto.getCpf();
        this.nome = clienteDto.getNome();
        this.email = clienteDto.getEmail();
        this.telefone = clienteDto.getTelefone();
        this.endereco = clienteDto.getEndereco();
        this.idade = clienteDto.getIdade();
    }


}
