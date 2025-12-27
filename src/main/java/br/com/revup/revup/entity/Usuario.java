package br.com.revup.revup.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.revup.revup.entity.enums.UsuarioRole;
import br.com.revup.revup.exceptions.EstadoInvalidoException;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
public class Usuario {
    // Atributos
    @Id
    @GeneratedValue
    private long id;

    private String nome;
    private String email;
    private String senha;

    @Setter(AccessLevel.NONE)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    @OneToMany
    private List<Veiculo> veiculos;

    // Construtores
    public Usuario (String nome, String email, String senha, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.role = UsuarioRole.USUARIO;
        this.veiculos = new ArrayList<>();
    }

    // Métodos
    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculos.contains(veiculo))
            throw new EstadoInvalidoException("O veículo com placa " + veiculo.getPlaca() + " já se encontra vinculado a este usuário.");
    
        veiculos.add(veiculo);
    }

    public void removerVeiculo(Veiculo veiculo) {
        if (!veiculos.remove(veiculo))
            throw new RecursoNaoEncontradoException("O veículo com placa " + veiculo.getPlaca() + " não pertence a este usuário.");
    }
}
