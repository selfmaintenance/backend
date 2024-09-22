package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.recurso.IRecursoService;

/**
 * [PrestadorFacade] é a fachada de prestador nela temos os serviços de recurso todos 
 * concentrados em um único lugar
 * 
 * @see IRecursoService
 *
 * @version 1.0.0 
 */
@Service
public class PrestadorFacade {
  /**
   * [IRecursoService] é a definição do serviço de recurso
   */
  public final IRecursoService recurso;

  public PrestadorFacade(
    IRecursoService recurso
  ) {
    this.recurso = recurso;
  }
}