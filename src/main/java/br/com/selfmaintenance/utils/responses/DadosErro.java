package br.com.selfmaintenance.utils.responses;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DadosErro {
  @JsonProperty
  private final String pacote;
  @JsonProperty 
  private final String metodo;
  @JsonProperty
  private String causa;
  @JsonProperty
  private final int status;
  
  public DadosErro(
    String pacote, 
    String metodo, 
    String causa,
    int status
  ) {
    this.pacote = pacote;
    this.metodo = metodo;
    this.causa = causa;
    this.status = status;
  }
}
