package br.com.selfmaintenance.presentation.http.controllers;

import br.com.selfmaintenance.app.records.AutenticacaoDTO;
import br.com.selfmaintenance.app.services.TokenService;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import br.com.selfmaintenance.repositories.UsuarioRepository;
import br.com.selfmaintenance.utils.RespostaApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<RespostaApi> login(@RequestBody @Valid AutenticacaoDTO data) {
        var usuarioNomeSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usuarioNomeSenha);

        var token = this.tokenService.gerarToken((UsuarioEntity) auth.getPrincipal());

        return ResponseEntity.ok(new RespostaApi(1, "Usuário autenticado com sucesso", token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<RespostaApi> registrar(@RequestBody @Valid UsuarioEntity usuario) {
        if (this.usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(encryptedPassword);
        this.usuarioRepository.save(usuario);

        return ResponseEntity.ok(new RespostaApi(1, "Usuário criado com sucesso", usuario));
    }
}
