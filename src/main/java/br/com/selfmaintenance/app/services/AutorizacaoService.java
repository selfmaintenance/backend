package br.com.selfmaintenance.app.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.repositories.usuario.UsuarioAutenticavelRepository;

@Service
public class AutorizacaoService implements UserDetailsService {
  private final UsuarioAutenticavelRepository usuarioRepository;

  public AutorizacaoService(UsuarioAutenticavelRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return this.usuarioRepository.findByEmail(email);
  }
}
