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
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/usuario")
public class UsuarioController {
  private final SelfMaintenanceFacade selfMaintenance;

  public UsuarioController(SelfMaintenanceFacade selfMaintenance) {
    this.selfMaintenance = selfMaintenance;
  }

  @PostMapping("/")
  public ResponseEntity<ApiResponse> criar(@RequestBody @Valid CriarUsuarioDTO dados) throws ServiceException {
    Map<String, Long> respostaCriacao = this.selfMaintenance.usuarioBase.criar(dados);
    return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(new ApiResponse(1, "Usuário criado com sucesso", respostaCriacao));
  }
}

