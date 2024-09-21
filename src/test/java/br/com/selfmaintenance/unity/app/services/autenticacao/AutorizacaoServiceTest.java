package br.com.selfmaintenance.unity.app.services.autenticacao;

import br.com.selfmaintenance.app.services.autenticacao.AutorizacaoService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class AutorizacaoServiceTest {

    @Mock
    private UsuarioAutenticavelRepository usuarioRepository;

    @InjectMocks
    private AutorizacaoService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameComSucesso() {
        UsuarioAutenticavel usuarioMock = new UsuarioAutenticavel();

        when(usuarioRepository.findByEmail("usuario@teste.com")).thenReturn(usuarioMock);

        UserDetails userDetails = userService.loadUserByUsername("usuario@teste.com");

        assertNotNull(userDetails);
    }

}