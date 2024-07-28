package br.com.selfmaintenance.repositories;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UserDetails findByEmail(String email);
}
