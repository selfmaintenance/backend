package br.com.selfmaintenance.app.services.usuario;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
@Service
public class UsuarioService {
  private final UsuarioAutenticavelRepository usuarioAutenticavelRepository;
  private final ClienteRepository clienteRepository;
  private final OficinaRepository oficinaRepository;

  public UsuarioService(
    UsuarioAutenticavelRepository usuarioAutenticavelRepository,
    ClienteRepository clienteRepository,
    OficinaRepository oficinaRepository
  ) {
    this.usuarioAutenticavelRepository = usuarioAutenticavelRepository;
    this.clienteRepository = clienteRepository;
    this.oficinaRepository = oficinaRepository;
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
          dados.usuarioAutenticavel().email(),
          dados.usuarioAutenticavel().contato(),
          dados.sexo(),
          usuarioAutenticavelSalvo.getPassword()
        ));

        resposta.put("idCliente", novoCliente.getId());
        resposta.put("idUsuarioAutenticavel", idUsuarioAutenticavel);
      } else if (usuarioAutenticavelSalvo.getRole() == UsuarioRole.OFICINA) {
        Oficina novaOficina = this.oficinaRepository.save(new Oficina(
          usuarioAutenticavelSalvo,
          dados.usuarioAutenticavel().nome(),
          dados.cnpj(),
          dados.usuarioAutenticavel().email(),
          dados.usuarioAutenticavel().senha()
        ));
        resposta.put("idPrestador", novaOficina.getId());
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
        "Informe ao menos o CPF OU CNPJ",
        "Erro ao criar novo usuário",
        HttpStatus.BAD_REQUEST
      );
    } else if (dados.cpf() != null && dados.cnpj() != null) {
      throw new ServiceException(
        UsuarioService.class.getName(),
        "criar",
        "Informe somente CPF ou CNPJ",
        "Erro ao criar novo usuário",
        HttpStatus.BAD_REQUEST
      );
    }

    if (UsuarioRole.valueOf((dados.usuarioAutenticavel().role())).equals(UsuarioRole.OFICINA) && dados.cnpj() == null) {
      throw new ServiceException(
        UsuarioService.class.getName(),
        "criar",
        "Oficina deve ter um CNPJ",
        "Erro ao criar novo usuário",
        HttpStatus.BAD_REQUEST
      );
    } else if (UsuarioRole.valueOf((dados.usuarioAutenticavel().role())).equals(UsuarioRole.CLIENTE) && dados.cpf() == null) {
      throw new ServiceException(
        UsuarioService.class.getName(),
        "criar",
        "Cliente deve ter um CPF",
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

