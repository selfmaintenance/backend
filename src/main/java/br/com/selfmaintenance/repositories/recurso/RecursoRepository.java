package br.com.selfmaintenance.repositories.recurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.recurso.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
}
