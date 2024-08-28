package br.com.selfmaintenance.app.records.veiculo;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.selfmaintenance.domain.entities.veiculo.VeiculoTipo;

public record VeiculoResponseDTO(
  @JsonProperty Long id,
  @JsonProperty String placa,
  @JsonProperty VeiculoTipo tipo,
  @JsonProperty String marca,
  @JsonProperty String modelo,
  @JsonProperty int ano,
  @JsonProperty String chassi,
  @JsonProperty String renavam,
  @JsonProperty String cor
) {
}