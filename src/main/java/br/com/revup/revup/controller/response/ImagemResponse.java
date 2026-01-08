package br.com.revup.revup.controller.response;

import org.springframework.core.io.Resource;

import br.com.revup.revup.entity.Imagem;

public record ImagemResponse (
    long id,
    String nome,
    String tipoArquivo,
    Resource resource
) {
    public static ImagemResponse paraDTO (Imagem imagem, Resource resource) {
        ImagemResponse response = new ImagemResponse(
            imagem.getId(),
            imagem.getNome(),
            imagem.getTipoArquivo(), 
            resource
        );

        return response;
    }
}
