package br.com.selfmaintenance.app.services.recurso;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.repositories.recurso.RecursoRepository;

@Service
public class RecursoService {
  private final RecursoRepository recursoRepository;

  public RecursoService(RecursoRepository recursoRepository) {
    this.recursoRepository = recursoRepository;
  }

  public void criar(CriarRecursoDTO dados, String emailPrestador) {
  }

  public void editar() {
  }
}
