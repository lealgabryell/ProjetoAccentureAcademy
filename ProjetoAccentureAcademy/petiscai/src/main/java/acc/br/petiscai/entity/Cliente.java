package acc.br.petiscai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Primary;

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

    public Cliente(long id, String cpf, String nome, String email, String telefone, String endereco, int idade) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.idade = idade;
    }

    public Cliente() {
    }

    public Cliente(String cpf, String nome, String email, String telefone, String endereco, int idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.idade = idade;
    }

    public long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }


    public String getNome() {
        return nome;
    }


    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getIdade() {
        return idade;
    }


}
