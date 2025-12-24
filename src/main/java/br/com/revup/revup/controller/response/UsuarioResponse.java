package br.com.revup.revup.controller.response;

import br.com.revup.revup.entity.Usuario;
import br.com.revup.revup.entity.enums.UsuarioRole;

public record UsuarioResponse(
    long id, 
    String nome,
    String email,
    String cpf,
    UsuarioRole role
) {
    public static UsuarioResponse paraDTO(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse(
            usuario.getId(), 
            usuario.getNome(), 
            usuario.getEmail(), 
            usuario.getCpf(), 
            usuario.getRole()
        );

        return response;
    }
}
