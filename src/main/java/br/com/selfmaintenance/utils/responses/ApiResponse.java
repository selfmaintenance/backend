package br.com.selfmaintenance.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.selfmaintenance.utils.responses.error.DadosErroResponse;

public record ApiResponse(
  @JsonProperty int status,
  @JsonProperty String mensagem,
  @JsonInclude(JsonInclude.Include.NON_NULL) Object dados,
  @JsonInclude(JsonInclude.Include.NON_NULL) DadosErroResponse erro
) {
  public ApiResponse(int status, String mensagem) {
    this(status, mensagem, null, null);
  }

  public ApiResponse(int status, String mensagem, Object dados) {
    this(status, mensagem, dados, null);
  }

  public ApiResponse(int status, String mensagem, DadosErroResponse erro) {
    this(status, mensagem, null, erro);
  }
}