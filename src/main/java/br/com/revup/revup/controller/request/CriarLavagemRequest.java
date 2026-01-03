package br.com.revup.revup.controller.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.revup.revup.entity.Lavagem;
import br.com.revup.revup.entity.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarLavagemRequest(
    @NotNull(message = "O campo 'idVeiculo' é obrigatório.")
    long idVeiculo,

    @NotBlank(message = "O campo 'detalhes' é obrigatório.")
    String detalhes,

    @NotNull(message = "O campo 'data' é obrigatório.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data
) {
    public Lavagem paraEntidade () {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(idVeiculo);

        return new Lavagem(
            veiculo, 
            detalhes, 
            data
        );
    }
}
