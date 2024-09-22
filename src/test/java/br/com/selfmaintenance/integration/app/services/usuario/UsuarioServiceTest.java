package br.com.selfmaintenance.integration.app.services.usuario;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.app.records.usuario.UsuarioAutenticavelDTO;
import br.com.selfmaintenance.app.services.usuario.UsuarioService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.generators.GeradorDocumento;

@SpringBootTest
@ActiveProfiles("integration-test")
public class UsuarioServiceTest {
  private final Faker faker = new Faker();

  @Autowired
  private UsuarioService usuarioService;

  @Test
  @DisplayName("Deve criar um usuário do tipo Cliente")
  void testCriarUsuarioTipoCliente() throws ServiceException {
    // arrange
    UsuarioAutenticavelDTO usuarioAutenticavelDTO = new UsuarioAutenticavelDTO(
      this.faker.name().firstName(), 
      this.faker.internet().emailAddress(), 
      this.faker.phoneNumber().cellPhone(), 
      this.faker.internet().password(), 
      UsuarioRole.CLIENTE.getRole()
    );

    CriarUsuarioDTO dados = new CriarUsuarioDTO(
      usuarioAutenticavelDTO, 
      GeradorDocumento.gerarCPF(),
      null,
      "M"
    );

    // act
    Map<String, Long> resposta = this.usuarioService.criar(dados);

    // assert
    assertAll("Verificar asserts para criação de usuário do tipo Cliente",
      () -> assertNotNull(resposta.get("idCliente")),
      () -> assertNotNull(resposta.get("idUsuarioAutenticavel")),
      () -> assertTrue(resposta.get("idCliente") instanceof Long),
      () -> assertTrue(resposta.get("idUsuarioAutenticavel") instanceof Long)
    );
  }

  @Test
  @DisplayName("Deve criar um usuário do tipo Oficina")
  void testCriarUsuarioTipoOficina() throws ServiceException {
    // arrange
    UsuarioAutenticavelDTO usuarioAutenticavelDTO = new UsuarioAutenticavelDTO(
      this.faker.name().firstName(), 
      this.faker.internet().emailAddress(), 
      this.faker.phoneNumber().cellPhone(), 
      this.faker.internet().password(), 
      UsuarioRole.OFICINA.getRole()
    );

    CriarUsuarioDTO dados = new CriarUsuarioDTO(
      usuarioAutenticavelDTO, 
      null,
      GeradorDocumento.gerarCNPJ(),
      "M"
    );

    // act
    Map<String, Long> resposta = this.usuarioService.criar(dados);

    // assert
    assertAll("Verificar asserts para criação de usuário do tipo Cliente",
      () -> assertNotNull(resposta.get("idOficina")),
      () -> assertNotNull(resposta.get("idUsuarioAutenticavel")),
      () -> assertTrue(resposta.get("idOficina") instanceof Long),
      () -> assertTrue(resposta.get("idUsuarioAutenticavel") instanceof Long)
    );
  }
}
