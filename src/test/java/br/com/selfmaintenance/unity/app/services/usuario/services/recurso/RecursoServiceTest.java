package br.com.selfmaintenance.unity.app.services.usuario.services.recurso;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
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
	void criarRecurso() { 
		// Assert
		String emailUsuario = this.faker.internet().emailAddress();
		int quantidade = this.faker.number().randomDigit();
		String descricao = this.faker.lorem().paragraph();
		CriarRecursoDTO recurso = new CriarRecursoDTO(emailUsuario, quantidade, descricao);
		// Long id,
		// String nome, 
		// String email, 
		// String contato, 
		// String senha, 
		// UsuarioRole role]

		// usar faker ou definir os atributos diretos
		UsuarioAutenticavel usuarioAutenticavelMock = new UsuarioAutenticavel(

		);
		// Long id,
    // UsuarioAutenticavel usuarioAutenticavel,
    // String nome, 
    // String cnpj, 
    // String email, 
    // String senha 

		// definir oficina
		Oficina oficinaMock = new Oficina(
			this.faker.number().numberBetween(1, 10),

		);

		Map<String, Long> resposta = this.recursoService.criar(recurso, emailUsuario);

		// Act
		
		/**
		 * Voce só deve chamar diretamente o método criar do recurso Service, de resto se usa os mocks
		 * e mockar somente oq vai precisar, nesse caso seria
		 * 
		 * usuarioAutenticavelRepository.findByEmailCustom -> mockar retorno para usar varaivel usuarioAutenticavelMock do escopo atual
		 * oficinaRepository.findByEmail -> mockar retorno para usar variavel oficinaMock do escopo atual
		 * recursoRepository.save -> mockar retorno para usar variavel recursoMock do escopo atual
		 * 
		 * pór fim, relizar um assert com base na resposta obtida em cadeia, por exemplo
		 * 
		 * assertAll("especficar caso de teste", 
		 * () -> assertEquals()
		 * ...)
		 */

	}

}
