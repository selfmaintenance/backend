package br.com.selfmaintenance.utils.exceptions;

import org.springframework.http.HttpStatus;

/**
 * [ServiceException] é a classe que representa as exceções de serviço da API.
 * 
 * @version 1.0.0
 */
public class ServiceException extends Exception {
  /**
   * [pacote] é o pacote onde a exceção ocorreu
   */
  private final String pacote;
  /**
   * [causa] é a causa da exceção
   */
  private final String causa;
  /**
   * [metodo] é o método onde a exceção ocorreu
   */
  private final String metodo;
  /**
   * [mensagem] é a mensagem da exceção
   */ 
  private final String mensagem;
  /**
   * [status] é o status da exceção
   */
  private final HttpStatus status;

  public ServiceException(
    String pacote, 
    String metodo, 
    String causa,
    String mensagem,
    HttpStatus status
  ) {
    super(mensagem);
    this.pacote = pacote;
    this.causa = causa;
    this.metodo = metodo;
    this.mensagem = mensagem;
    this.status = status;
  }

  /**
   * [getPacote] é o método que retorna o pacote onde a exceção ocorreu
   * 
   * @return o pacote onde a exceção ocorreu
   */
  public String getPacote() {
    return this.pacote;
  }

  /**
   * [getCausa] é o método que retorna a causa da exceção
   * 
   * @return a causa da exceção
   */
  public String getCausa() {
    return this.causa;
  }

  /**
   * [getMetodo] é o método que retorna o método onde a exceção ocorreu
   * 
   * @return o método onde a exceção ocorreu
   */
  public String getMetodo() {
    return this.metodo;
  }

  /**
   * [getStatus] é o método que retorna o status da exceção
   * 
   * @return o status da exceção
   */
  public HttpStatus getStatus() {
    return this.status;
  }

  /**
   * [getMensagem] é o método que retorna a mensagem da exceção
   * 
   * @return a mensagem da exceção
   */
  public String getMensagem() {
    return this.mensagem;
  }
}