package br.com.revup.revup.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Lavagem {
    // Atributos
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "veiculo_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_lavagem_veiculo_cascade",
            foreignKeyDefinition = "FOREIGN KEY (veiculo_id) REFERENCES veiculo(id) ON DELETE CASCADE"
        )
    ) //Testar melhor
    private Veiculo veiculo;

    private String detalhes;
    private LocalDate data;

    // Construtor
    public Lavagem(Veiculo veiculo, String detalhes, LocalDate data) {
        this.veiculo = veiculo;
        this.detalhes = detalhes;
        this.data = data;
    }

}
