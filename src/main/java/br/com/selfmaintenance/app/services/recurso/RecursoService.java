package br.com.selfmaintenance.app.services.recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
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

  public RecursoResponseDTO editar(Long id, EditarRecursoDTO dados, String emailPrestador) {
    Prestador prestador = this.prestadorRepository.findByEmail(emailPrestador);
    Recurso recurso = this.recursoRepository.findByPrestadorAndId(prestador, id);

    if (recurso == null) {
      return null;
    }
    dados.nome().ifPresent(recurso::setNome);
    dados.quantidade().ifPresent(recurso::setQuantidade);
    dados.descricao().ifPresent(recurso::setDescricao);

    this.recursoRepository.save(recurso);

    return new RecursoResponseDTO(
      recurso.getId(),
      recurso.getNome(),
      recurso.getDescricao(),
      recurso.getQuantidade()
    );
  }

  public List<RecursoResponseDTO> listar(String emailPrestador) {
    List<Recurso> recursos = this.recursoRepository.findByPrestador_email(emailPrestador);
    List<RecursoResponseDTO> recursosResponse = new ArrayList<>();

    for (Recurso recurso : recursos) {
      recursosResponse.add(new RecursoResponseDTO(
        recurso.getId(),
        recurso.getNome(),
        recurso.getDescricao(),
        recurso.getQuantidade()
        )
      );
    }

    return recursosResponse;
  }

  public RecursoResponseDTO buscar(Long id, String emailPrestador) {
    Prestador prestador = this.prestadorRepository.findByEmail(emailPrestador);
    Recurso recurso = this.recursoRepository.findByPrestadorAndId(prestador, id);

    if (recurso == null) {
      return null;
    }
    
    return new RecursoResponseDTO(
      recurso.getId(),
      recurso.getNome(),
      recurso.getDescricao(),
      recurso.getQuantidade()
    );
  }

  public boolean deletar(Long id, String emailPrestador) {
    Prestador prestador = this.prestadorRepository.findByEmail(emailPrestador);
    Recurso recurso = this.recursoRepository.findByPrestadorAndId(prestador, id);

    if (recurso == null) {
      return false;
    }
    this.recursoRepository.delete(recurso);
    return true;
  }
}