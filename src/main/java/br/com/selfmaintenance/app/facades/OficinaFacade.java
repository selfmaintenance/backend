package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.app.services.usuario.PrestadorService;

/**
 * [OficinaFacade] é a fachada de oficina nela temos os serviços de prestador e recurso todos 
 * concentrados em um único lugar
 * 
 * @see PrestadorService
 * @see RecursoService
 *
 * @version 1.0.0 
 */
@Service
public class OficinaFacade {
  /**
   * [PrestadorService] é o serviço de prestador
   */
  public final PrestadorService prestador;
  /**
   * [RecursoService] é o serviço de recurso
   */
  public final RecursoService recurso;

  public OficinaFacade(
    PrestadorService prestador, 
    RecursoService recurso
  ) {
    this.prestador = prestador;
    this.recurso = recurso;
  }
}