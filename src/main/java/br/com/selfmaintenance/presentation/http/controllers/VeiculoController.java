package br.com.selfmaintenance.presentation.http.controllers;

import br.com.selfmaintenance.app.facades.SelfMaintenanceFacade;
import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.EditarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * [VeiculoController] é a classe que representa o controlador de veículos do sistema.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/veiculo")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Veículos", description = "Operações relacionadas a veículos")
public class VeiculoController {
    private final SelfMaintenanceFacade selfMaintenance;

    public VeiculoController(SelfMaintenanceFacade selfMaintenance) {
        this.selfMaintenance = selfMaintenance;
    }

    /**
     * Método que cria um veículo no sistema
     *
     * @param dados
     * @param token
     * @return um mapa com o id do veículo criado
     * @throws ServiceException
     * @see CriarVeiculoDTO
     * @see ApiResponse
     */
    @PostMapping
    @Operation(
            summary = "Cria um veículo",
            description = "Este endpoint permite que um cliente crie um novo veículo associado à sua conta. O cliente deve fornecer os dados necessários do veículo e um token de autenticação válido.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Veículo criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou faltando",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarVeiculoDTO dados, @RequestHeader("Authorization") String token) {
        String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
        Map<String, Long> resposta = this.selfMaintenance.cliente.veiculo.criar(dados, emailCliente);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(1, "Veículo criado com sucesso", resposta));
    }

    /**
     * Método que edita um veículo no sistema
     *
     * @param id
     * @param dados
     * @param token
     * @return um mapa com o id do veículo editado
     * @see EditarVeiculoDTO
     * @see ApiResponse
     */
    @PatchMapping("/{id}")
    @Operation(
            summary = "Edita um veículo",
            description = "Este endpoint permite que um cliente edite os dados de um veículo existente. O cliente deve fornecer o ID do veículo, os novos dados e um token de autenticação válido.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veículo editado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou faltando",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Veículo não encontrado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ApiResponse> editar(
            @PathVariable Long id,
            @RequestBody @Valid EditarVeiculoDTO dados,
            @RequestHeader("Authorization") String token
    ) {
        String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
        VeiculoResponseDTO resposta = this.selfMaintenance.cliente.veiculo.editar(id, dados, emailCliente);
        if (resposta == null) {
            return ResponseEntity.ok(new ApiResponse(-1, "Veículo não encontrado"));
        }

        return ResponseEntity.ok(new ApiResponse(1, "Veículo editado com sucesso", resposta));
    }

    /**
     * Método que lista os veículos do sistema
     *
     * @param token
     * @return uma lista de veículos
     * @see ApiResponse
     */
    @GetMapping
    @Operation(
            summary = "Lista os veículos",
            description = "Este endpoint permite que um cliente liste todos os veículos cadastrados em sua conta. O cliente deve fornecer um token de autenticação válido.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veículos listados com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ApiResponse> listar(@RequestHeader("Authorization") String token) {
        String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
        List<VeiculoResponseDTO> resposta = this.selfMaintenance.cliente.veiculo.listar(emailCliente);
        return ResponseEntity.ok(new ApiResponse(1, "Veículos listados com sucesso", resposta));
    }

    /**
     * Método que busca um veículo no sistema
     *
     * @param id
     * @param token
     * @return um veículo
     * @see ApiResponse
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um veículo",
            description = "Este endpoint permite que um cliente busque os detalhes de um veículo específico cadastrado em sua conta, utilizando o ID do veículo. O cliente deve fornecer um token de autenticação válido.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Veículo não encontrado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ApiResponse> buscar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
        VeiculoResponseDTO resposta = this.selfMaintenance.cliente.veiculo.buscar(id, emailCliente);

        if (resposta == null) {
            return ResponseEntity.ok(new ApiResponse(-1, "Veículo não encontrado"));
        }
        return ResponseEntity.ok(new ApiResponse(1, "Veículo encontrado com sucesso", resposta));
    }

    /**
     * Método que deleta um veículo no sistema
     *
     * @param id
     * @param token
     * @return uma mensagem de sucesso ou erro
     * @see ApiResponse
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um veículo",
            description = "Este endpoint permite que um cliente delete um veículo cadastrado em sua conta, utilizando o ID do veículo. O cliente deve fornecer um token de autenticação válido.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veículo deletado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Veículo não encontrado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ApiResponse> deletar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
        boolean resposta = this.selfMaintenance.cliente.veiculo.deletar(id, emailCliente);
        if (!resposta) {
            return ResponseEntity.ok(new ApiResponse(-1, "Veículo não encontrado"));
        }

        return ResponseEntity.ok(new ApiResponse(1, "Veículo deletado com sucesso"));
    }
}