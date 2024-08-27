package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.recurso.RecursoService;

@Service
public class PrestadorFacade {
  public final RecursoService recurso;

  public PrestadorFacade(
    RecursoService recurso
  ) {
    this.recurso = recurso;
  }
}