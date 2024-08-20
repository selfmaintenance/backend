package br.com.selfmaintenance.app.services.veiculo;

import br.com.selfmaintenance.repositories.veiculo.VeiculoRepository;

public class VeiculoService {
  private final VeiculoRepository veiculoRepository;

  public VeiculoService(VeiculoRepository veiculoRepository) {
    this.veiculoRepository = veiculoRepository;
  }


}