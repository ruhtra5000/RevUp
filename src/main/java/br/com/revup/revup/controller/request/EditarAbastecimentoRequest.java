package br.com.revup.revup.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.revup.revup.entity.Abastecimento;
import br.com.revup.revup.entity.enums.TipoCombustivel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EditarAbastecimentoRequest(
    @NotNull(message = "O campo 'combustivel' é obrigatório.")
    TipoCombustivel combustivel,

    @NotNull(message = "O campo 'litros' é obrigatório.")
    @Min(value = 1, message = "A quantidade de litros deve ser, no mínimo, um litro.")
    BigDecimal litros,

    @NotNull(message = "O campo 'data' é obrigatório.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data,

    BigDecimal kmComTanque
) {
    public Abastecimento paraEntidade() {
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setCombustivel(combustivel);
        abastecimento.setLitros(litros);
        abastecimento.setData(data);
        abastecimento.setKmComTanque(kmComTanque);

        return abastecimento;
    }
}
