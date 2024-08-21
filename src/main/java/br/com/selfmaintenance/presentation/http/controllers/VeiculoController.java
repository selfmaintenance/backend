package br.com.selfmaintenance.presentation.http.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;
import br.com.selfmaintenance.app.services.autenticacao.TokenService;
import br.com.selfmaintenance.app.services.veiculo.VeiculoService;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/veiculo")
public class VeiculoController {
  private final VeiculoService veiculoService;
  private final TokenService tokenService;
  
  public VeiculoController(VeiculoService veiculoService, TokenService tokenService) {
    this.veiculoService = veiculoService;
    this.tokenService = tokenService;
  }

  @PostMapping("/")
  public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarVeiculoDTO dados, @RequestHeader("Authorization") String token) {
    String emailUsuario = this.tokenService.extrairEmailUsuarioToken(token);
    Map<String,Long> resposta = this.veiculoService.criar(dados, emailUsuario);

    return ResponseEntity.ok(new ApiResponse(1, "Veículo criado com sucesso", resposta));
  }

  @GetMapping("/")
  public ResponseEntity<ApiResponse> listar(@RequestHeader("Authorization") String token) {
    String emailUsuario = this.tokenService.extrairEmailUsuarioToken(token);
    List<VeiculoResponseDTO> resposta = this.veiculoService.listar(emailUsuario);
    return ResponseEntity.ok(new ApiResponse(1, "Veículos listados com sucesso", resposta));
  }
}