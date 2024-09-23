package br.com.selfmaintenance.presentation.http.controllers;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.selfmaintenance.app.facades.SelfMaintenanceFacade;
import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import jakarta.validation.Valid;

/**
 * [RecursoController] é a classe que representa o controlador de recursos do sistema.
 * 
 * @version 1.0.0
 */
@RestController
@RequestMapping("/recurso")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Recursos", description = "Operações relacionadas a recursos")
public class RecursoController {
  private final SelfMaintenanceFacade selfMaintenance;

  public RecursoController(SelfMaintenanceFacade selfMaintenance) {
    this.selfMaintenance = selfMaintenance;
  }

  /**
   * Método que cria um recurso no sistema
   * 
   * @param dados
   * @param token
   * 
   * @see CriarRecursoDTO
   * @see ApiResponse
   * 
   * @return um mapa com o id do recurso criado
   */
  @PostMapping
  @Operation(
          summary = "Cria um recurso",
          description = "Este endpoint permite que um prestador crie um novo recurso no sistema, fornecendo os dados necessários.",
          responses = {
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ApiResponse.class))),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou faltando",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                          content = @Content(mediaType = "application/json"))
          }
  )
  public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarRecursoDTO dados, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    Map<String, Long> resposta = this.selfMaintenance.prestador.recurso.criar(dados, emailPrestador);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ApiResponse(1, "Recurso criado com sucesso", resposta));
  }

  /**
   * Método que edita um recurso no sistema
   * 
   * @param id
   * @param dados
   * @param token
   * 
   * @see EditarRecursoDTO
   * @see ApiResponse
   * 
   * @return um mapa com o id do recurso editado
   */
  @PatchMapping("/{id}")
  @Operation(
          summary = "Edita um recurso",
          description = "Este endpoint permite que um prestador edite um recurso existente, fornecendo o ID e os novos dados.",
          responses = {
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recurso editado com sucesso",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ApiResponse.class))),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos ou faltando",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                          content = @Content(mediaType = "application/json"))
          }
  )
  public ResponseEntity<ApiResponse> editar(@PathVariable Long id, @RequestBody @Valid EditarRecursoDTO dados, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    RecursoResponseDTO resposta = this.selfMaintenance.prestador.recurso.editar(id, dados, emailPrestador);
    if (resposta == null) {
      return ResponseEntity.ok(new ApiResponse(-1, "Recurso não encontrado"));
    }
    return ResponseEntity.ok(new ApiResponse(1, "Recurso editado com sucesso", resposta));
  }

  /**
   * Método que lista os recursos do sistema
   * 
   * @param token
   * 
   * @see ApiResponse
   * 
   * @return uma lista de recursos
   */
  @Operation(
          summary = "Lista os recursos",
          description = "Este endpoint permite que um prestador liste todos os recursos cadastrados.",
          responses = {
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recursos listados com sucesso",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ApiResponse.class))),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                          content = @Content(mediaType = "application/json"))
          }
  )
  @GetMapping
  public ResponseEntity<ApiResponse> listar(@RequestHeader("Authorization") String token) {
    String emailPrestador = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    List<RecursoResponseDTO> resposta = this.selfMaintenance.prestador.recurso.listar(emailPrestador);
    return ResponseEntity.ok(new ApiResponse(1, "Recursos listados com sucesso", resposta));
  }

  /**
   * Método que busca um recurso no sistema
   * 
   * @param id
   * @param token
   * 
   * @see ApiResponse
   * 
   * @return um mapa com o id do recurso buscado
   */
  @GetMapping("/{id}")
  @Operation(
          summary = "Busca um recurso",
          description = "Este endpoint permite que um prestador busque um recurso específico utilizando o ID.",
          responses = {
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ApiResponse.class))),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                          content = @Content(mediaType = "application/json"))
          }
  )
  public ResponseEntity<ApiResponse> buscar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    RecursoResponseDTO resposta = this.selfMaintenance.prestador.recurso.buscar(id, emailPrestador);
    if (resposta == null) {
      return ResponseEntity.ok(new ApiResponse(-1, "Recurso não encontrado"));
    }
    return ResponseEntity.ok(new ApiResponse(1, "Recurso encontrado com sucesso", resposta));    
  }

  /**
   * Método que deleta um recurso no sistema
   * 
   * @param id
   * @param token
   * 
   * @see ApiResponse
   * 
   * @return um mapa com o id do recurso deletado
   */
  @DeleteMapping("/{id}")
  @Operation(
          summary = "Deleta um recurso",
          description = "Este endpoint permite que um prestador delete um recurso existente utilizando o ID.",
          responses = {
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recurso deletado com sucesso",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ApiResponse.class))),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Não autorizado",
                          content = @Content(mediaType = "application/json")),
                  @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                          content = @Content(mediaType = "application/json"))
          }
  )
  public ResponseEntity<ApiResponse> deletar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    boolean resposta = this.selfMaintenance.prestador.recurso.deletar(id, emailPrestador);
    if (!resposta) {
      return ResponseEntity.ok(new ApiResponse(-1, "Recurso não encontrado"));
    }
    return ResponseEntity.ok(new ApiResponse(1, "Recurso deletado com sucesso"));
  }
}
