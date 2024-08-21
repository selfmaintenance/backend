package br.com.selfmaintenance.app.services.usuario;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.domain.entities.usuario.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.Prestador;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.repositories.usuario.PrestadorRepository;
import br.com.selfmaintenance.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
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

  public Map<String, Long> criar(CriarUsuarioDTO dados) throws ServiceException {
      this.validarDadosCriacaoUsuario(dados);
      UsuarioAutenticavel usuarioAutenticavelSalvo = this.criarUsuarioAutenticavel(dados);
      
      Long idUsuarioAutenticavel = usuarioAutenticavelSalvo.getId();
      Map<String, Long> resposta = new HashMap<>();

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

        resposta.put("idCliente", novoCliente.getId());
        resposta.put("idUsuarioAutenticavel", idUsuarioAutenticavel);
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
        resposta.put("idPrestador", novoPrestador.getId());
        resposta.put("idUsuarioAutenticavel", idUsuarioAutenticavel);
      }
      return resposta;
    }

    private void validarDadosCriacaoUsuario(CriarUsuarioDTO dados) throws ServiceException {
      UserDetails usuarioExiste = this.usuarioAutenticavelRepository.findByEmail(dados.usuarioAutenticavel().email());

      if (usuarioExiste != null) {
        throw new ServiceException(
          UsuarioService.class.getName(), 
          "criar", 
          "Já existe um usuário com esse email",
          "Erro ao criar novo usuário",
          HttpStatus.CONFLICT
        );
      }

      if (dados.cpf() == null && dados.cnpj() == null) {
        throw new ServiceException(
          UsuarioService.class.getName(), 
          "criar", 
          "Informe ao menos o CPF OU(E) CNPJ",
          "Erro ao criar novo usuário",
          HttpStatus.BAD_REQUEST
        );
      }
    }

    private UsuarioAutenticavel criarUsuarioAutenticavel(CriarUsuarioDTO dados) {
      UsuarioAutenticavel usuarioAutenticavel = new UsuarioAutenticavel(
        dados.usuarioAutenticavel().nome(),
        dados.usuarioAutenticavel().email(),
        dados.usuarioAutenticavel().contato(),
        dados.usuarioAutenticavel().senha(),
        UsuarioRole.valueOf(dados.usuarioAutenticavel().role())
      );

      usuarioAutenticavel.criptografarSenha();
      return this.usuarioAutenticavelRepository.save(usuarioAutenticavel);
    }
}

