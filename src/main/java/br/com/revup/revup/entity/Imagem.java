package br.com.revup.revup.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Imagem {
    @Id
    @GeneratedValue
    private long id;
    private String path;        // Caminho do arquivo 
    private String nome;        // Nome do arquivo 
    private String tipoArquivo; // Extensão do arquivo (png, jpeg, ...)
}
