package br.com.selfmaintenance.app.records.recurso;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecursoResponseDTO(
  @JsonProperty Long id,
  @JsonProperty String nome,
  @JsonProperty String descricao,
  @JsonProperty int quantidade
) {
}
