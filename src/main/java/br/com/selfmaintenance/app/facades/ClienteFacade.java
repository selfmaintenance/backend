package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.veiculo.IVeiculoService;

/**
 * [ClienteFacade] é a fachada de cliente nela temos os serviços de veículo todos 
 * concentrados em um único lugar
 * 
 * @see IVeiculoService
 *
 * @version 1.0.0 
 */
@Service
public class ClienteFacade {
  /**
   * [IVeiculoService] é a definição do serviço de veículo
   */
  public final IVeiculoService veiculo;

  public ClienteFacade(
    IVeiculoService veiculo
  ) {
    this.veiculo = veiculo;
  }
}