package br.com.selfmaintenance.repositories.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;

@Repository
public interface UsuarioAutenticavelRepository extends JpaRepository<UsuarioAutenticavel, Long> {
  UserDetails findByEmail(String email);
}