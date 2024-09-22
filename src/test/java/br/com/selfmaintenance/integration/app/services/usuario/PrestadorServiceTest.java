package br.com.selfmaintenance.integration.app.services.usuario;

import br.com.selfmaintenance.app.records.prestador.CriarPrestadorDTO;
import br.com.selfmaintenance.app.records.prestador.UsuarioAutenticavelPrestadorDTO;
import br.com.selfmaintenance.app.services.usuario.prestador.PrestadorService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration-test")
public class PrestadorServiceTest {

    private final Faker faker = new Faker();
    @Autowired
    private PrestadorService service;
    @Autowired
    private UsuarioAutenticavelRepository usuarioAutenticavelRepository;
    @Autowired
    private PrestadorRepository prestadorRepository;
    @Autowired
    private OficinaRepository oficinaRepository;

    private UsuarioAutenticavel usuarioAutenticavel;
    private Oficina oficina;

    private String email;
    private String emailOficina;

    @BeforeEach
    void setUp() {
        email = faker.internet().emailAddress();
        emailOficina = faker.internet().emailAddress();
        criarUsuarioAuteticavel();
        criarOficina();

    }

    @Test
    void criarPrestadorComSucesso() throws ServiceException {
        String emailCriarComSucesso = faker.internet().emailAddress();
        UsuarioAutenticavelPrestadorDTO dto = new UsuarioAutenticavelPrestadorDTO("nome1", emailCriarComSucesso, "contato1", "400289221");
        CriarPrestadorDTO dados = new CriarPrestadorDTO(dto, "88113491841", "Masculino");

        Map<String, Long> response = service.criar(dados, emailOficina);

        assertNotNull(response);
        assertTrue(response.containsKey("idPrestador"));
    }

    @Test
    void erroAoCriarPrestador() throws ServiceException {
        UsuarioAutenticavel usuario = usuarioAutenticavelRepository.findByEmailCustom(email);
        UsuarioAutenticavelPrestadorDTO usuarioAutenticavelPrestadorDTO = new UsuarioAutenticavelPrestadorDTO("nome", usuario.getEmail(), "contato", "40028922");
        CriarPrestadorDTO dados = new CriarPrestadorDTO(usuarioAutenticavelPrestadorDTO, "88113491848", "Masculino");

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            this.service.criar(dados, email);
        });

        assertNotNull(exception);
        assertEquals("Já existe um usuário com esse email", exception.getCausa());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());

    }


    private void criarUsuarioAuteticavel() {
        usuarioAutenticavel = new UsuarioAutenticavel("teste", email, "contato", "74587458", UsuarioRole.PRESTADOR);
        usuarioAutenticavelRepository.save(usuarioAutenticavel);
    }

    private void criarOficina() {
        oficina = new Oficina(usuarioAutenticavel, "nome", "12345678942", emailOficina, "12345678");
        oficinaRepository.save(oficina);
    }

}