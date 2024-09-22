package br.com.selfmaintenance.integration.app.services.veiculo;

import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.EditarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;
import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.app.services.veiculo.VeiculoService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;
import br.com.selfmaintenance.domain.entities.veiculo.VeiculoTipo;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.OficinaRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import br.com.selfmaintenance.infra.repositories.veiculo.VeiculoRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration-test")
public class VeiculoServiceTest {

    private Faker faker = new Faker();

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UsuarioAutenticavelRepository usuarioAutenticavelRepository;

    private Cliente cliente;
    private UsuarioAutenticavel usuarioAutenticavel;
    private Veiculo veiculo;
    private String email;

    @BeforeEach
    void setUp() {
        email = faker.internet().emailAddress();
        criarUsuarioAuteticavel();
        criarClieente();
        criarVeiculo();

    }

    @Test
    void editarComSucesso() {
        Cliente cliente1 = clienteRepository.findByEmail(email);
        List<Veiculo> veiculos =  veiculoRepository.findByCliente_email(email);
        EditarVeiculoDTO dados = new EditarVeiculoDTO(Optional.of("marca"),
                Optional.of("modelo"),
                Optional.of(2024),
                Optional.of("verde"),
                "CARRO");

        VeiculoResponseDTO response = veiculoService.editar(veiculos.get(0).getId(), dados, cliente1.getEmail());

        assertNotNull(response);
        assertEquals("verde", response.cor());
    }

    @Test
    void criarComSucesso() {
        CriarVeiculoDTO dto = new CriarVeiculoDTO("placa",
                "marca", "modelo", 2024,
                "chassi", "renavamda", "cor", "CARRO");

        Map<String, Long> resultado = veiculoService.criar(dto, email);

        assertNotNull(resultado);
        assertTrue(resultado.containsKey("idVeiculo"));
        assertEquals(4, resultado.get("idVeiculo"));
    }

    @Test
    void listarComSucesso() {
        List<VeiculoResponseDTO> response = veiculoService.listar(email);

        assertNotNull(response);
        assertEquals("preto", response.get(0).cor());
        assertEquals(1998, response.get(0).ano());
    }



    @Test
    void editarRetornaNull() {
        VeiculoResponseDTO response = veiculoService.editar(9L, null, email);

        assertNull(response);
    }

    @Test
    void buscarComSucesso() {
        VeiculoResponseDTO response = veiculoService.buscar(2L, email);

        assertNotNull(response);
        assertEquals(2L, response.id());
    }

    @Test
    void retornaFalseQuandoNaoConseguirDeletar() {
        Boolean response = veiculoService.deletar(9L, email);

        assertFalse(response);
    }

    @Test
    void deletarComSucesso() {
        UsuarioAutenticavel autenticavel = new UsuarioAutenticavel("teste1", "email", "contato1", "745874581", UsuarioRole.CLIENTE);
        usuarioAutenticavelRepository.save(autenticavel);
        cliente = new Cliente(autenticavel, "cliente", "12345789a", "email", "7474742", "masculino", "78458745");
        clienteRepository.save(cliente);
        veiculo = new Veiculo(cliente, "placa", VeiculoTipo.TRATOR, "marca", "modelo", 1998, "chassi", "12345678910", "preto");
        veiculoRepository.save(veiculo);
        List<Veiculo> veiculos =  veiculoRepository.findByCliente_email("email");

        Boolean response = veiculoService.deletar(veiculos.get(0).getId(), "email");

        assertTrue(response);
    }

    @Test
    void buscarRetornaNull() {
        VeiculoResponseDTO response = veiculoService.buscar(9L, email);

        assertNull(response);
    }

    private void criarClieente() {
        cliente = new Cliente(usuarioAutenticavel, "prestador", "12345789", email, "747474", "masculino", "78458745");
        clienteRepository.save(cliente);
    }

    private void criarUsuarioAuteticavel() {
        usuarioAutenticavel = new UsuarioAutenticavel("teste", email, "contato", "74587458", UsuarioRole.PRESTADOR);
        usuarioAutenticavelRepository.save(usuarioAutenticavel);
    }

    private void criarVeiculo() {
        veiculo = new Veiculo(cliente, "placa", VeiculoTipo.TRATOR, "marca", "modelo", 1998, "chassi", "12345678910", "preto");
        veiculoRepository.save(veiculo);
    }
}