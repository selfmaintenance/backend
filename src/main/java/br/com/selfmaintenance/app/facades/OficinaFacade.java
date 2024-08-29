package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.app.services.usuario.PrestadorService;

@Service
public class OficinaFacade {
  public final PrestadorService prestador;
  public final RecursoService recurso;

  public OficinaFacade(
    PrestadorService prestador, 
    RecursoService recurso
  ) {
    this.prestador = prestador;
    this.recurso = recurso;
  }
}