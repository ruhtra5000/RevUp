package br.com.revup.revup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.revup.revup.entity.enums.TipoCombustivel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Abastecimento {
    // Atributos
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "veiculo_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_abastecimento_veiculo_cascade",
            foreignKeyDefinition = "FOREIGN KEY (veiculo_id) REFERENCES veiculo(id) ON DELETE CASCADE"
        )
    ) //Testar melhor
    private Veiculo veiculo;

    @Enumerated(EnumType.STRING)
    private TipoCombustivel combustivel;

    private BigDecimal litros;
    private LocalDate data;
    private BigDecimal kmComTanque;

    @Setter(AccessLevel.NONE)
    private BigDecimal kmPorLitro;

    // Setter
    public void setKmComTanque(BigDecimal kmComTanque) {
        this.kmComTanque = kmComTanque;
        calcularKmPorLitro();
    }

    // Métodos
    public void calcularKmPorLitro() {
        this.kmPorLitro = this.kmComTanque.divide(this.litros);
    }
}
