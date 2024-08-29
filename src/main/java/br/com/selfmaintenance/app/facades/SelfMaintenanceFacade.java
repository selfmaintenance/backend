package br.com.selfmaintenance.app.facades;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.services.usuario.UsuarioService;

@Service
public class SelfMaintenanceFacade { 
  public final AutenticacaoFacade autenticacao;
  public final UsuarioService usuarioBase; 
  public final ClienteFacade cliente;
  public final OficinaFacade oficina;
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