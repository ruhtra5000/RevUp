package br.com.revup.revup.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Imagem {
    // Atributos
    @Id
    @GeneratedValue
    private long id;
    private String path;        // Caminho do arquivo 
    private String nome;        // Nome do arquivo 
    private String tipoArquivo; // Extensão do arquivo (png, jpeg, ...)

    // Construtor
    public Imagem (String path, String nome, String tipoArquivo) {
        this.path = path;
        this.nome = nome;
        this.tipoArquivo = tipoArquivo;
    }
}
