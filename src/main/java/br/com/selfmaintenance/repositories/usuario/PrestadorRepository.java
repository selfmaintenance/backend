package br.com.selfmaintenance.repositories.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.usuario.Prestador;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {
}