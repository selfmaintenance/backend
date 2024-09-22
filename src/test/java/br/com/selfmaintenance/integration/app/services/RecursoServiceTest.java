package br.com.selfmaintenance.integration.app.services;

import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.app.records.usuario.UsuarioAutenticavelDTO;

import com.github.javafaker.Faker;

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
	void criarRecursoEBuscar() throws ServiceException{
		 
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
        assertNotNull(resposta);
        assertTrue(resposta.containsKey("idRecurso"), "ID do recurso não foi retornado.");
        assertNotNull(resposta.get("idRecurso"), "O ID do recurso deve ser não nulo.");
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
	    
	    CriarRecursoDTO recursoDTO = new CriarRecursoDTO("Recurso Teste", 5, "Descrição do recurso");
	    Map<String, Long> respostaCriacao = recursoService.criar(recursoDTO, email);

	   
	    Long idRecurso = respostaCriacao.get("idRecurso");
	    RecursoResponseDTO recurso = recursoService.buscar(idRecurso, email);
	    
	    // Assert 
	    assertNotNull(recurso);
	    assertEquals("Recurso Teste", recurso.nome());
	}
	
	@Test
	@DisplayName("Deve listar todos os recursos de um usuário")
	void testListarRecursosDoUsuario() throws ServiceException {
	    // Criar um usuário de oficina e associá-lo a um recurso
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
	    
	    // Criar um novo recurso para o usuário
	    CriarRecursoDTO recursoDTO1 = new CriarRecursoDTO("Recurso Teste 1", 5, "Descrição do recurso 1");
	    recursoService.criar(recursoDTO1, email);
	    
	    // Criar um segundo recurso para o mesmo usuário
	    CriarRecursoDTO recursoDTO2 = new CriarRecursoDTO("Recurso Teste 2", 3, "Descrição do recurso 2");
	    recursoService.criar(recursoDTO2, email);

	    // Act
	    List<RecursoResponseDTO> recursos = recursoService.listar(email);

	    // Assert
	    //Verificar se os recursos foram corretamente listados
	    assertAll("Verificar lista de recursos do usuário",
	        () -> assertNotNull(recursos),
	        () -> assertEquals(2, recursos.size()),
	        () -> assertTrue(recursos.stream().anyMatch(recurso -> "Recurso Teste 1".equals(recurso.nome()))),
	        () -> assertTrue(recursos.stream().anyMatch(recurso -> "Recurso Teste 2".equals(recurso.nome())))
	    );
	}	
}
