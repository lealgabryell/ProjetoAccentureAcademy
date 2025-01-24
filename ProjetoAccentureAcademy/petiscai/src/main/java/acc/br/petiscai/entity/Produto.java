package acc.br.petiscai.entity;

import acc.br.petiscai.dto.ProdutoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToOne(mappedBy = "produto")
    @JsonIgnoreProperties("produto")
    private Estoque estoque;
    private String tipo;
    private Double preco;
    @Column(nullable = true)
    private Integer quantidade;

    public Produto() {}

    public Produto(String nome, String tipo, Double preco, Integer quantidade) {
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto(ProdutoDto produtoDto) {
        this.nome = produtoDto.getNome();
        this.tipo = produtoDto.getTipo();
        this.preco = produtoDto.getPreco();
        this.quantidade = produtoDto.getQuantidade();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }


    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
