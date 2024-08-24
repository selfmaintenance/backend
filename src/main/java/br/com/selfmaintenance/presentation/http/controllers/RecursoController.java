package br.com.selfmaintenance.presentation.http.controllers;

import java.util.List;
import java.util.Map;

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

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;
import br.com.selfmaintenance.app.services.autenticacao.TokenService;
import br.com.selfmaintenance.app.services.recurso.RecursoService;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/recurso")
public class RecursoController {
  private final RecursoService recursoService;
  private final TokenService tokenService;

  public RecursoController(RecursoService recursoService, TokenService tokenService) {
    this.recursoService = recursoService;
    this.tokenService = tokenService;
  }

  @PostMapping("/")
  public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarRecursoDTO dados, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.tokenService.extrairEmailUsuarioToken(token);
    Map<String, Long> resposta = this.recursoService.criar(dados, emailPrestador);
    return ResponseEntity.ok(new ApiResponse(1, "Recurso criado com sucesso", resposta));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse> editar(@PathVariable Long id, @RequestBody @Valid EditarRecursoDTO dados, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.tokenService.extrairEmailUsuarioToken(token);
    RecursoResponseDTO resposta = this.recursoService.editar(id, dados, emailPrestador);
    if (resposta == null) {
      return ResponseEntity.ok(new ApiResponse(-1, "Recurso não encontrado"));
    }
    return ResponseEntity.ok(new ApiResponse(1, "Recurso editado com sucesso", resposta));
  }

  @GetMapping("/")
  public ResponseEntity<ApiResponse> listar(@RequestHeader("Authorization") String token) {
    String emailPrestador = this.tokenService.extrairEmailUsuarioToken(token);
    List<RecursoResponseDTO> resposta = this.recursoService.listar(emailPrestador);
    return ResponseEntity.ok(new ApiResponse(1, "Recursos listados com sucesso", resposta));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> buscar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.tokenService.extrairEmailUsuarioToken(token);
    System.out.println("emailPrestador: " + emailPrestador);
    RecursoResponseDTO resposta = this.recursoService.buscar(id, emailPrestador);
    if (resposta == null) {
      return ResponseEntity.ok(new ApiResponse(-1, "Recurso não encontrado"));
    }
    return ResponseEntity.ok(new ApiResponse(1, "Recurso encontrado com sucesso", resposta));    
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> deletar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    String emailPrestador = this.tokenService.extrairEmailUsuarioToken(token);
    boolean resposta = this.recursoService.deletar(id, emailPrestador);
    if (!resposta) {
      return ResponseEntity.ok(new ApiResponse(-1, "Recurso não encontrado"));
    }
    return ResponseEntity.ok(new ApiResponse(1, "Recurso deletado com sucesso"));
  }
}
