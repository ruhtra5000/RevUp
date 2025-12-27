package br.com.revup.revup.controller.request;

import org.hibernate.validator.constraints.br.CPF;

import br.com.revup.revup.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(
    @NotBlank(message = "O campo 'nome' é obrigatório.")
    String nome,
    
    @NotBlank(message = "O campo 'email' é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email,

    @NotBlank(message = "O campo 'senha' é obrigatório.")
    @Size(min = 8, message = "A senha deve ter, no mínimo, 8 caracteres.")
    String senha,

    @NotBlank(message = "O campo 'cpf' é obrigatório.")
    @CPF(message = "O CPF deve ser válido.")
    String cpf
) {
    public Usuario paraEntidade() {
        return new Usuario(
            nome, 
            email, 
            senha, 
            cpf
        );
    }
}
