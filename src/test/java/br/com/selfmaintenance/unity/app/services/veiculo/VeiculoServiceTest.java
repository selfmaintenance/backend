package br.com.selfmaintenance.unity.app.services.veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.EditarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;
import br.com.selfmaintenance.app.services.veiculo.VeiculoService;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;
import br.com.selfmaintenance.domain.entities.veiculo.VeiculoTipo;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.veiculo.VeiculoRepository;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {
    @InjectMocks
    private VeiculoService service;
    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    private final String EMAIL = "teste@email";
    private Veiculo veiculo;
    @BeforeEach
    void setUp() {
        startVeiculo();
    }
    @Test
    void criarComSucesso() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        Cliente cliente = new Cliente();
        CriarVeiculoDTO dto = new CriarVeiculoDTO("placa",
                "marca", "modelo", 2024,
                "chassi", "renavamda", "cor", "CARRO");

        when(clienteRepository.findByEmail(EMAIL)).thenReturn(cliente);
        when(veiculoRepository.save(any())).thenReturn(veiculo);

        Map<String, Long> response = service.criar(dto, EMAIL);
        //verifica se o veículo foi criado
        assertNotNull(response);
        //verifica se o id de resposta é o mesmo que o esperado
        assertEquals(1, response.get("idVeiculo"));
        //verifica se o método foi chamado uma vez, somente.
        verify(clienteRepository, times(1)).findByEmail(EMAIL);

    }

    @Test
    void falhaAoEditarVeiculo() {
        Cliente cliente = new Cliente();

        when(clienteRepository.findByEmail(EMAIL)).thenReturn(cliente);
        when(veiculoRepository.findByClienteAndId(cliente, 1L)).thenReturn(null);

        VeiculoResponseDTO response = service.editar(1L, null, EMAIL);

        assertNull(response);
        verify(clienteRepository, times(1)).findByEmail(EMAIL);
        verify(veiculoRepository, times(1)).findByClienteAndId(cliente, 1L);
    }

    @Test
    void sucessoAoEditarVeiculo() {
        Veiculo veiculo = new Veiculo();
        Cliente cliente = new Cliente();
        EditarVeiculoDTO dados = new EditarVeiculoDTO(Optional.of("marca"),
                Optional.of("modelo"),
                Optional.of(2024),
                Optional.of("cor"),
                "CARRO");

        when(clienteRepository.findByEmail(EMAIL)).thenReturn(cliente);
        when(veiculoRepository.findByClienteAndId(cliente, 1L)).thenReturn(veiculo);
        when(veiculoRepository.save(any())).thenReturn(veiculo);

        VeiculoResponseDTO response = service.editar(1L, dados, EMAIL);

        assertNotNull(response);
        verify(clienteRepository, times(1)).findByEmail(EMAIL);
        verify(veiculoRepository, times(1)).findByClienteAndId(cliente, 1L);

    }

    @Test
    void sucessoAoListar() {
        ArrayList<Veiculo> veiculos = new ArrayList();
        veiculos.add(veiculo);

        when(veiculoRepository.findByCliente_email(EMAIL)).thenReturn(veiculos);

        List<VeiculoResponseDTO> response = service.listar(EMAIL);

        assertNotNull(response);
        assertEquals(ArrayList.class, response.getClass());
        verify(veiculoRepository, times(1)).findByCliente_email(EMAIL);

    }

    @Test
    void erroAoBuscar() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findByEmail(EMAIL)).thenReturn(cliente);
        when(veiculoRepository.findByClienteAndId(cliente, 1L)).thenReturn(null);

        VeiculoResponseDTO response = service.buscar(1L, EMAIL);

        assertNull(response);
        verify(clienteRepository, times(1)).findByEmail(EMAIL);
        verify(veiculoRepository, times(1)).findByClienteAndId(cliente, 1L);
    }
    @Test
    void sucessoAoBuscar() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findByEmail(EMAIL)).thenReturn(cliente);
        when(veiculoRepository.findByClienteAndId(cliente, 1L)).thenReturn(veiculo);

        VeiculoResponseDTO response = service.buscar(1L, EMAIL);

        assertNotNull(response);
        verify(clienteRepository, times(1)).findByEmail(EMAIL);
        verify(veiculoRepository, times(1)).findByClienteAndId(cliente, 1L);
    }
    @Test
    void erroAoDeletar() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findByEmail(EMAIL)).thenReturn(cliente);
        when(veiculoRepository.findByClienteAndId(cliente, 1L)).thenReturn(null);

        boolean response = service.deletar(1L, EMAIL);

        assertFalse(response);
        verify(clienteRepository, times(1)).findByEmail(EMAIL);
        verify(veiculoRepository, times(1)).findByClienteAndId(cliente, 1L);
    }

    @Test
    void sucessoAoDeletar(){
        Cliente cliente = new Cliente();
        when(clienteRepository.findByEmail(EMAIL)).thenReturn(cliente);
        when(veiculoRepository.findByClienteAndId(cliente, 1L)).thenReturn(veiculo);

        boolean response = service.deletar(1L, EMAIL);

        assertTrue(response);
        verify(clienteRepository, times(1)).findByEmail(EMAIL);
        verify(veiculoRepository, times(1)).findByClienteAndId(cliente, 1L);
    }

    private void startVeiculo() {
         veiculo = new Veiculo(new Cliente(), "placa", VeiculoTipo.CAMINHAO,
                "marca", "modelo", 2024,
                "chassi", "renavamdele", "cor");
    }
}