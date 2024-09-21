package br.com.selfmaintenance.unity.app.services.usuario.services.recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.infra.repositories.recurso.RecursoRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;


@ExtendWith(MockitoExtension.class)
class RecursoServiceTest {
	private final Faker faker = new Faker();

	@InjectMocks
	private RecursoService recursoService;
	@Mock
	private RecursoRepository recursoRepository;
	@Mock
	private UsuarioAutenticavelRepository usuarioAutenticavelRepository;
	@Mock
	private PrestadorRepository prestadorRepository;
	@Mock
	private OficinaRepository oficinaRepository;
	@Mock
	private UsuarioAutenticavel usuarioAutenticavel;
     
	
	@Test
	@DisplayName("Deve criar um recurso e retornar um Map com o id do recurso criado")
	void criarRecurso() { 
		// arrange
		String emailUsuario = this.faker.internet().emailAddress();
		int quantidade = this.faker.number().randomDigit();
		String descricao = this.faker.lorem().paragraph();
		CriarRecursoDTO recursoDTO = new CriarRecursoDTO(emailUsuario, quantidade, descricao);
		
		UsuarioAutenticavel usuarioAutenticavelMock = new UsuarioAutenticavel(
			this.faker.number().randomNumber(), "Nome Usuario", emailUsuario, "123456", "senha", UsuarioRole.OFICINA
		);
		
		Oficina oficinaMock = new Oficina(
			this.faker.number().randomNumber(),
			usuarioAutenticavelMock,
			this.faker.name().firstName(),
			"67877778-08900",
			emailUsuario,
			this.faker.internet().password()
		);

		 Recurso recursoMock = new Recurso(
				this.faker.number().randomNumber(), 
				oficinaMock, 
				this.faker.name().firstName(), 
				this.faker.number().numberBetween(0, 99), 
				this.faker.lorem().paragraph()
			); 

		Map<String, Long> respostaMock = Map.of(
				"idRecurso", recursoMock.getId()
				);

		Mockito
		.when(usuarioAutenticavelRepository.findByEmailCustom(emailUsuario)).thenReturn(usuarioAutenticavelMock);
		Mockito
		.when(recursoRepository.save(any(Recurso.class))).thenReturn(recursoMock);
		
		when(oficinaRepository.findByEmail(emailUsuario)).thenReturn(oficinaMock);
		
		// Act and Assert
		Map<String, Long> resposta = this.recursoService.criar(recursoDTO, emailUsuario);
		assertEquals(respostaMock, resposta);
	}
	
	@Test
	@DisplayName("Deve retornar uma lista de RecursoResponseDTO")
	void listar() {
		// Arrange
		String emailUsuario = this.faker.internet().emailAddress();
		UsuarioAutenticavel usuarioAutenticavelMock = new UsuarioAutenticavel(
			this.faker.number().randomNumber(), "Nome Usuario", emailUsuario, "123456", "senha", UsuarioRole.OFICINA
		);
		
		Oficina oficinaMock = new Oficina(
			this.faker.number().randomNumber(),
			usuarioAutenticavelMock,
			this.faker.name().firstName(),
			"67877778-08900",
			emailUsuario,
			this.faker.internet().password()
		);
		List<Recurso> recursosMock = new ArrayList<>();
		
		// Criando recursos
		recursosMock.add(new Recurso(
				 this.faker.number().randomNumber(), 
				 oficinaMock, 
				 this.faker.name().firstName(), 
				 this.faker.number().numberBetween(0, 99), 
				 this.faker.lorem().paragraph()
			)
		);
		recursosMock.add(new Recurso(
				 this.faker.number().randomNumber(), 
				 oficinaMock, 
				 this.faker.name().firstName(), 
				 this.faker.number().numberBetween(0, 99), 
				 this.faker.lorem().paragraph()
			)
		);
	    List<RecursoResponseDTO> respostaMock = new ArrayList<>(); 
	    respostaMock.add(new RecursoResponseDTO(
	    		recursosMock.get(0).getId(), 
	    		recursosMock.get(0).getNome(), 
	    		recursosMock.get(0).getDescricao(), 
	    		recursosMock.get(0).getQuantidade()
	    	)
	    );
	    
	    respostaMock.add(new RecursoResponseDTO(
	    		recursosMock.get(1).getId(), 
	    		recursosMock.get(1).getNome(), 
	    		recursosMock.get(1).getDescricao(), 
	    		recursosMock.get(1).getQuantidade()
	    	)
	    );
		
	    Mockito
			.when(oficinaRepository.findByEmail(emailUsuario))
			.thenReturn(oficinaMock);
		Mockito
			.when(usuarioAutenticavelRepository.findByEmailCustom(emailUsuario))
			.thenReturn(usuarioAutenticavelMock);
		Mockito
			.when(recursoRepository.findByOficina_email(emailUsuario))
			.thenReturn(recursosMock);
		
		// Act and Assert
		List<RecursoResponseDTO> resposta = this.recursoService.listar(emailUsuario);
		assertEquals(respostaMock, resposta);
	}
	
