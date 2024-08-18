package br.com.selfmaintenance.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CriarUsuario {
  @JsonProperty
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long idCliente;

  @JsonProperty
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long idPrestador;

  @JsonProperty
  private Long idUsuarioAutenticavel;
  
  public CriarUsuario(Long idCliente, Long idPrestador, Long idUsuarioAutenticavel) {
    this.idCliente = idCliente;
    this.idPrestador = idPrestador;
    this.idUsuarioAutenticavel = idUsuarioAutenticavel;
  }
}