package br.com.selfmaintenance.utils.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * [DadosErroResponse] é a classe que representa os dados de erro da API.
 * 
 * @version 1.0.0
 */
public record DadosErroResponse(
  @JsonProperty String pacote,
  @JsonProperty String metodo,
  @JsonProperty String causa,
  @JsonProperty int status
) {
}