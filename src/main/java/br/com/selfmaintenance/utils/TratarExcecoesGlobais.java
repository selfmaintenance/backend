package br.com.selfmaintenance.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

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
}
