package br.com.revup.revup.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.revup.revup.exceptions.ArquivoInvalidoException;
import br.com.revup.revup.exceptions.CredenciaisInvalidasException;
import br.com.revup.revup.exceptions.EstadoInvalidoException;
import br.com.revup.revup.exceptions.RecursoDuplicadoException;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.exceptions.TipoArquivoNaoSuportadoException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdviceGeral {
    
    // Erro de validação (Spring Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> lidarComMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();
        
        erroResponse.setStatus(400);
        erroResponse.setErro("Erro de validação de campo");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());
        
        Map<String, String> errosPorCampo = new HashMap<>();
	    exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errosPorCampo.put(fieldName, errorMessage);
	    });
        erroResponse.setDetalhes(errosPorCampo);
	    
		return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.BAD_REQUEST
        );
    }

    // Exceções customizadas
    @ExceptionHandler(ArquivoInvalidoException.class)
    public ResponseEntity<ErroResponse> lidarComArquivoInvalidoException(ArquivoInvalidoException exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();

        erroResponse.setStatus(400);
        erroResponse.setErro("Arquivo Invalido");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());

        return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroResponse> lidarComCredenciaisInvalidasException(CredenciaisInvalidasException exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();

        erroResponse.setStatus(401);
        erroResponse.setErro("Credenciais Invalidas");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());

        return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.UNAUTHORIZED
        );
    }
    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<ErroResponse> lidarComEstadoInvalidoException(EstadoInvalidoException exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();

        erroResponse.setStatus(409);
        erroResponse.setErro("Estado Invalido");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());

        return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErroResponse> lidarComRecursoDuplicadoException(RecursoDuplicadoException exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();

        erroResponse.setStatus(409);
        erroResponse.setErro("Recurso Duplicado");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());

        return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> lidarComRecursoNaoEncontradoException(RecursoNaoEncontradoException exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();

        erroResponse.setStatus(404);
        erroResponse.setErro("Recurso Não Encontrado");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());

        return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(TipoArquivoNaoSuportadoException.class)
    public ResponseEntity<ErroResponse> lidarComTipoArquivoNaoSuportadoException(TipoArquivoNaoSuportadoException exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();

        erroResponse.setStatus(415);
        erroResponse.setErro("Tipo de arquivo não suportado");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());

        return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );
    }

    // Fallback 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> lidarComException(Exception exception, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse();

        erroResponse.setStatus(500);
        erroResponse.setErro("Erro Interno");
        erroResponse.setMensagem(exception.getMessage());
        erroResponse.setPath(request.getRequestURI());

        return new ResponseEntity<ErroResponse>(
            erroResponse, 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
