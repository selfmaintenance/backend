package br.com.selfmaintenance.repositories.usuario.oficina;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;


public interface OficinaRepository extends  JpaRepository<Oficina, Long> {
  Oficina findByEmail(String email);
}