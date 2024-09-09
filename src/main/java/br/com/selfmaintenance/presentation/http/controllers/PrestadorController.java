package br.com.selfmaintenance.presentation.http.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.selfmaintenance.app.facades.SelfMaintenanceFacade;
import br.com.selfmaintenance.app.records.prestador.CriarPrestadorDTO;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.responses.ApiResponse;
import jakarta.validation.Valid;

/**
 * [PrestadorController] é a classe que representa o controlador de prestadores do sistema.
 * 
 * @version 1.0.0
 */
@RestController
@RequestMapping("/oficina/prestador")
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
  public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarPrestadorDTO dados, @RequestHeader("Authorization") String token) throws ServiceException {
    String emailOficina = selfMaintenance.autenticacao.token.extrairEmailUsuarioToken(token);
    Map<String, Long> resposta = this.selfMaintenance.oficina.prestador.criar(dados, emailOficina);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ApiResponse(1, "Prestador criado com sucesso", resposta));
  }
}