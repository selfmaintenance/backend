package br.com.selfmaintenance.presentation.http.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.selfmaintenance.app.facades.SelfMaintenanceFacade;
import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * [UsuarioController] é a classe que representa o controlador de usuários do sistema.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
public class UsuarioController {
  private final SelfMaintenanceFacade selfMaintenance;

  public UsuarioController(SelfMaintenanceFacade selfMaintenance) {
    this.selfMaintenance = selfMaintenance;
  }

  /**
   * Método que cria um usuário no sistema
   *
   * @param dados
   * @return um mapa com o id do usuário criado
   * @throws ServiceException
   * @see CriarUsuarioDTO
   */
  @Operation(
    summary = "Cria um usuário",
    description = "Este endpoint permite criar um novo usuário no sistema, fornecendo os dados necessários.",
    responses = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
        content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou faltando",
        content = @Content(mediaType = "application/json")),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
        content = @Content(mediaType = "application/json"))
    }
  )
  @PostMapping
  public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarUsuarioDTO dados) throws ServiceException {
      Map<String, Long> respostaCriacao = this.selfMaintenance.usuarioBase.criar(dados);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(new ApiResponse(1, "Usuário criado com sucesso", respostaCriacao));
  }
}

