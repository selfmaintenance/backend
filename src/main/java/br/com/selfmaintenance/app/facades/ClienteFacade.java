package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.veiculo.VeiculoService;

/**
 * [ClienteFacade] é a fachada de cliente nela temos os serviços de veículo todos 
 * concentrados em um único lugar
 * 
 * @see VeiculoService
 *
 * @version 1.0.0 
 */
@Service
public class ClienteFacade {
  /**
   * [VeiculoService] é o serviço de veículo
   */
  public final VeiculoService veiculo;

  public ClienteFacade(
    VeiculoService veiculo
  ) {
    this.veiculo = veiculo;
  }
}