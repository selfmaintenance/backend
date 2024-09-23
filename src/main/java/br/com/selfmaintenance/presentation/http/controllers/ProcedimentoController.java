package br.com.selfmaintenance.presentation.http.controllers;

import br.com.selfmaintenance.app.records.procedimento.CriarProcedimentoDTO;
import br.com.selfmaintenance.app.records.procedimento.DetalhesProcedimentoDTO;
import br.com.selfmaintenance.app.services.procedimento.ProcedimentoService;
import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.infra.repositories.recurso.RecursoRepository;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/procedimentos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Procedimentos", description = "Operações relacionadas a procedimentos no sistema")
public class ProcedimentoController {

    private final ProcedimentoService procedimentoService;
    private final ClienteRepository clienteRepository;
    private final PrestadorRepository prestadorRepository;
    private final RecursoService recursoService;
    private final RecursoRepository recursoRepository;

    public ProcedimentoController(ProcedimentoService procedimentoService, ClienteRepository clienteRepository, PrestadorRepository prestadorRepository, RecursoService recursoService, RecursoRepository recursoRepository) {
        this.procedimentoService = procedimentoService;
        this.clienteRepository = clienteRepository;
        this.prestadorRepository = prestadorRepository;
        this.recursoService = recursoService;
        this.recursoRepository = recursoRepository;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo procedimento", description = "Cria um novo procedimento no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    public ResponseEntity<DetalhesProcedimentoDTO> criar(@RequestBody CriarProcedimentoDTO dados) {
        DetalhesProcedimentoDTO detalhesProcedimentoDTO = procedimentoService.criar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhesProcedimentoDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os procedimentos", description = "Retorna uma lista de todos os procedimentos cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    public ResponseEntity<Page<DetalhesProcedimentoDTO>> listar(Pageable pageable) {
        var page = procedimentoService.listar(pageable).map(DetalhesProcedimentoDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar procedimento por ID", description = "Retorna um procedimento específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    public ResponseEntity<DetalhesProcedimentoDTO> buscarPorId(@PathVariable Long id) {
        var procedimento = procedimentoService.buscarPorId(id);
        if (procedimento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DetalhesProcedimentoDTO(procedimento));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Cancelar um procedimento", description = "Marca um procedimento como cancelado com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Procedimento cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        var procedimento = procedimentoService.buscarPorId(id);
        procedimento.cancelar();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar um procedimento", description = "Atualiza os dados de um procedimento existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    public ResponseEntity<DetalhesProcedimentoDTO> atualizarProcedimento(
            @PathVariable Long id,
            @RequestBody CriarProcedimentoDTO dados) {

        // Buscar o procedimento existente pelo ID
        Procedimento procedimento = procedimentoService.buscarPorId(id);

        if (procedimento == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualizar os campos do procedimento com os dados do DTO
        procedimento.setNome(dados.nome());
        procedimento.setDescricao(dados.descricao());

        // Verifica se o cliente existe
        Optional<Cliente> clienteOptional = clienteRepository.findById(dados.clienteId());
        if (clienteOptional.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        procedimento.setCliente(clienteOptional.get());

        // Verifica se o prestador existe
        Optional<Prestador> prestadorOptional = prestadorRepository.findById(dados.prestadorId());
        if (prestadorOptional.isEmpty()) {
            throw new IllegalArgumentException("Prestador não encontrado.");
        }
        procedimento.setPrestador(prestadorOptional.get());

        // Retornar o DTO com os detalhes atualizados
        return ResponseEntity.ok(new DetalhesProcedimentoDTO(procedimento));
    }

    @PostMapping("/aceitar/{id}")
    @Transactional
    @Operation(summary = "Aceitar um procedimento", description = "Marca um procedimento como aceito com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Procedimento aceito com sucesso"),
            @ApiResponse(responseCode = "400", description = "Data do agendamento incorreta"),
            @ApiResponse(responseCode = "404", description = "Procedimento cancelado ou não encontrado")
    })
    public ResponseEntity<String> aceitar(@PathVariable Long id, @RequestBody LocalDateTime dataAgendamento) {
        var procedimento = procedimentoService.buscarPorId(id);

        if (dataAgendamento.isBefore(LocalDateTime.now())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("A data do agendamento deve ser no futuro");
        }

        if (!procedimento.isAtivo()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Procedimento cancelado");
        }

        procedimento.aceitar(dataAgendamento);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Procedimento aceito com sucesso");
    }

    @PostMapping("/finalizar/{id}")
    @Transactional
    @Operation(summary = "Finaliza um procedimento", description = "Marca um procedimento como finalizado com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Procedimento finalizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Procedimento não aceito"),
            @ApiResponse(responseCode = "404", description = "Procedimento cancelado ou não encontrado")
    })
    public ResponseEntity<String> finalizar(@PathVariable Long id) {
        var procedimento = procedimentoService.buscarPorId(id);
        if (procedimento.getDataAceitacao() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Procedimento ainda não aceito");
        }
        if (!procedimento.isAtivo()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Procedimento cancelado");
        }
        procedimento.finalizar();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Procedimento finalizado com sucesso");
    }

    @PostMapping("/adicionar-recurso/{procedimentoId}")
    @Transactional
    @Operation(summary = "Adiciona um recurso a um procedimento", description = "Vincula um recurso a um procedimento com base nos IDs fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Recurso adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Procedimento não aceito ou já finalizado"),
            @ApiResponse(responseCode = "404", description = "Procedimento ou recurso não encontrado")
    })
    public ResponseEntity<String> adicionarRecurso(@PathVariable Long procedimentoId, @RequestBody Long recursoId) {
        var procedimento = procedimentoService.buscarPorId(procedimentoId);
        var recurso = recursoRepository.getReferenceById(recursoId);

        if (procedimento == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Procedimento não encontrado");
        }

        if (procedimento.getDataAceitacao() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Procedimento ainda não aceito");
        }

        if (!procedimento.isAtivo()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Procedimento finalizado ou cancelado");
        }

        procedimento.adicionarRecurso(recurso);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Recurso adicionado com sucesso");
    }

}
