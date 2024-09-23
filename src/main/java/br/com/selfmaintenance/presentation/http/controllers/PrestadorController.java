package br.com.selfmaintenance.presentation.http.controllers;

import br.com.selfmaintenance.app.facades.SelfMaintenanceFacade;
import br.com.selfmaintenance.app.records.prestador.CriarPrestadorDTO;
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

import java.util.Map;

/**
 * [PrestadorController] é a classe que representa o controlador de prestadores do sistema.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/oficina/prestador")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Prestadores", description = "Operações relacionadas a prestadores")
public class PrestadorController {
    private final SelfMaintenanceFacade selfMaintenance;

    public PrestadorController(SelfMaintenanceFacade selfMaintenance) {
        this.selfMaintenance = selfMaintenance;
    }

    /**
     * Método que cria um prestador no sistema
     *
     * @param dados
     * @param token
     * @return
     * @throws ServiceException
     */
    @PostMapping
    @Operation(
            summary = "Cria um prestador",
            description = "Este endpoint permite criar um novo prestador no sistema, fornecendo os dados necessários.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Prestador criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou faltando",
                            content = @Content(mediaType = "application/json")),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarPrestadorDTO dados, @RequestHeader("Authorization") String token) throws ServiceException {
        String emailOficina = selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
        Map<String, Long> resposta = this.selfMaintenance.oficina.prestador.criar(dados, emailOficina);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(1, "Prestador criado com sucesso", resposta));
    }

}