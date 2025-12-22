package br.com.revup.revup.entity;

import java.math.BigDecimal;

import br.com.revup.revup.entity.enums.TipoVeiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Veiculo {
    // Atributos
    @Id
    @GeneratedValue
    private long id;
    
    private String placa;

    @Setter(AccessLevel.NONE)
    private String chassi; 

    @Setter(AccessLevel.NONE)
    private String renavam;

    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipo;

    private String marca;
    private String modelo;
    private int ano;
    private String cor;

    private BigDecimal kmTotal;
    private BigDecimal consumoMedio;

    // Métodos
    public void adicionarKm(int km) {
        this.kmTotal.add(new BigDecimal(km));
    }
}