	@Test
	@DisplayName("Deve retornar true se o recurso deletado")
	void deletarRecursoTest() {
		// Arrange

		String emailUsuario = this.faker.internet().emailAddress();
		UsuarioAutenticavel usuarioAutenticavelMock = new UsuarioAutenticavel(
				 1L, "Nome Usuario", emailUsuario, "123456", "senha", UsuarioRole.OFICINA
		);
		
		Oficina oficinaMock = new Oficina(
			1L,
			usuarioAutenticavelMock,
			this.faker.name().firstName(),
			"67877778-08900",
			emailUsuario,
			this.faker.internet().password()
		);

		 Recurso recursoMock = new Recurso(
				this.faker.number().randomNumber(), 
				oficinaMock, 
				this.faker.name().firstName(), 
				this.faker.number().numberBetween(0, 99), 
				this.faker.lorem().paragraph()
			); 
		 
			Mockito
				.when(usuarioAutenticavelRepository.findByEmailCustom(emailUsuario))
				.thenReturn(usuarioAutenticavelMock);

			Mockito
				.when(oficinaRepository.findByEmail(emailUsuario))
				.thenReturn(oficinaMock);

			Mockito
				.when(recursoRepository.findByOficinaAndId(oficinaMock, recursoMock.getId()))
				.thenReturn(recursoMock);
		
	    // Act
	    boolean resposta = this.recursoService.deletar(recursoMock.getId(), emailUsuario);
	    
	    // Assert
	    assertTrue(resposta);
	}
	
	@Test
	@DisplayName("Deve retornar o recurso editado e comparar com o recurso atualizado")
	void editarRecursoTest() {
		// Arrange
		String emailUsuario = this.faker.internet().emailAddress();
		
		UsuarioAutenticavel usuarioAutenticavelMock = new UsuarioAutenticavel(
			this.faker.number().randomNumber(), "Nome Usuario", emailUsuario, "123456", "senha", UsuarioRole.OFICINA
		);
		
		Oficina oficinaMock = new Oficina(
			this.faker.number().randomNumber(),
			usuarioAutenticavelMock,
			this.faker.name().firstName(),
			"67877778-08900",
			emailUsuario,
			this.faker.internet().password()
		);

		// Criando o recurso 
		Recurso recursoExistenteMock = new Recurso(
			this.faker.number().randomNumber(), 
			oficinaMock, 
			this.faker.name().firstName(), 
			this.faker.number().numberBetween(0, 99), 
			this.faker.lorem().paragraph()
		);
		
		EditarRecursoDTO recursoDTO = new EditarRecursoDTO(
			Optional.of("Recurso atualizado"), 
			Optional.of(50), 
			Optional.of("Descrição do recurso atualizado")
		);
		
		Recurso recursoAtualizadoMock = new Recurso(
			recursoExistenteMock.getId(), 
			oficinaMock, 
			recursoDTO.nome().get(), 
			recursoDTO.quantidade().get(), 
			recursoDTO.descricao().get()
		);

		Mockito
			.when(usuarioAutenticavelRepository.findByEmailCustom(emailUsuario))
			.thenReturn(usuarioAutenticavelMock);
		Mockito
			.when(oficinaRepository.findByEmail(emailUsuario))
			.thenReturn(oficinaMock);
		Mockito
			.when(recursoRepository.findByOficinaAndId(oficinaMock, recursoExistenteMock.getId()))
			.thenReturn(recursoExistenteMock);
		Mockito
			.when(recursoRepository.save(any(Recurso.class)))
			.thenReturn(recursoAtualizadoMock);
		
		// Act
		RecursoResponseDTO resposta = this.recursoService.editar(recursoExistenteMock.getId(), recursoDTO, emailUsuario);
		
		// Assert
		assertNotNull(resposta);
		assertEquals(recursoAtualizadoMock.getId(), resposta.id());
		assertEquals(recursoAtualizadoMock.getNome(), resposta.nome());
		assertEquals(recursoAtualizadoMock.getQuantidade(), resposta.quantidade());
		assertEquals(recursoAtualizadoMock.getDescricao(), resposta.descricao());
	}
	
	@Test
	@DisplayName("Deve retornar o recurso correspondente")
	void buscarRecursoTest() {
		// Arrange
		String emailUsuario = this.faker.internet().emailAddress();
		
		UsuarioAutenticavel usuarioAutenticavelMock = new UsuarioAutenticavel(
			this.faker.number().randomNumber(), "Nome Usuario", emailUsuario, "123456", "senha", UsuarioRole.OFICINA
		);
		
		Oficina oficinaMock = new Oficina(
			this.faker.number().randomNumber(),
			usuarioAutenticavelMock,
			this.faker.name().firstName(),
			"67877778-08900",
			emailUsuario,
			this.faker.internet().password()
		);

		Recurso recursoExistenteMock = new Recurso(
			this.faker.number().randomNumber(), 
			oficinaMock, 
			this.faker.name().firstName(), 
			this.faker.number().numberBetween(0, 99), 
			this.faker.lorem().paragraph()
		);

		Mockito
			.when(usuarioAutenticavelRepository.findByEmailCustom(emailUsuario))
			.thenReturn(usuarioAutenticavelMock);
		Mockito
			.when(oficinaRepository.findByEmail(emailUsuario))
			.thenReturn(oficinaMock);
		Mockito
			.when(recursoRepository.findByOficinaAndId(oficinaMock, recursoExistenteMock.getId()))
			.thenReturn(recursoExistenteMock);
		
		// Act
		RecursoResponseDTO resposta = this.recursoService.buscar(recursoExistenteMock.getId(), emailUsuario);
		
		// Assert
		assertNotNull(resposta);
		assertEquals(recursoExistenteMock.getId(), resposta.id());
		assertEquals(recursoExistenteMock.getNome(), resposta.nome());
		assertEquals(recursoExistenteMock.getQuantidade(), resposta.quantidade());
		assertEquals(recursoExistenteMock.getDescricao(), resposta.descricao());
	}
}
