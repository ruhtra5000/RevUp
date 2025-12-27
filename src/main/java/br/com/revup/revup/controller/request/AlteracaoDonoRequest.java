package br.com.revup.revup.controller.request;

import jakarta.validation.constraints.NotNull;

public record AlteracaoDonoRequest(
    @NotNull(message = "O campo \"idAntigoDono\" é obrigatório.")
    long idAntigoDono,

    @NotNull(message = "O campo \"idNovoDono\" é obrigatório.")
    long idNovoDono
) {
    
}
