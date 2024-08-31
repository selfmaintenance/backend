package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.recurso.RecursoService;

/**
 * @category Facades
 * 
 * [PrestadorFacade] é a fachada de prestador nela temos os serviços de recurso todos 
 * concentrados em um único lugar
 * 
 * @see RecursoService
 *
 * @version 1.0.0 
 */
@Service
public class PrestadorFacade {
  /**
   * [RecursoService] é o serviço de recurso
   */
  public final RecursoService recurso;

  public PrestadorFacade(
    RecursoService recurso
  ) {
    this.recurso = recurso;
  }
}