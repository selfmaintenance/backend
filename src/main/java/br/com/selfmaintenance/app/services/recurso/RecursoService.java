package br.com.selfmaintenance.app.services.recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.infra.repositories.recurso.RecursoRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;

/**
 * [RecursoService] é a classe que representa a camada de serviço de recursos do sistema.
 * 
 * @version 1.0.0
 */
@Service
public class RecursoService {
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
    Oficina oficina = this.obterOficina(email);
    
    Recurso recursoSalvo = this.recursoRepository.save(new Recurso(
      oficina,
      dados.nome(),
      dados.quantidade(),
      dados.descricao()
    ));

    return Map.of("idRecurso", recursoSalvo.getId());
  }

  /**
   * [editar] é o método que edita um recurso no sistema.
   * 
   * @param id é o id do recurso a ser editado
   * @param dados é o DTO com os dados do recurso
   * @param email é o email do usuário autenticado
   * 
   * @see EditarRecursoDTO
   * @see Recurso
   * @see Oficina
   * 
   * @return um DTO com os dados do recurso editado
   */
  public RecursoResponseDTO editar(Long id, EditarRecursoDTO dados, String email) {
    Oficina oficina = this.obterOficina(email);
    Recurso recurso = this.recursoRepository.findByOficinaAndId(oficina, id);
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

  /**
   * [listar] é o método que lista os recursos de uma oficina.
   * 
   * @param email é o email do usuário autenticado
   * 
   * @return uma lista de DTOs com os dados dos recursos
   */
  public List<RecursoResponseDTO> listar(String email) {
    Oficina oficina = this.obterOficina(email);
    List<Recurso> recursos = this.recursoRepository.findByOficina_email(oficina.getEmail());
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

  /**
   * [buscar] é o método que busca um recurso no sistema.
   * 
   * @param id é o id do recurso a ser buscado
   * @param email é o email do usuário autenticado
   * 
   * @return um DTO com os dados do recurso buscado
   */
  public RecursoResponseDTO buscar(Long id, String email) {
    Oficina oficina = this.obterOficina(email);
    Recurso recurso = this.recursoRepository.findByOficinaAndId(oficina, id);
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

  /**
   * [deletar] é o método que deleta um recurso no sistema.
   * 
   * @param id é o id do recurso a ser deletado
   * @param email é o email do usuário autenticado
   * 
   * @return um booleano indicando se o recurso foi deletado
   */
  public boolean deletar(Long id, String email) {
    Oficina oficina = this.obterOficina(email);
    Recurso recurso = this.recursoRepository.findByOficinaAndId(oficina, id);

    if (recurso == null) {
      return false;
    }
    this.recursoRepository.delete(recurso);
    return true;
  }

  /**
   * [obterOficina] é o método que obtém a oficina de um usuário autenticado.
   * 
   * @param email é o email do usuário autenticado
   * 
   * @return a oficina do usuário autenticado
   */
  private Oficina obterOficina(String email) {
    UsuarioAutenticavel usuarioAutenticavel = this.usuarioAutenticavelRepository.findByEmailCustom(email);

    if (usuarioAutenticavel.getRole().equals(UsuarioRole.OFICINA)) {
      return this.oficinaRepository.findByEmail(email);
    } else {
      Prestador prestador = this.prestadorRepository.findByEmail(email);
      return this.oficinaRepository.findById(prestador.getOficina().getId()).get();
    }
  }
}