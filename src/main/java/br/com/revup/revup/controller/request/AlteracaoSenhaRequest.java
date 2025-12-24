package br.com.revup.revup.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlteracaoSenhaRequest(
    @NotBlank(message = "O campo \"senha\" é obrigatório.")
    @Size(min = 8, message = "A senha deve ter, no mínimo, 8 caracteres.")
    String senha
) {
    
}
