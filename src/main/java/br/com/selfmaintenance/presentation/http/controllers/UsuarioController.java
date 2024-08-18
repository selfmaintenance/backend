package br.com.selfmaintenance.presentation.http.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.selfmaintenance.app.records.usuario.dtos.CriarUsuarioDTO;
import br.com.selfmaintenance.app.services.UsuarioService;
import br.com.selfmaintenance.utils.exceptions.ServiceException;
import br.com.selfmaintenance.utils.responses.CriarUsuario;
import br.com.selfmaintenance.utils.responses.RespostaApi;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/usuario")
public class UsuarioController {
  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping("/")
  public ResponseEntity<RespostaApi> criar(@RequestBody @Valid CriarUsuarioDTO dados) throws ServiceException {
    CriarUsuario respostaCriacao = this.usuarioService.criar(dados);
    return ResponseEntity.ok(new RespostaApi(1, "Usuário criado com sucesso", respostaCriacao));
  }
}

