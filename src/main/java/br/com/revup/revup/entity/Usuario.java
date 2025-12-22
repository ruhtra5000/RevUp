package br.com.revup.revup.entity;

import java.util.List;

import br.com.revup.revup.entity.enums.UsuarioRole;
import br.com.revup.revup.exceptions.RecursoDuplicadoException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
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

    private UsuarioRole role;

    @OneToMany
    private List<Veiculo> veiculos;

    // Métodos
    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculos.contains(veiculo))
            throw new RecursoDuplicadoException("O veículo com placa " + veiculo.getPlaca() + " já se encontra vinculado a este usuário.");
    
        veiculos.add(veiculo);
    }

    public void removerVeiculo(Veiculo veiculo) {
        if (!veiculos.remove(veiculo))
            throw new RecursoDuplicadoException("O veículo com placa " + veiculo.getPlaca() + " não pertence a este usuário.");
    }
}
