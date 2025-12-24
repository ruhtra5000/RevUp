package br.com.revup.revup.exceptions;

public class CredenciaisInvalidasException extends RuntimeException {
    
    // cod. HTTP: 401
    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }

}
