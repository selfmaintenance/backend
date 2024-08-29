package br.com.selfmaintenance.repositories.usuario.oficina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {
  Prestador findByEmail(String email);
}