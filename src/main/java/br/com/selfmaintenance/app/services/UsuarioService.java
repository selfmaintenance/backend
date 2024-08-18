package br.com.selfmaintenance.app.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.selfmaintenance.app.records.usuario.dtos.CriarUsuarioDTO;
import br.com.selfmaintenance.domain.entities.usuario.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.Prestador;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.repositories.usuario.PrestadorRepository;
import br.com.selfmaintenance.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.responses.CriarUsuario;

@Service
public class UsuarioService {
  private final UsuarioAutenticavelRepository usuarioAutenticavelRepository;
  private final ClienteRepository clienteRepository;
  private final PrestadorRepository prestadorRepository;

  public UsuarioService(
    UsuarioAutenticavelRepository usuarioAutenticavelRepository,
    ClienteRepository clienteRepository,
    PrestadorRepository prestadorRepository
  ) {
    this.usuarioAutenticavelRepository = usuarioAutenticavelRepository;
    this.clienteRepository = clienteRepository;
    this.prestadorRepository = prestadorRepository;
  }

  public CriarUsuario criar(CriarUsuarioDTO dados) throws ServiceException {
    try {
      UserDetails usuarioExiste = this.usuarioAutenticavelRepository.findByEmail(dados.usuarioAutenticavel().email());

      if (usuarioExiste != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com esse email");
      }

      if (dados.cpf() == null && dados.cnpj() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe ao menos o CPF OU(E) CNPJ");  
      }
      
      UsuarioAutenticavel usuarioAutenticavel = new UsuarioAutenticavel(
        dados.usuarioAutenticavel().nome(),
        dados.usuarioAutenticavel().email(),
        dados.usuarioAutenticavel().contato(),
        dados.usuarioAutenticavel().senha(),
        dados.usuarioAutenticavel().role()
      );

      usuarioAutenticavel.criptografarSenha();
      UsuarioAutenticavel usuarioAutenticavelSalvo = this.usuarioAutenticavelRepository.save(usuarioAutenticavel);
      
      Long idUsuarioAutenticavel = usuarioAutenticavelSalvo.getId();
      if (usuarioAutenticavelSalvo.getRole() == UsuarioRole.CLIENTE) {
        Cliente novoCliente = this.clienteRepository.save(new Cliente(
          usuarioAutenticavelSalvo,
          dados.usuarioAutenticavel().nome(),
          dados.cpf(),
          dados.cnpj(),
          dados.usuarioAutenticavel().email(),
          dados.usuarioAutenticavel().contato(),
          dados.sexo(),
          usuarioAutenticavelSalvo.getPassword()
        ));

        return new CriarUsuario(novoCliente.getId(), null, idUsuarioAutenticavel);
      } else {
        System.out.println(usuarioAutenticavelSalvo.getPassword());
        Prestador novoPrestador = this.prestadorRepository.save(new Prestador(
          usuarioAutenticavelSalvo,
          dados.usuarioAutenticavel().nome(),
          dados.cpf(),
          dados.cnpj(),
          dados.usuarioAutenticavel().email(),
          dados.usuarioAutenticavel().contato(),
          dados.sexo(),
          usuarioAutenticavelSalvo.getPassword()          
        ));
        return new CriarUsuario(null, novoPrestador.getId(), idUsuarioAutenticavel);
      }
    } catch (Exception ex) {
      String causa;
      HttpStatus status;
      if (ex instanceof ResponseStatusException responseStatusException) {
        causa = responseStatusException.getReason();
        status = HttpStatus.valueOf(responseStatusException.getStatusCode().value());
      } else {
        causa = ex.getCause().getMessage();
        status = HttpStatus.BAD_REQUEST;
      }

      throw new ServiceException(
        UsuarioService.class.getName(), 
        "criar", 
        causa,
        "Erro ao criar novo usuário",
        status
      );
    }
  }
}

