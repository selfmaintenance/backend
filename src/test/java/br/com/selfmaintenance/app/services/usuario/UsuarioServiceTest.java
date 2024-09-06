package br.com.selfmaintenance.app.services.usuario;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;

@Comment("Testa a classe UsuarioService")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UsuarioServiceTest {
  @InjectMocks
  private UsuarioService usuarioService;
  @Mock
  private UsuarioAutenticavelRepository MockUsuarioAutenticavelRepository;
  @Mock 
  private ClienteRepository MockClienteRepository;
  @Mock 
  private OficinaRepository MockOficinaRepository;

  @Comment("Deveria lançar uma ServiceException caso já exista um usuário cadastrado com email fornecido")
  @Test
  void deveLancarExcecaoCasoJaExistaUmEmailCadastrado() {
    // arrange
    String email = "usuarioExistente@gmail.com";
    UsuarioAutenticavel usuarioExistente = new UsuarioAutenticavel();
    when(this.MockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(new UsuarioAutenticavel());    
    // act
    CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(null, email, "000.000.000-73", "M");
    assertThrows(ServiceException.class, () -> {
      usuarioService.criar(criarUsuarioDTO);
    });
  }
}


// private void validarDadosCriacaoUsuario(CriarUsuarioDTO dados) throws ServiceException {
//   UserDetails usuarioExiste = this.usuarioAutenticavelRepository.findByEmail(dados.usuarioAutenticavel().email());

//   if (usuarioExiste != null) {
//     throw new ServiceException(
//       UsuarioService.class.getName(), 
//       "criar", 
//       "Já existe um usuário com esse email",
//       "Erro ao criar novo usuário",
//       HttpStatus.CONFLICT
//     );
//   }

//   if (dados.cpf() == null && dados.cnpj() == null) {
//     throw new ServiceException(
//       UsuarioService.class.getName(), 
//       "criar", 
//       "Informe ao menos o CPF OU CNPJ",
//       "Erro ao criar novo usuário",
//       HttpStatus.BAD_REQUEST
//     );
//   } else if (dados.cpf() != null && dados.cnpj() != null) {
//     throw new ServiceException(
//       UsuarioService.class.getName(),
//       "criar",
//       "Informe somente CPF ou CNPJ",
//       "Erro ao criar novo usuário",
//       HttpStatus.BAD_REQUEST
//     );
//   }

//   if (UsuarioRole.valueOf((dados.usuarioAutenticavel().role())).equals(UsuarioRole.OFICINA) && dados.cnpj() == null) {
//     throw new ServiceException(
//       UsuarioService.class.getName(),
//       "criar",
//       "Oficina deve ter um CNPJ",
//       "Erro ao criar novo usuário",
//       HttpStatus.BAD_REQUEST
//     );
//   } else if (UsuarioRole.valueOf((dados.usuarioAutenticavel().role())).equals(UsuarioRole.CLIENTE) && dados.cpf() == null) {
//     throw new ServiceException(
//       UsuarioService.class.getName(),
//       "criar",
//       "Cliente deve ter um CPF",
//       "Erro ao criar novo usuário",
//       HttpStatus.BAD_REQUEST
//     );
//   }
// }
