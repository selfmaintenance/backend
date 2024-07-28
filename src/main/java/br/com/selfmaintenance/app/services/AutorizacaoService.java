package br.com.selfmaintenance.app.services;

import br.com.selfmaintenance.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutorizacaoService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AutorizacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Considera o atributo o email como atributo de autenticação
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.usuarioRepository.findByEmail(email);
    }
}
