package br.com.selfmaintenance.app.services.autenticacao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String chaveAutenticacao;

  @Value("${spring.application.name}")
  private String emissorDaChave;

  public String criar(UsuarioAutenticavel usuario) {
    try {
        Algorithm algoritmoAutenticacao = Algorithm.HMAC256(this.chaveAutenticacao);
        return JWT.create()
                .withIssuer(this.emissorDaChave)
                .withSubject(usuario.getEmail())
                .withExpiresAt(this.getExpiracao())
                .sign(algoritmoAutenticacao);
    } catch (JWTCreationException ex) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar token", ex);
    }
  }

  public String validar(String token) {
      Algorithm algoritmoAutenticacao = Algorithm.HMAC256(this.chaveAutenticacao);
      return JWT.require(algoritmoAutenticacao)
              .withIssuer(this.emissorDaChave)
              .build()
              .verify(token)
              .getSubject();
  }

  public String extrairEmailUsuarioToken(String token) {
    if (token == null || !token.startsWith("Bearer ") || token.startsWith("Bearer null")) {
      return null;
    }

    Algorithm algoritmoAutenticacao = Algorithm.HMAC256(this.chaveAutenticacao);
    var dadosToken = JWT.require(algoritmoAutenticacao)
            .withIssuer(this.emissorDaChave)
            .build()
            .verify(token.substring(7))
            .getClaims();
    
    return dadosToken.get("sub").asString();
  }

  private Instant getExpiracao() {
    return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
  }
}
