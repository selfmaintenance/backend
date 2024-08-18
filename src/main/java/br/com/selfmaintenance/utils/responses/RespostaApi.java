package br.com.selfmaintenance.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RespostaApi {
  @JsonProperty
  private int status;
  
  @JsonProperty
  private String mensagem;

  @JsonProperty
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Object dados;
  
  @JsonProperty
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private DadosErro erro;

  public RespostaApi(int status, String mensagem) {
    this.status = status;
    this.mensagem = mensagem;
  }

  public RespostaApi(int status, String mensagem, Object dados) {
    this.status = status;
    this.mensagem = mensagem;
    this.dados = dados;
  }

  public RespostaApi(int status, String mensagem, DadosErro erro) {
    this.status = status;
    this.mensagem = mensagem;
    this.erro = erro;
  }
}
