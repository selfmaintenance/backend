package br.com.selfmaintenance.presentation.http.controllers;

import java.util.List;
import java.util.Map;

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
import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.EditarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/veiculo")
public class VeiculoController {
  private final SelfMaintenanceFacade selfMaintenance;
  
  public VeiculoController(SelfMaintenanceFacade selfMaintenance) {
    this.selfMaintenance = selfMaintenance;
  }

  @PostMapping("/")
  public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarVeiculoDTO dados, @RequestHeader("Authorization") String token) {
    String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    Map<String,Long> resposta = this.selfMaintenance.cliente.veiculo.criar(dados, emailCliente);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ApiResponse(1, "Veículo criado com sucesso", resposta));
  }

  @PatchMapping("/{id}")
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

  @GetMapping("/")
  public ResponseEntity<ApiResponse> listar(@RequestHeader("Authorization") String token) {
    String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    List<VeiculoResponseDTO> resposta = this.selfMaintenance.cliente.veiculo.listar(emailCliente);
    return ResponseEntity.ok(new ApiResponse(1, "Veículos listados com sucesso", resposta));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> buscar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    VeiculoResponseDTO resposta = this.selfMaintenance.cliente.veiculo.buscar(id, emailCliente);

    if (resposta == null) {
      return ResponseEntity.ok(new ApiResponse(-1, "Veículo não encontrado"));
    }
    return ResponseEntity.ok(new ApiResponse(1, "Veículo encontrado com sucesso", resposta));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> deletar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    String emailCliente = this.selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    boolean resposta = this.selfMaintenance.cliente.veiculo.deletar(id, emailCliente);
    if (!resposta) {
      return ResponseEntity.ok(new ApiResponse(-1, "Veículo não encontrado"));
    }

    return ResponseEntity.ok(new ApiResponse(1, "Veículo deletado com sucesso"));
  }
}