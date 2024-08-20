package br.com.selfmaintenance.app.records.veiculo;

import br.com.selfmaintenance.app.validations.veiculo.ValidTipo;
import br.com.selfmaintenance.domain.entities.veiculo.VeiculoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarVeiculoDTO(
  @NotBlank(message = "Placa não pode ser vazia")
  @Size(min = 7, max = 7, message = "Placa deve ter 7 caracteres")
  String placa,

  @ValidTipo
  VeiculoTipo tipo,

  String marca,

  String modelo,

  int ano,

  @NotBlank(message = "Chassi não pode ser vazio")
  String chassi,

  @Size(min = 9, max = 11, message = "Renavam deve ter entre 9 e 11 caracteres")
  String renavam,

  String cor
) {
}
