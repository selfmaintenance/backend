package br.com.selfmaintenance.app.services.autenticacao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;

/**
 * @author Edielson Rodrigues
 * 
 * AutorizacaoService é a classe que representa o serviço de autorização
 */
@Service
public class AutorizacaoService implements UserDetailsService {
  private final UsuarioAutenticavelRepository usuarioRepository;

  /**
   * Construtor da classe recebendo o repositório de usuarioAutenticavel
   * 
   * @param usuarioRepository
   */
  public AutorizacaoService(UsuarioAutenticavelRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.usuarioRepository.findByEmail(email);
  }
}
