package br.com.revup.revup.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.revup.revup.entity.Manutencao;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarManutencaoRequest(
    @NotBlank(message = "O campo 'servico' é obrigatório.")
    String servico,

    @NotNull(message = "O campo 'valor' é obrigatório.")
    @Min(value = 0, message = "Valores monetários negativos não são aceitos.")
    BigDecimal valor,

    @NotNull(message = "O campo 'data' é obrigatório.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data,

    @NotNull(message = "O campo 'quilometragem' é obrigatório.")
    @Min(value = 0, message = "Quilometragem negativa não é aceita.")
    BigDecimal quilometragem
) {
    public Manutencao paraEntidade() {
        Manutencao manutencao = new Manutencao();
        manutencao.setServico(servico);
        manutencao.setValor(valor);
        manutencao.setData(data);
        manutencao.setQuilometragem(quilometragem);

        return manutencao;
    }
}
