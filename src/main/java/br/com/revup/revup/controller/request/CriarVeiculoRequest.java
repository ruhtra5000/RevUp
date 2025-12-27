package br.com.revup.revup.controller.request;

import java.math.BigDecimal;

import br.com.revup.revup.controller.constraints.AnoFabricacao;
import br.com.revup.revup.entity.Veiculo;
import br.com.revup.revup.entity.enums.TipoVeiculo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CriarVeiculoRequest(
    @NotNull(message = "O campo 'idUsuario' é obrigatório.")
    long idUsuario,

    @NotBlank(message = "O campo 'placa' é obrigatório.")
    @Pattern(regexp = "^[A-Z]{3}\\d[A-Z]\\d{2}$|^[A-Z]{3}\\d{4}$", message = "Placa inválida.")
    String placa,

    @NotBlank(message = "O campo 'chassi' é obrigatório.")
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$", message = "Número de chassi inválido.")
    String chassi,

    @NotBlank(message = "O campo 'renavam' é obrigatório.")
    @Pattern(regexp = "^\\d{11}$", message = "RENAVAM inválido.")
    String renavam,

    @NotNull(message = "O campo 'tipo' é obrigatório.")
    TipoVeiculo tipo,

    @NotBlank(message = "O campo 'marca' é obrigatório.")
    String marca,

    @NotBlank(message = "O campo 'modelo' é obrigatório.")
    String modelo,

    @NotNull(message = "O campo 'ano' é obrigatório.")
    @AnoFabricacao
    int ano,

    @NotBlank(message = "O campo 'cor' é obrigatório.")
    String cor,

    @NotNull(message = "O campo 'kmTotal' é obrigatório.")
    @Min(value = 0, message = "A quilometragem mínima é 0 km.")
    BigDecimal kmTotal
) {
    public Veiculo paraEntidade() {
        return new Veiculo (
            placa, chassi,
            renavam, tipo,
            marca, modelo, 
            ano, cor, kmTotal
        );
    }
}
