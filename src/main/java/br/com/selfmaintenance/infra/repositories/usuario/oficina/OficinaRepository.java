package br.com.selfmaintenance.infra.repositories.usuario.oficina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Long> {
  Oficina findByEmail(String email);
}