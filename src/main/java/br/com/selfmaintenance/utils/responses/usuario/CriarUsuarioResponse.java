package br.com.selfmaintenance.utils.responses.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CriarUsuarioResponse(
  @JsonInclude(JsonInclude.Include.NON_NULL) Long idCliente,
  @JsonInclude(JsonInclude.Include.NON_NULL) Long idPrestador,
  @JsonProperty Long idUsuarioAutenticavel
) {
}