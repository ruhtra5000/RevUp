package br.com.revup.revup.exceptions;

public class TipoArquivoNaoSuportadoException extends RuntimeException {
    
    public TipoArquivoNaoSuportadoException (String mensagem) {
        super(mensagem);
    }
    
}
