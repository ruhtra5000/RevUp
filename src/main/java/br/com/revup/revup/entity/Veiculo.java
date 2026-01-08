package br.com.revup.revup.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.revup.revup.entity.enums.TipoVeiculo;
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

    @OneToMany
    private List<Imagem> imagens;

    // Construtores
    public Veiculo () {
        this.imagens = new ArrayList<Imagem>(3);
    }

    public Veiculo(String placa, String chassi, String renavam, TipoVeiculo tipo, String marca, 
                   String modelo, int ano, String cor, BigDecimal kmTotal) {
        this.placa = placa;
        this.chassi = chassi;
        this.renavam = renavam;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.kmTotal = kmTotal;
        this.imagens = new ArrayList<Imagem>(3);
    }
    
    // Métodos
    public void adicionarKm(int km) {
        this.kmTotal.add(new BigDecimal(km));
    }

    public void adicionarImagem (Imagem imagem) {
        if (imagens.size() >= 3)
            throw new EstadoInvalidoException("Cada veículo suporta até 3 imagens.");

        imagens.add(imagem);
    }

    public void removerImagem (Imagem imagem) {
        if (!imagens.remove(imagem)) 
            throw new RecursoNaoEncontradoException("A imagem com id " + imagem.getId() + " não existe ou não está vinculada ao veículo com placa " + placa);
    }
}
