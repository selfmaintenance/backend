package br.com.selfmaintenance.utils.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {
  private final String pacote; 
  private final String causa;
  private final String metodo; 
  private final String mensagem;
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

  public String getPacote() {
    return this.pacote;
  }

  public String getCausa() {
    return this.causa;
  }

  public String getMetodo() {
    return this.metodo;
  }

  public HttpStatus getStatus() {
    return this.status;
  }

  public String getMensagem() {
    return this.mensagem;
  }
}