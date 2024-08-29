package br.com.selfmaintenance.app.services.recurso;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.repositories.recurso.RecursoRepository;
import br.com.selfmaintenance.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.repositories.usuario.oficina.PrestadorRepository;

@Service
public class RecursoService { // TODO: CONTINUAR IMPLEMENTAÇÃOA
  private final RecursoRepository recursoRepository;
  private final UsuarioAutenticavelRepository usuarioAutenticavelRepository;
  private final PrestadorRepository prestadorRepository;
  private final OficinaRepository oficinaRepository;
  
  public RecursoService(
    RecursoRepository recursoRepository,
    UsuarioAutenticavelRepository usuarioAutenticavelRepository,
    PrestadorRepository prestadorRepository,
    OficinaRepository oficinaRepository
  ) {
    this.recursoRepository = recursoRepository;
    this.usuarioAutenticavelRepository = usuarioAutenticavelRepository;
    this.prestadorRepository = prestadorRepository;
    this.oficinaRepository = oficinaRepository;
  }

  public Map<String, Long> criar(CriarRecursoDTO dados, String email) {
    UsuarioAutenticavel usuarioAutenticavel = this.usuarioAutenticavelRepository.findByEmailCustom(email);
    Oficina oficina = null;
    
    if (usuarioAutenticavel.getRole().equals(UsuarioRole.OFICINA)) {
      oficina = this.oficinaRepository.findByEmail(email);
    } else {
      Prestador prestador = this.prestadorRepository.findByEmail(email);
      oficina = this.oficinaRepository.findById(prestador.getOficina().getId()).get();
    }
    
    Recurso recursoSalvo = this.recursoRepository.save(new Recurso(
      oficina,
      dados.nome(),
      dados.quantidade(),
      dados.descricao()
    ));

    return Map.of("idRecurso", recursoSalvo.getId());
  }

  public RecursoResponseDTO editar(Long id, EditarRecursoDTO dados, String email) {
    return null;
    // Prestador prestador = this.prestadorRepository.findByEmail(emailPrestador);
    // Recurso recurso = this.recursoRepository.findByPrestadorAndId(prestador, id);

    // if (recurso == null) {
    //   return null;
    // }
    // dados.nome().ifPresent(recurso::setNome);
    // dados.quantidade().ifPresent(recurso::setQuantidade);
    // dados.descricao().ifPresent(recurso::setDescricao);

    // this.recursoRepository.save(recurso);

    // return new RecursoResponseDTO(
    //   recurso.getId(),
    //   recurso.getNome(),
    //   recurso.getDescricao(),
    //   recurso.getQuantidade()
    // );
  }

  public List<RecursoResponseDTO> listar(String email) {
    return null;
    // List<Recurso> recursos = this.recursoRepository.findByPrestador_email(emailPrestador);
    // List<RecursoResponseDTO> recursosResponse = new ArrayList<>();

    // for (Recurso recurso : recursos) {
    //   recursosResponse.add(new RecursoResponseDTO(
    //     recurso.getId(),
    //     recurso.getNome(),
    //     recurso.getDescricao(),
    //     recurso.getQuantidade()
    //     )
    //   );
    // }

    // return recursosResponse;
  }

  public RecursoResponseDTO buscar(Long id, String email) {
    return null;
    // Prestador prestador = this.prestadorRepository.findByEmail(emailPrestador);
    // Recurso recurso = this.recursoRepository.findByPrestadorAndId(prestador, id);

    // if (recurso == null) {
    //   return null;
    // }
    
    // return new RecursoResponseDTO(
    //   recurso.getId(),
    //   recurso.getNome(),
    //   recurso.getDescricao(),
    //   recurso.getQuantidade()
    // );
  }

  public boolean deletar(Long id, String email) {
    return false;
    // Prestador prestador = this.prestadorRepository.findByEmail(emailPrestador);
    // Recurso recurso = this.recursoRepository.findByPrestadorAndId(prestador, id);

    // if (recurso == null) {
    //   return false;
    // }
    // this.recursoRepository.delete(recurso);
    // return true;
  }
}