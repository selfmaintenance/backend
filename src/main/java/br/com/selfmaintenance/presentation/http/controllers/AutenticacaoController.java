package br.com.selfmaintenance.presentation.http.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.selfmaintenance.app.facades.SelfMaintenanceFacade;
import br.com.selfmaintenance.app.records.AutenticacaoDTO;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * [AutenticacaoController] é a classe que representa o controlador de autenticação do sistema.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Operações relacionadas à autenticação de usuários")
public class AutenticacaoController {
  private final SelfMaintenanceFacade selfMaintenance;

  public AutenticacaoController(
    SelfMaintenanceFacade selfMaintenance
  ) {
    this.selfMaintenance = selfMaintenance;
  }

  /**
   * Método que autentica um usuário no sistema
   *
   * @param dados
   * @return
   * @throws ResponseStatusException
   */
  @PostMapping("/login")
  @Operation(
    summary = "Autentica um usuário",
    description = "Este endpoint permite que um usuário se autentique no sistema, fornecendo suas credenciais.",
    responses = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso",
    content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos",
      content = @Content(mediaType = "application/json")),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(mediaType = "application/json"))
    }
  )
  public ResponseEntity<ApiResponse> login(@RequestBody @Valid AutenticacaoDTO dados) throws ResponseStatusException {
    var usuarioNomeSenha = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
    if (this.selfMaintenance.autenticacao.autorizacao.loadUserByUsername(dados.email()) == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
    }

    var auth = this.selfMaintenance.autenticacao.gerenciador.authenticate(usuarioNomeSenha);
    var token = this.selfMaintenance.autenticacao.token.criar((UsuarioAutenticavel) auth.getPrincipal());
    Map<String, String> resposta = Map.of("token", token);
    return ResponseEntity.ok(new ApiResponse(1, "Usuário autenticado com sucesso", resposta));
  }
}

