package br.com.selfmaintenance.app.services.autenticacao;

import br.com.selfmaintenance.app.records.prestador.CriarPrestadorDTO;
import br.com.selfmaintenance.app.records.prestador.UsuarioAutenticavelPrestadorDTO;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    private UsuarioAutenticavel usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "chaveAutenticacao", "chaveSecretaDeTeste");
        ReflectionTestUtils.setField(tokenService, "emissorDaChave", "emissorDeTeste");
        UsuarioAutenticavelPrestadorDTO usuarioAutenticavelPrestadorDTO = new UsuarioAutenticavelPrestadorDTO("nome", "email", "contato", "40028922");
        CriarPrestadorDTO dados = new CriarPrestadorDTO(usuarioAutenticavelPrestadorDTO, "88113491848", "Masculino");
        usuario = new UsuarioAutenticavel(dados.usuarioAutenticavelPrestador().nome(),
                dados.usuarioAutenticavelPrestador().email(),
                dados.usuarioAutenticavelPrestador().contato(),
                dados.usuarioAutenticavelPrestador().senha(),
                UsuarioRole.PRESTADOR);
    }


    @Test
    void extrairEmailUsuarioTokenRetorneNull() {
        String reponse = tokenService.extrairEmailUsuarioToken("token");

        assertNull(reponse);
    }

    @Test
    void criarComSucesso() {
        String token = tokenService.criar(usuario);

        assertNotNull(token);
    }

    @Test
    void validarTokenComSucesso() {
        String token = tokenService.criar(usuario);
        String valido = tokenService.validar(token);

        assertNotNull(valido);
        assertEquals("email", valido);
    }

}