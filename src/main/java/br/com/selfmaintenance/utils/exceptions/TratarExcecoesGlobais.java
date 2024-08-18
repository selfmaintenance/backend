package br.com.selfmaintenance.utils.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import br.com.selfmaintenance.utils.responses.DadosErro;
import br.com.selfmaintenance.utils.responses.RespostaApi;

@RestControllerAdvice
class TratarExcecoesGlobais {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RespostaApi> tratarErroDeValidacao(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String nome = ((FieldError) error).getField();
        String mensagem = error.getDefaultMessage();
        errors.put(nome, mensagem);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespostaApi(-1, "Erro de validação", errors));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<RespostaApi> tratarAusenciaDeCorpo(HttpMessageNotReadableException ex) {
    System.out.println(ex.getHttpInputMessage());
    Map<String, String> errors = new HashMap<>();
    errors.put("mensagem", "Corpo da requisição não pode ser vazio");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespostaApi(-1, "Envio inválido", errors));
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<RespostaApi> tratarResponseException(ResponseStatusException ex) {
    return ResponseEntity
            .status(ex.getStatusCode())
            .body(new RespostaApi(-1, ex.getReason()));
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<RespostaApi> tratarServiceException(ServiceException ex) {
    return ResponseEntity
            .status(ex.getStatus())
            .body(new RespostaApi(-1, ex.getMensagem(), new DadosErro(
              ex.getPacote(), 
              ex.getMetodo(),
              ex.getCausa(),
              ex.getStatus().value()
              ))
            );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RespostaApi> tratarException(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new RespostaApi(-1, "Erro interno no servidor"));
  }
}

