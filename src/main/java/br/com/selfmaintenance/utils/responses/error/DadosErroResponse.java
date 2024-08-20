package br.com.selfmaintenance.utils.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DadosErroResponse(
  @JsonProperty String pacote,
  @JsonProperty String metodo,
  @JsonProperty String causa,
  @JsonProperty int status
) {
}