package br.com.selfmaintenance.unity.app.services.usuario;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.github.javafaker.Faker;

import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.app.records.usuario.UsuarioAutenticavelDTO;
import br.com.selfmaintenance.app.services.usuario.UsuarioService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.generators.GeradorDocumento;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
  private final Faker faker =  new Faker();

  @InjectMocks
  private UsuarioService usuarioService;

  @Mock
  private UsuarioAutenticavelRepository mockUsuarioAutenticavelRepository;

  @Mock
  private ClienteRepository mockClienteRepository;

  @Mock
  private OficinaRepository mockOficinaRepository;

  @Test
  @DisplayName("Deve lançar exceção ao tentar criar um usuário com email já existente")
  void emailJaUsado() {
    // arrange
    String email = this.faker.internet().emailAddress();
    CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
      new UsuarioAutenticavelDTO(
        this.faker.name().firstName(), 
        email, 
        this.faker.phoneNumber().cellPhone(), 
        this.faker.internet().password(), 
        UsuarioRole.CLIENTE.getRole()
      ), 
      GeradorDocumento.gerarCPF(), 
      null, 
      "M"
    );
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(new UsuarioAutenticavel());

    // act and assert
    ServiceException exception = assertThrows(ServiceException.class, () -> {
      this.usuarioService.criar(criarUsuarioDTO);
    });
    assertAll("Verificar asserts para exceção de conflito de emails", 
      () -> assertEquals("Já existe um usuário com esse email", exception.getCausa()),
      () -> assertEquals("Erro ao criar novo usuário", exception.getMessage()),
      () -> assertEquals(HttpStatus.CONFLICT, exception.getStatus())
    );
  }

  @Test
  @DisplayName("Deve lançar uma exceção caso o CPF e CNPJ sejam nulos")
  void documentoIdentificadorNaoInformado() {
    // arrange 
    String email = this.faker.internet().emailAddress();
    CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
      new UsuarioAutenticavelDTO(
        this.faker.name().firstName(), 
        email, 
        this.faker.phoneNumber().cellPhone(), 
        this.faker.internet().password(), 
        UsuarioRole.CLIENTE.getRole()
      ), 
      null,
      null, 
      "F"
    );
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(null);

    // act and asserts
    ServiceException exception = assertThrows(ServiceException.class, () -> {
      this.usuarioService.criar(criarUsuarioDTO);
    });
    assertAll("Verificar asserts para exceção de documentos não informados",
      () -> assertEquals("Informe ao menos o CPF OU CNPJ", exception.getCausa()),
      () -> assertEquals("Erro ao criar novo usuário", exception.getMessage()),
      () -> assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus())
    );
  }

  @Test
  @DisplayName("Deve lançar uma exceção caso o CPF e CNPJ sejam informados ao mesmo tempo")
  void documentoIdentificadorInformadoMaisDeUmaVez() {
    // arrange 
    String email = this.faker.internet().emailAddress();
    CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
      new UsuarioAutenticavelDTO(
        this.faker.name().firstName(), 
        email, 
        this.faker.phoneNumber().cellPhone(), 
        this.faker.internet().password(), 
        UsuarioRole.CLIENTE.getRole()
      ), 
      GeradorDocumento.gerarCPF(), 
      GeradorDocumento.gerarCNPJ(), 
      "F"
    );
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(null);

    // act and asserts
    ServiceException exception = assertThrows(ServiceException.class, () -> {
      this.usuarioService.criar(criarUsuarioDTO);
    });
    assertAll("Verificar asserts para exceção de ambos documentos informados",
      () -> assertEquals("Informe somente CPF ou CNPJ", exception.getCausa()),
      () -> assertEquals("Erro ao criar novo usuário", exception.getMessage()),
      () -> assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus())
    );
  }

  @Test
  @DisplayName("Deve lançar uma exceção caso o usuário seja uma oficina e não tenha CNPJ")
  void oficinaSemCNPJ() {
    // arrange 
    String email = this.faker.internet().emailAddress();
    CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
      new UsuarioAutenticavelDTO(
        this.faker.name().firstName(), 
        email, 
        this.faker.phoneNumber().cellPhone(), 
        this.faker.internet().password(), 
        UsuarioRole.OFICINA.getRole()
      ), 
      GeradorDocumento.gerarCPF(), 
      null, 
      "F"
    );
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(null);

    // act and asserts
    ServiceException exception = assertThrows(ServiceException.class, () -> {
      this.usuarioService.criar(criarUsuarioDTO);
    });
    assertAll("Verificar asserts para exceção de Oficina sem CNPJ",
      () -> assertEquals("Oficina deve ter um CNPJ", exception.getCausa()),
      () -> assertEquals("Erro ao criar novo usuário", exception.getMessage()),
      () -> assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus())
    );
  }

  @Test
  @DisplayName("Deve lançar uma exceção caso o usuário seja um Cliente e não tenha CPF")
  void clienteSemCPF() {
    // arrange 
    String email = this.faker.internet().emailAddress();
    CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
      new UsuarioAutenticavelDTO(
        this.faker.name().firstName(), 
        email, 
        this.faker.phoneNumber().cellPhone(), 
        this.faker.internet().password(), 
        UsuarioRole.CLIENTE.getRole()
      ), 
      null, 
      GeradorDocumento.gerarCNPJ(), 
      "F"
    );
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(null);

    // act and asserts
    ServiceException exception = assertThrows(ServiceException.class, () -> {
      this.usuarioService.criar(criarUsuarioDTO);
    });
    assertAll("Verificar asserts para exceção de Cliente sem CPF",
      () -> assertEquals("Cliente deve ter um CPF", exception.getCausa()),
      () -> assertEquals("Erro ao criar novo usuário", exception.getMessage()),
      () -> assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus())
    );
  }

  @Test
  @DisplayName("Deve retornar um mapa com ID do UsuarioAutenticavel e da Oficina caso os dados sejam informados corretamente")
  void oficinaCriadaComSucesso() throws ServiceException {
    // arrange
    Long idUsuarioAutenticavel = this.faker.number().randomNumber();
    Long idOficina = this.faker.number().randomNumber();
    String nomeUsuario = this.faker.name().firstName();
    String email = this.faker.internet().emailAddress();
    String contato = this.faker.phoneNumber().cellPhone();
    String senha = this.faker.internet().password();
    String CNPJ = GeradorDocumento.gerarCNPJ();

    CriarUsuarioDTO dados = new CriarUsuarioDTO(
      new UsuarioAutenticavelDTO(
        nomeUsuario, 
        email, 
        contato, 
        senha, 
        UsuarioRole.OFICINA.getRole()
      ), 
      null, 
      CNPJ, 
      "F"
    );

    UsuarioAutenticavel mockUsuarioAutenticavelSalvo = new UsuarioAutenticavel(
      idUsuarioAutenticavel,
      nomeUsuario,
      email,
      contato,
      senha,
      UsuarioRole.OFICINA
    );
    mockUsuarioAutenticavelSalvo.criptografarSenha();
    Oficina mockOficinaSalva = new Oficina(
      idOficina,
      mockUsuarioAutenticavelSalvo,
      dados.usuarioAutenticavel().nome(),
      dados.cnpj(),
      dados.usuarioAutenticavel().email(),
      dados.usuarioAutenticavel().senha()
    );
    Map<String, Long> mockResposta = Map.of(
      "idOficina", idOficina,
      "idUsuarioAutenticavel", idUsuarioAutenticavel
    );

    Mockito
      .when(this.mockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(null);
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.save(Mockito.any(UsuarioAutenticavel.class)))
      .thenReturn(mockUsuarioAutenticavelSalvo);
    Mockito
      .when(this.mockOficinaRepository.save(Mockito.any(Oficina.class)))
      .thenReturn(mockOficinaSalva);

    // act
    Map<String,Long> resposta = this.usuarioService.criar(dados);

    // assert
    assertEquals(mockResposta, resposta);
  }

  @Test
  @DisplayName("Deve retornar um mapa com ID do UsuarioAutenticavel e do Cliente caso os dados sejam informados corretamente")
  void clienteCriadoComSucesso() throws ServiceException {
    // arrange
    Long idUsuarioAutenticavel = this.faker.number().randomNumber();
    Long idCliente = this.faker.number().randomNumber();
    String nomeUsuario = this.faker.name().firstName();
    String email = this.faker.internet().emailAddress();
    String contato = this.faker.phoneNumber().cellPhone();
    String senha = this.faker.internet().password();
    String CPF = GeradorDocumento.gerarCPF();

    CriarUsuarioDTO dados = new CriarUsuarioDTO(
      new UsuarioAutenticavelDTO(
        nomeUsuario, 
        email, 
        contato, 
        senha, 
        UsuarioRole.CLIENTE.getRole()
      ), 
      CPF, 
      null, 
      "F"
    );

    UsuarioAutenticavel mockUsuarioAutenticavelSalvo = new UsuarioAutenticavel(
      idUsuarioAutenticavel,
      nomeUsuario,
      email,
      contato,
      senha,
      UsuarioRole.CLIENTE
    );
    mockUsuarioAutenticavelSalvo.criptografarSenha();
    Cliente mockClienteSalvo = new Cliente(
      idCliente,
      mockUsuarioAutenticavelSalvo,
      dados.usuarioAutenticavel().nome(),
      dados.cpf(),
      dados.usuarioAutenticavel().email(),
      dados.usuarioAutenticavel().contato(),
      dados.sexo(),
      dados.usuarioAutenticavel().senha()
    );

    Map<String, Long> mockResposta = Map.of(
      "idCliente", idCliente,
      "idUsuarioAutenticavel", idUsuarioAutenticavel
    );
    
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.findByEmail(email))
      .thenReturn(null);
    Mockito
      .when(this.mockUsuarioAutenticavelRepository.save(Mockito.any(UsuarioAutenticavel.class)))
      .thenReturn(mockUsuarioAutenticavelSalvo);
    Mockito
      .when(this.mockClienteRepository.save(Mockito.any(Cliente.class)))
      .thenReturn(mockClienteSalvo);

    // act
    Map<String,Long> resposta = this.usuarioService.criar(dados);

    // assert
    assertEquals(mockResposta, resposta);
  }
} 
