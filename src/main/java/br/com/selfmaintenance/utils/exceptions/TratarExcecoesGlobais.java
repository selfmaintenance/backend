package br.com.selfmaintenance.utils.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.exceptions.JWTDecodeException;

import br.com.selfmaintenance.utils.responses.ApiResponse;
import br.com.selfmaintenance.utils.responses.error.DadosErroResponse;

/**
 * [TratarExcecoesGlobais] é a classe que trata as exceções globais da API.
 * 
 * @version 1.0.0
 */
@RestControllerAdvice
class TratarExcecoesGlobais {

  /**
   * [tratarErroDeValidacao] é o método que trata exceções lançadas devido a erros de  validação
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> tratarErroDeValidacao(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String nome = ((FieldError) error).getField();
        String mensagem = error.getDefaultMessage();
        errors.put(nome, mensagem);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(-1, "Erro de validação", errors));
  }

  /**
   * [tratarAusenciaDeCorpo] é o método que trata exceções lançadas devido a ausência de corpo na requisição
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse> tratarAusenciaDeCorpo(HttpMessageNotReadableException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("mensagem", "Corpo da requisição não pode ser vazio");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(-1, "Envio inválido", errors));
  }

  /**
   * [tratarResponseException] é o método que trata exceções lançadas devido a erros de status
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse> tratarResponseException(ResponseStatusException ex) {
    return ResponseEntity
            .status(ex.getStatusCode())
            .body(new ApiResponse(-1, ex.getReason()));
  }

  /**
   * [tratarJWTDecodeException] é o método que trata exceções lançadas devido a erros de decodificação de token
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(JWTDecodeException.class)
  public ResponseEntity<ApiResponse> tratarJWTDecodeException(JWTDecodeException ex) {
    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponse(-1, "Token inválido"));
  }

  /**
   * [tratarServiceException] é o método que trata exceções lançadas devido a erros de serviço
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ApiResponse> tratarServiceException(ServiceException ex) {
    return ResponseEntity
            .status(ex.getStatus())
            .body(new ApiResponse(-1, ex.getMensagem(), new DadosErroResponse(
              ex.getPacote(), 
              ex.getMetodo(),
              ex.getCausa(),
              ex.getStatus().value()
              ))
            );
  }

  /**
   * [tratarAutenticacaoException] é o método que trata exceções lançadas devido a erros de autenticação
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiResponse> tratarAutenticacaoException(AuthenticationException ex) {
    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponse(-1, "Usuário ou senha inválidos"));
  }

  /**
   * [tratarArgumentoIncorreto] é o método que trata exceções lançadas devido a argumentos incorretos
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResponse> tratarArgumentoIncorreto(MethodArgumentTypeMismatchException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse(-1, "O Argumento "+ex.getName()+" recebeu um valor inválido"));
  }

  /**
   * [tratarException] é o método que trata exceções lançadas devido a erros internos não mapeados no servidor
   * 
   * @param ex
   * @return a resposta da API
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> tratarException(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse(-1, "Erro interno no servidor"));
  }
}

