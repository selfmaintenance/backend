package br.com.selfmaintenance.unity.app.services.usuario.services.recurso;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.app.records.usuario.UsuarioAutenticavelDTO;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.infra.repositories.recurso.RecursoRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import br.com.selfmaintenance.utils.generators.GeradorDocumento;
import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;


@ExtendWith(MockitoExtension.class)
class RecursoServiceTest {

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
    	   // Criação do usuário autenticável
    	    String emailUsuario = "usuario@teste";
    	    UsuarioAutenticavel usuarioAuth = new UsuarioAutenticavel("UsuarioNome", emailUsuario,
    	        "8799999999",
    	        "1234senhaUser",
    	        UsuarioRole.OFICINA
    	    );
    	    // Criação da oficina
    	    String emailOficina = "@testeOficina";
    	    Oficina oficina = new Oficina(1L, usuarioAuth, "Oficina1", "7367263-3829",emailOficina,
    	    		"senhaa12234");
    	    // Criação do recurso associado à oficina
    	    Recurso recurso = new Recurso(oficina, "recurso1", 10, "Descricao recurso 1");

    	    // Mock do repositório para retornar o recurso específico
    	    Mockito.when(recursoRepository.findByOficina_email(emailOficina))
    	    .thenReturn(Collections.singletonList(recurso));
    	    
    	    System.out.println("ANtes de list");
    	    // Chamada ao serviço para listar os recursos
    	    List<RecursoResponseDTO> recursos = recursoService.listar(emailOficina);
    	    
    	    // Verificações
    	    assertEquals(1, recursos.size(), "Deveria retornar apenas um recurso");
    	    
    	    // Verificar os dados do recurso retornado
    	    RecursoResponseDTO recursoResponse = recursos.get(0);
    	    assertEquals("recurso1", recursoResponse.nome(), "Nome do recurso está incorreto");
    	    assertEquals(10, recursoResponse.quantidade(), "Quantidade do recurso está incorreta");
    	    assertEquals("Descricao recurso 1", recursoResponse.descricao(), "Descrição do recurso está incorreta");

    	    // Verificar se o repositório foi chamado corretamente
    	    Mockito.verify(recursoRepository, Mockito.times(1)).findByOficina_email(emailOficina);
   /* 	String emailUsuario = "usuario@teste";
     	UsuarioAutenticavel usuarioAuth = new UsuarioAutenticavel(
     			"UsuarioNome",
     			emailUsuario,
     			"8799999999",
     			"1234senhaUser",
     			UsuarioRole.OFICINA
     			);
    
     	String emailOficina = "@testeOficina";
    
     	Oficina oficina = new Oficina(1L, usuarioAuth, "Oficina1", "7367263-3829",
     			emailOficina, "senhaa12234");
     	
     	Recurso recurso = new Recurso(oficina, "recurso1", 10, "Descricao recurso 1");

     	Mockito.when(recursoRepository.findAll()).thenReturn(Collections.singletonList(recurso));
     	
     	List<RecursoResponseDTO> recursos =  recursoService.listar(emailOficina);
     	
     	assertEquals(1, recursos.size());    */
     }
     
     
     
     
     
   /*  
     @Test
     void listarTodos() {
    	
    	String emailUsuario = "usuario@teste";
    	UsuarioAutenticavel usuarioAuth = new UsuarioAutenticavel(
    			"UsuarioNome",
    			emailUsuario,
    			"8799999999",
    			"1234senhaUser",
    			UsuarioRole.OFICINA
    			);
    	usuarioAutenticavelRepository.save(usuarioAuth);
    	String emailOficina = "@testeOficina";
   
    	Oficina oficina = new Oficina(1L, usuarioAuth, "Oficina1", "7367263-3829", emailOficina, "senhaa12234");
    	
    	oficinaRepository.save(oficina);
    	System.out.println(usuarioAuth.getRole());
    	
    	
    	Recurso recurso = new Recurso(oficina, "recurso1", 10, "Descricao recurso 1");
    	
    	recursoRepository.save(recurso);
    	System.out.println("mockito");
    	Mockito.when(recursoRepository.findAll()).thenReturn(Collections.singletonList(recurso));
    	System.out.println("Depois do  mockito");
    	List<RecursoResponseDTO> recursos =  recursoService.listar(emailOficina);
    	System.out.println("Recursos");
    	
    	assertEquals(1, recursos.size());
    }  */

 
}
