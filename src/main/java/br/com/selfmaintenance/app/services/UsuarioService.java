package br.com.selfmaintenance.app.services;

import br.com.selfmaintenance.app.interfaces.IUsuarioEntity;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import br.com.selfmaintenance.repositories.UsuarioRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity criar(IUsuarioEntity usuario) throws BadRequestException {
        if (this.usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new BadRequestException("Usuário já existe");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(encryptedPassword);

        return this.usuarioRepository.save((UsuarioEntity) usuario);
    }
}



