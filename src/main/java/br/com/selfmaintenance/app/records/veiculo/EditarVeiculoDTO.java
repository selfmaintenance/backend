package br.com.selfmaintenance.app.records.veiculo;

import java.util.Optional;

import br.com.selfmaintenance.app.validations.veiculo.ValidTipo;

public record EditarVeiculoDTO(
  Optional<String> marca,
  Optional<String> modelo,
  Optional<Integer> ano,
  Optional<String> cor,

  @ValidTipo
  String tipo
) {
}