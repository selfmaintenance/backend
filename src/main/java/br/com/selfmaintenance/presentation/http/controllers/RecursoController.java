package br.com.selfmaintenance.presentation.http.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
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
    String email = this.tokenService.extrairEmailUsuarioToken(token);
    Map<String, Long> resposta = this.recursoService.criar(dados, email);
    return ResponseEntity.ok(new ApiResponse(1, "Recurso criado com sucesso", resposta));
  } 
}
