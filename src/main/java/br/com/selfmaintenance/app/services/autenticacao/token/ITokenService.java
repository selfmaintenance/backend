package br.com.selfmaintenance.app.services.autenticacao.token;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;

/**
 * [ITokenService] é a interface que define os métodos para a camada de serviço de tokens do sistema.
 * 
 * @version 1.0.0
 */
public interface ITokenService {
  /**
   * [criar] cria um token de autenticação para um usuário.
   * 
   * @param usuario é o usuário autenticável
   * @return o token de autenticação
   */
  String criar(UsuarioAutenticavel usuario);

  /**
   * [validar] valida um token de autenticação.
   * 
   * @param token é o token de autenticação
   * @return o email do usuário autenticado
   */
  String validar(String token);

  /**
   * [extrairEmailUsuarioToken] extrai o email do usuário autenticado de um token.
   * 
   * @param token é o token de autenticação
   * @return o email do usuário autenticado
   */
  String extrairEmailUsuarioToken(String token);
}
