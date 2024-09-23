package br.com.selfmaintenance.integration.app.services.procedimentos;

import br.com.selfmaintenance.app.records.procedimento.CriarProcedimentoDTO;
import br.com.selfmaintenance.app.records.procedimento.DetalhesProcedimentoDTO;
import br.com.selfmaintenance.app.services.procedimento.ProcedimentoService;
import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.infra.repositories.procedimento.ProcedimentoRepository;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcedimentoServiceTest {

    @Mock
    private ProcedimentoRepository procedimentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PrestadorRepository prestadorRepository;

    @InjectMocks
    private ProcedimentoService procedimentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarProcedimentoComSucesso() {
        // Dado um prestador e cliente existentes
        Long prestadorId = 1L;
        Long clienteId = 1L;
        CriarProcedimentoDTO dto = new CriarProcedimentoDTO(prestadorId, clienteId, "Procedimento A", "Descrição A");

        Prestador prestador = new Prestador();
        prestador.setId(prestadorId);
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        when(prestadorRepository.findById(prestadorId)).thenReturn(Optional.of(prestador));
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        Procedimento procedimento = new Procedimento(prestador, cliente, dto.nome(), dto.descricao());
        when(procedimentoRepository.save(any(Procedimento.class))).thenReturn(procedimento);

        // Quando criamos um novo procedimento
        DetalhesProcedimentoDTO resultado = procedimentoService.criar(dto);

        // Então o procedimento é criado com sucesso
        assertNotNull(resultado);
        assertEquals(dto.nome(), resultado.nome());
        assertEquals(dto.descricao(), resultado.descricao());

        verify(procedimentoRepository, times(1)).save(any(Procedimento.class));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExistir() {
        // Dado que o cliente não existe
        Long prestadorId = 1L;
        Long clienteId = 1L;
        CriarProcedimentoDTO dto = new CriarProcedimentoDTO(prestadorId, clienteId, "Procedimento A", "Descrição A");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Quando tentamos criar um procedimento
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            procedimentoService.criar(dto);
        });

        // Então uma exceção é lançada
        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(procedimentoRepository, never()).save(any(Procedimento.class));
    }

    @Test
    void deveLancarExcecaoQuandoPrestadorNaoExistir() {
        // Dado que o prestador não existe
        Long prestadorId = 1L;
        Long clienteId = 1L;
        CriarProcedimentoDTO dto = new CriarProcedimentoDTO(prestadorId, clienteId, "Procedimento A", "Descrição A");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(new Cliente()));
        when(prestadorRepository.findById(prestadorId)).thenReturn(Optional.empty());

        // Quando tentamos criar um procedimento
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            procedimentoService.criar(dto);
        });

        // Então uma exceção é lançada
        assertEquals("Prestador não encontrado.", exception.getMessage());
        verify(procedimentoRepository, never()).save(any(Procedimento.class));
    }

    @Test
    void deveBuscarProcedimentoPorIdComSucesso() {
        // Dado um procedimento existente
        Long procedimentoId = 1L;
        Procedimento procedimento = new Procedimento();
        procedimento.setId(procedimentoId);

        when(procedimentoRepository.getReferenceById(procedimentoId)).thenReturn(procedimento);

        // Quando buscamos pelo ID
        Procedimento resultado = procedimentoService.buscarPorId(procedimentoId);

        // Então o procedimento é retornado com sucesso
        assertNotNull(resultado);
        assertEquals(procedimentoId, resultado.getId());
        verify(procedimentoRepository, times(1)).getReferenceById(procedimentoId);
    }

    @Test
    void deveListarTodosOsProcedimentosComPaginacao() {
        // Dado uma lista de procedimentos
        PageRequest pageRequest = PageRequest.of(0, 10);
        Procedimento procedimento = new Procedimento();
        Page<Procedimento> page = new PageImpl<>(List.of(procedimento));

        when(procedimentoRepository.findAll(pageRequest)).thenReturn(page);

        // Quando listamos os procedimentos
        Page<Procedimento> resultado = procedimentoService.listar(pageRequest);

        // Então a lista de procedimentos é retornada corretamente
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(procedimentoRepository, times(1)).findAll(pageRequest);
    }

}
