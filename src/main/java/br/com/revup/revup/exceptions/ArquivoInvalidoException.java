package br.com.revup.revup.exceptions;

public class ArquivoInvalidoException extends RuntimeException {
    
    public ArquivoInvalidoException (String mensagem) {
        super(mensagem);
    }
}
