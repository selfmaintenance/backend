package br.com.selfmaintenance.app.facades;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.autenticacao.AutorizacaoService;
import br.com.selfmaintenance.app.services.autenticacao.TokenService;

@Service
public class AutenticacaoFacade {
  public final TokenService token;
  public final AuthenticationManager gerenciador;
  public final AutorizacaoService autorizacao;

  public AutenticacaoFacade(
    TokenService token,
    AuthenticationManager gerenciador,
    AutorizacaoService autorizacao
  ) {
    this.token = token;
    this.gerenciador = gerenciador;
    this.autorizacao = autorizacao;
  }
}