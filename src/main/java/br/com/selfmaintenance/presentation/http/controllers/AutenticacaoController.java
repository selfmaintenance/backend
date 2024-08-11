package br.com.selfmaintenance.presentation.http.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.selfmaintenance.app.records.AutenticacaoDTO;
import br.com.selfmaintenance.app.services.AutorizacaoService;
import br.com.selfmaintenance.app.services.TokenService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import br.com.selfmaintenance.utils.RespostaApi;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AutorizacaoService autorizacaoService;

    public AutenticacaoController(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            AutorizacaoService autorizacaoService, AutorizacaoService autorizacaoService1) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.autorizacaoService = autorizacaoService1;
    }

    @PostMapping("/login")
    public ResponseEntity<RespostaApi> login(@RequestBody @Valid AutenticacaoDTO data) throws BadRequestException {
        var usuarioNomeSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        if (this.autorizacaoService.loadUserByUsername(data.email()) == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        }

        var auth = this.authenticationManager.authenticate(usuarioNomeSenha);
        var token = this.tokenService.gerarToken((UsuarioEntity) auth.getPrincipal());

        return ResponseEntity.ok(new RespostaApi(1, "Usuário autenticado com sucesso", token));
    }
}
