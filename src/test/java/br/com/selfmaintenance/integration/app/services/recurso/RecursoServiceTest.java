package br.com.selfmaintenance.integration.app.services.recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.app.records.usuario.UsuarioAutenticavelDTO;
import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.app.services.usuario.UsuarioService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.generators.GeradorDocumento;

@SpringBootTest
@ActiveProfiles("integration-test")
class RecursoServiceTest {
	private Faker faker = new Faker();
	
	@Autowired
	RecursoService recursoService;
	
	@Autowired
	UsuarioService usuarioService;

	
	@Test
	@DisplayName("Deve criar um novo recurso e retornar o seu id salvo no banco")
	void criarRecurso() throws ServiceException{
		String email = this.faker.internet().emailAddress();
		
		UsuarioAutenticavelDTO usuarioAutenticavelDTO = new UsuarioAutenticavelDTO(
			this.faker.name().firstName(),
			email,
			this.faker.phoneNumber().cellPhone(),
			this.faker.internet().password(),
			UsuarioRole.OFICINA.getRole()
		);

		// Criar o DTO para criar o usuário
		CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
			usuarioAutenticavelDTO,
			null, 
			GeradorDocumento.gerarCNPJ(), 
			"M" 
		);
		
		// Salvar usuario antes de cria recurso
		usuarioService.criar(criarUsuarioDTO);

		// Criar o DTO de recurso
		CriarRecursoDTO recursoDTO = new CriarRecursoDTO("Recurso Teste", 5, "Descrição do recurso");

		Map<String, Long> resposta = this.recursoService.criar(recursoDTO, email);
		
		// Verificar se o recurso foi criado corretamente
		assertAll("Verificar asserts para retorno da criação de um recurso",
			() -> assertNotNull(resposta),
			() -> assertTrue(resposta.containsKey("idRecurso"), "ID do recurso não foi retornado."),
			() -> assertNotNull(resposta.get("idRecurso"), "O ID do recurso deve ser não nulo.")
		);
  }
	
	@Test
	@DisplayName("Deve buscar recurso por ID e email")
	void testBuscarRecursoPorIdEEmail() throws ServiceException {
		// Criar um usuário e um recurso primeiro (reutilizando o código de criação)
		String email = this.faker.internet().emailAddress();
		UsuarioAutenticavelDTO usuarioAutenticavelDTO = new UsuarioAutenticavelDTO(
			this.faker.name().firstName(),
			email,
			this.faker.phoneNumber().cellPhone(),
			this.faker.internet().password(),
			UsuarioRole.OFICINA.getRole()
		);
		CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
			usuarioAutenticavelDTO,
			null,
			GeradorDocumento.gerarCNPJ(),
			"M"
		);
		usuarioService.criar(criarUsuarioDTO);
		
		CriarRecursoDTO recursoDTO = new CriarRecursoDTO(
			this.faker.name().name(), 
			this.faker.number().numberBetween(0, 100), 
			this.faker.lorem().sentence()
		);

		Map<String, Long> respostaCriacao = recursoService.criar(recursoDTO, email);
		Long idRecurso = respostaCriacao.get("idRecurso");
		RecursoResponseDTO respostaMock = new RecursoResponseDTO(
			idRecurso, 
			recursoDTO.nome(), 
			recursoDTO.descricao(), 
			recursoDTO.quantidade()
		);

		// Act and assert
		RecursoResponseDTO recurso = recursoService.buscar(idRecurso, email);
		assertEquals(respostaMock, recurso);
	}
	
	@Test
	@DisplayName("Deve listar todos os recursos de um usuário")
	void testListarRecursosDoUsuario() throws ServiceException {
		// Arrange
		String email = this.faker.internet().emailAddress();
		UsuarioAutenticavelDTO usuarioAutenticavelDTO = new UsuarioAutenticavelDTO(
			this.faker.name().firstName(),
			email,
			this.faker.phoneNumber().cellPhone(),
			this.faker.internet().password(),
			UsuarioRole.OFICINA.getRole()
		);
		CriarUsuarioDTO criarUsuarioDTO = new CriarUsuarioDTO(
			usuarioAutenticavelDTO,
			null,
			GeradorDocumento.gerarCNPJ(),
			"M"
		);
		usuarioService.criar(criarUsuarioDTO);
		
		List<RecursoResponseDTO> respostaMock = new ArrayList<>();
		CriarRecursoDTO recursoMock01 =  new CriarRecursoDTO(
			this.faker.name().name(), 
			this.faker.number().numberBetween(0, 100),
			this.faker.lorem().sentence()
		);
		Map<String, Long> mapIdRecurso01 = recursoService.criar(recursoMock01, email);
		
		CriarRecursoDTO recursoMock02 =  new CriarRecursoDTO(
			this.faker.name().name(), 
			this.faker.number().numberBetween(0, 100),
			this.faker.lorem().sentence()
		);
		Map<String, Long> mapIdRecurso02 = recursoService.criar(recursoMock02, email);

		respostaMock.add(
			new RecursoResponseDTO(
				mapIdRecurso01.get("idRecurso"),
				recursoMock01.nome(),
				recursoMock01.descricao(),
				recursoMock01.quantidade()
			)
		);
		respostaMock.add(
			new RecursoResponseDTO(
				mapIdRecurso02.get("idRecurso"),
				recursoMock02.nome(),
				recursoMock02.descricao(),
				recursoMock02.quantidade()
			)
		);
		// Act
		List<RecursoResponseDTO> recursos = recursoService.listar(email);

		// Assert
		assertAll("Verificar lista de recursos do usuário",
			() -> assertNotNull(recursos),
			() -> assertEquals(2, recursos.size()),
			() -> assertEquals(respostaMock, recursos)
		);
	}	
}
