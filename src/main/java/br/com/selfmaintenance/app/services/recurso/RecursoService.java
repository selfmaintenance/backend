package br.com.selfmaintenance.app.services.recurso;

import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.Prestador;
import br.com.selfmaintenance.repositories.recurso.RecursoRepository;
import br.com.selfmaintenance.repositories.usuario.PrestadorRepository;

@Service
public class RecursoService {
  private final RecursoRepository recursoRepository;
  private final PrestadorRepository prestadorRepository;
  
  public RecursoService(RecursoRepository recursoRepository, PrestadorRepository prestadorRepository) {
    this.recursoRepository = recursoRepository;
    this.prestadorRepository = prestadorRepository;
  }

  public Map<String, Long> criar(CriarRecursoDTO dados, String emailPrestador) {
    Prestador prestador = this.prestadorRepository.findByEmail(emailPrestador);

    Recurso recursoSalvo = this.recursoRepository.save(new Recurso(
      prestador,
      dados.nome(),
      dados.quantidade(),
      dados.descricao()
    ));

    return Map.of("idRecurso", recursoSalvo.getId());
  }

  public void editar() {
  }
}