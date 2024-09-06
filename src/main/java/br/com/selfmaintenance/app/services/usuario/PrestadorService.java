package br.com.selfmaintenance.app.services.usuario;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.prestador.CriarPrestadorDTO;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;

/**
 * [PrestadorService] é a classe que representa a camada de serviço de prestadores do sistema.
 * 
 * @version 1.0.0
 */
@Service
public class PrestadorService {
  private final UsuarioAutenticavelRepository usuarioAutenticavelRepository;
  private final PrestadorRepository prestadorRepository;
  private final OficinaRepository oficinaRepository;

  public PrestadorService(
    UsuarioAutenticavelRepository usuarioAutenticavelRepository,
    PrestadorRepository prestadorRepository, 
    OficinaRepository oficinaRepository
  ) {
    this.usuarioAutenticavelRepository = usuarioAutenticavelRepository;
    this.prestadorRepository = prestadorRepository;
    this.oficinaRepository = oficinaRepository;
  }

  /**
   * [criar] é o método que cria um prestador no sistema.
   * 
   * @param dados é o DTO com os dados do prestador
   * @param emailOficina é o email da oficina que está criando o prestador
   * 
   * @see CriarPrestadorDTO
   * @see Prestador
   * @see Oficina
   * 
   * @return um mapa com o id do prestador criado
   */
  public Map<String, Long> criar(CriarPrestadorDTO dados, String emailOficina) throws ServiceException {
    this.validarDadosCriacao(dados);
    dados.usuarioAutenticavelPrestador().nome();
    Oficina oficina = this.oficinaRepository.findByEmail(emailOficina);
    UsuarioAutenticavel usuarioAutenticavel = this.criarUsuarioAutenticavel(dados);

    Prestador novoPrestador = this.prestadorRepository.save(new Prestador(
      oficina,
      usuarioAutenticavel,
      dados.usuarioAutenticavelPrestador().nome(),
      dados.cpf(),
      dados.usuarioAutenticavelPrestador().email(),
      dados.usuarioAutenticavelPrestador().contato(),
      dados.sexo(),
      usuarioAutenticavel.getPassword()
    )); 

    return Map.of("idPrestador", novoPrestador.getId());
  }

  /**
   * [criarUsuarioAutenticavel] é o método que cria um usuário autenticável no sistema.
   * 
   * @param dados
   * 
   * @see CriarPrestadorDTO
   * @see UsuarioAutenticavel
   * 
   * @return um usuário autenticável criado
   */
  private UsuarioAutenticavel criarUsuarioAutenticavel(CriarPrestadorDTO dados) {
    UsuarioAutenticavel usuarioAutenticavel = new UsuarioAutenticavel(
      dados.usuarioAutenticavelPrestador().nome(),
      dados.usuarioAutenticavelPrestador().email(),
      dados.usuarioAutenticavelPrestador().contato(),
      dados.usuarioAutenticavelPrestador().senha(),
      UsuarioRole.PRESTADOR
    );

    usuarioAutenticavel.criptografarSenha();
    return this.usuarioAutenticavelRepository.save(usuarioAutenticavel);
  }

  /**
   * [validarDadosCriacao] é o método que valida os dados de criação de um prestador.
   * 
   * @param dados
   * 
   * @see CriarPrestadorDTO
   * 
   * @throws ServiceException se os dados de criação do prestador forem inválidos
   */
  private void validarDadosCriacao(CriarPrestadorDTO dados) throws ServiceException {
    UserDetails usuarioExiste = this.usuarioAutenticavelRepository.findByEmail(dados.usuarioAutenticavelPrestador().email());
    if (usuarioExiste != null) {
      throw new ServiceException(
        UsuarioService.class.getName(), 
        "criar", 
        "Já existe um usuário com esse email",
        "Erro ao criar novo prestador",
        HttpStatus.CONFLICT
      );
    }
  }
}