package br.com.selfmaintenance.utils.responses.autenticacao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AutenticacaoResponse(
  @JsonProperty String token
) {
}