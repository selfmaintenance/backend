package br.com.selfmaintenance.unity.app.services.usuario;

import br.com.selfmaintenance.app.records.prestador.CriarPrestadorDTO;
import br.com.selfmaintenance.app.records.prestador.UsuarioAutenticavelPrestadorDTO;
import br.com.selfmaintenance.app.services.usuario.PrestadorService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestadorServiceTest {

    @InjectMocks
    private PrestadorService prestadorService;
    @Mock
    private UsuarioAutenticavelRepository usuarioAutenticavelRepository;
    @Mock
    private PrestadorRepository prestadorRepository;
    @Mock
    private OficinaRepository oficinaRepository;
    @Mock
    private UserDetails userDetails;
    private final String ERRO_CRIAR_PRESTADOR =  "Erro ao criar novo prestador";
    private CriarPrestadorDTO dados;

    @BeforeEach
    void setUp() {
        startCriarPrestadorDTO();
    }

    @Test
    void erroAoCriar() {
        when(usuarioAutenticavelRepository.findByEmail(dados.usuarioAutenticavelPrestador().email())).thenReturn(userDetails);

        ServiceException serviceException = assertThrows(ServiceException.class, () -> prestadorService.criar(dados, "emailOficina"));

        assertNotNull(serviceException);
        assertEquals(ERRO_CRIAR_PRESTADOR, serviceException.getMensagem());
    }

    @Test
    void sucessoAoCriar() throws ServiceException {
        Prestador prestador = new Prestador();
        prestador.setId(1L);
        UsuarioAutenticavel usuarioAutenticavel = new UsuarioAutenticavel(dados.usuarioAutenticavelPrestador().nome(),
                dados.usuarioAutenticavelPrestador().email(),
                dados.usuarioAutenticavelPrestador().contato(),
                dados.usuarioAutenticavelPrestador().senha(),
                UsuarioRole.PRESTADOR);

        when(usuarioAutenticavelRepository.findByEmail(dados.usuarioAutenticavelPrestador().email())).thenReturn(null);
        when(oficinaRepository.findByEmail(dados.usuarioAutenticavelPrestador().email())).thenReturn(new Oficina());
        when(usuarioAutenticavelRepository.save(any())).thenReturn(usuarioAutenticavel);
        when(prestadorRepository.save(any())).thenReturn(prestador);

        Map<String, Long> response =  prestadorService.criar(dados, dados.usuarioAutenticavelPrestador().email());

        assertNotNull(response);
        assertEquals(1L, response.get("idPrestador"));
        verify(prestadorRepository, times(1)).save(any());
    }

    private void startCriarPrestadorDTO() {
        UsuarioAutenticavelPrestadorDTO usuarioAutenticavelPrestadorDTO = new UsuarioAutenticavelPrestadorDTO("nome", "email", "contato", "40028922");
        dados = new CriarPrestadorDTO(usuarioAutenticavelPrestadorDTO, "88113491848", "Masculino");
    }

}