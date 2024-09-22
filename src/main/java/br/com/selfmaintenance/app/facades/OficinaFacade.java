package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.recurso.IRecursoService;
import br.com.selfmaintenance.app.services.usuario.prestador.IPrestadorService;

/**
 * [OficinaFacade] é a fachada de oficina nela temos os serviços de prestador e recurso todos 
 * concentrados em um único lugar
 * 
 * @see IPrestadorService
 * @see IRecursoService
 *
 * @version 1.0.0 
 */
@Service
public class OficinaFacade {
  /**
   * [IPrestadorService] é a definição do serviço de prestador
   */
  public final IPrestadorService prestador;
  /**
   * [IRecursoService] é a definição do serviço de recurso
   */
  public final IRecursoService recurso;

  public OficinaFacade(
    IPrestadorService prestador, 
    IRecursoService recurso
  ) {
    this.prestador = prestador;
    this.recurso = recurso;
  }
}