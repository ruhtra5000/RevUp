package br.com.revup.revup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Manutencao {
    // Atributos
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "veiculo_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_manutencao_veiculo_cascade",
            foreignKeyDefinition = "FOREIGN KEY (veiculo_id) REFERENCES veiculo(id) ON DELETE CASCADE"
        )
    ) //Testar melhor
    private Veiculo veiculo;

    private String servico;
    private BigDecimal valor;
    private LocalDate data;
    private BigDecimal quilometragem;
}
