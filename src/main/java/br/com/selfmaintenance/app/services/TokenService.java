package br.com.selfmaintenance.app.services;

import br.com.selfmaintenance.app.interfaces.IUsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String chaveAutenticacao;

    @Value("${spring.application.name}")
    private String emissorDaChave;

    public String gerarToken(IUsuarioEntity usuario) {
        try {
            Algorithm algoritmoAutenticacao = Algorithm.HMAC256(this.chaveAutenticacao);
            String token = JWT.create()
                    .withIssuer(this.emissorDaChave)
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(this.getExpiracao())
                    .sign(algoritmoAutenticacao);

            return token;
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro ao criar token de autenticação", ex);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algoritmoAutenticacao = Algorithm.HMAC256(this.chaveAutenticacao);
            return JWT.require(algoritmoAutenticacao)
                    .withIssuer(this.emissorDaChave)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Token inválido", ex);
        }
    }

    // Expira em 1 dia
    private Instant getExpiracao() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
