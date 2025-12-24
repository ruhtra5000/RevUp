package br.com.revup.revup.controller.request;

import br.com.revup.revup.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EditarUsuarioRequest(
    @NotBlank(message = "O campo \"nome\" é obrigatório.")
    String nome,
    
    @NotBlank(message = "O campo \"email\" é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email
) {
    public Usuario paraEntidade() {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);

        return usuario;
    }
}
