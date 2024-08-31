package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.usuario.UsuarioService;

/**
 * [SelfMaintenanceFacade] é a fachada de manutenção própria nela temos os serviços de autenticação, usuário, cliente, oficina e prestador todos 
 * concentrados em um único lugar, ela é fachada utilizada em todo o sistema, caso um novo domínio seja criado ele deve ser adicionado aqui
 * 
 * @see AutenticacaoFacade
 * @see UsuarioService
 * @see ClienteFacade
 * @see OficinaFacade
 * @see PrestadorFacade
 * 
 * @version 1.0.0
 * 
 */
@Service
public class SelfMaintenanceFacade { 
  /**
   * [AutenticacaoFacade] é a fachada de autenticação
   */
  public final AutenticacaoFacade autenticacao;
  /**
   * [UsuarioService] é o serviço de usuário
   */
  public final UsuarioService usuarioBase; 
  /**
   * [ClienteFacade] é a fachada de cliente
   */
  public final ClienteFacade cliente;
  /**
   * [OficinaFacade] é a fachada de oficina
   */
  public final OficinaFacade oficina;
  /**
   * [PrestadorFacade] é a fachada de prestador
   */
  public final PrestadorFacade prestador;
  
  public SelfMaintenanceFacade(
    AutenticacaoFacade autenticacao,
    UsuarioService usuarioBase,
    ClienteFacade cliente,
    OficinaFacade oficina,
    PrestadorFacade prestador
  ) {
    this.autenticacao = autenticacao;
    this.usuarioBase = usuarioBase;
    this.cliente = cliente;
    this.oficina = oficina;
    this.prestador = prestador;
  }
}