package br.com.revup.revup.controller.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.revup.revup.entity.Lavagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarLavagemRequest(
    @NotBlank(message = "O campo 'detalhes' é obrigatório.")
    String detalhes,

    @NotNull(message = "O campo 'data' é obrigatório.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data
) {
    public Lavagem paraEntidade () {
        Lavagem lavagem = new Lavagem();
        lavagem.setDetalhes(detalhes);
        lavagem.setData(data);

        return lavagem;
    }
}
