package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.veiculo.VeiculoService;

@Service
public class ClienteFacade {
  public final VeiculoService veiculo;

  public ClienteFacade(
    VeiculoService veiculo
  ) {
    this.veiculo = veiculo;
  }
}