package br.com.selfmaintenance.app.facades;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.autenticacao.AutorizacaoService;
import br.com.selfmaintenance.app.services.autenticacao.TokenService;

/**
 * [AutenticacaoFacade] é a fachada de autenticação nela temos os serviços de token, gerenciador de autenticação e autorização todos 
 * concentrados em um único lugar
 * 
 * @see TokenService
 * @see AuthenticationManager
 * @see AutorizacaoService
 *
 * @version 1.0.0 
 */
@Service
public class AutenticacaoFacade {
  /**
   * [TokenService] é o serviço de token
   */
  public final TokenService token;
  /**
   * [AuthenticationManager] é o gerenciador de autenticação
   */
  public final AuthenticationManager gerenciador;
  /**
   * [AutorizacaoService] é o serviço de autorização
   */
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