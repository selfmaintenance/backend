package br.com.selfmaintenance.presentation.http.controllers;

import br.com.selfmaintenance.app.services.procedimento.ProcedimentoService;
import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/procedimentos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Procedimentos", description = "Operações relacionadas a procedimentos no sistema")
public class ProcedimentoController {

    private final ProcedimentoService procedimentoService;

    public ProcedimentoController(ProcedimentoService procedimentoService) {
        this.procedimentoService = procedimentoService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo procedimento", description = "Cria um novo procedimento no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Procedimento> criarProcedimento(@RequestBody Procedimento procedimento) {
        Procedimento novoProcedimento = procedimentoService.criarProcedimento(procedimento);
        return ResponseEntity.ok(novoProcedimento);
    }

    @GetMapping
    @Operation(summary = "Listar todos os procedimentos", description = "Retorna uma lista de todos os procedimentos cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos retornada com sucesso")
    })
    public ResponseEntity<List<Procedimento>> listarProcedimentos() {
        List<Procedimento> procedimentos = procedimentoService.listarProcedimentos();
        return ResponseEntity.ok(procedimentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar procedimento por ID", description = "Retorna um procedimento específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    public ResponseEntity<Procedimento> buscarProcedimentoPorId(@PathVariable Long id) {
        Optional<Procedimento> procedimento = procedimentoService.buscarProcedimentoPorId(id);
        return procedimento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um procedimento", description = "Atualiza os dados de um procedimento existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    public ResponseEntity<Procedimento> atualizarProcedimento(@PathVariable Long id, @RequestBody Procedimento procedimentoAtualizado) {
        Optional<Procedimento> procedimento = procedimentoService.atualizarProcedimento(id, procedimentoAtualizado);
        return procedimento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um procedimento", description = "Remove um procedimento do sistema com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Procedimento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    public ResponseEntity<Void> deletarProcedimento(@PathVariable Long id) {
        boolean deletado = procedimentoService.deletarProcedimento(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/recursos")
    @Operation(summary = "Adicionar recurso a um procedimento", description = "Associa um recurso a um procedimento existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recurso adicionado ao procedimento"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    public ResponseEntity<Procedimento> adicionarRecurso(@PathVariable Long id, @RequestBody Recurso recurso) {
        Optional<Procedimento> procedimento = procedimentoService.adicionarRecurso(id, recurso);
        return procedimento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
