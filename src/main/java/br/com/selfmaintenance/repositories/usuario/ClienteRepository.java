package br.com.selfmaintenance.repositories.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.usuario.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Cliente findByEmail(String email);
}