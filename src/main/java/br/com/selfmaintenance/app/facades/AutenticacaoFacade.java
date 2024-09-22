package br.com.selfmaintenance.app.facades;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.autenticacao.AutorizacaoService;
import br.com.selfmaintenance.app.services.autenticacao.token.ITokenService;

/**
 * [AutenticacaoFacade] é a fachada de autenticação nela temos os serviços de token, gerenciador de autenticação e autorização todos 
 * concentrados em um único lugar
 * 
 * @see ITokenService
 * @see AuthenticationManager
 * @see AutorizacaoService
 *
 * @version 1.0.0 
 */
@Service
public class AutenticacaoFacade {
  /**
   * [ITokenService] é a definição do serviço de token
   */
  public final ITokenService token;
  /**
   * [AuthenticationManager] é a definição do serviço de autenticação
   */
  public final AuthenticationManager gerenciador;
  /**
   * [UserDetailsService] é a definição do serviço de autorização
   */
  public final UserDetailsService autorizacao;

  public AutenticacaoFacade(
    ITokenService token,
    AuthenticationManager gerenciador,
    UserDetailsService autorizacao
  ) {
    this.token = token;
    this.gerenciador = gerenciador;
    this.autorizacao = autorizacao;
  }
}