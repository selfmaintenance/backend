package br.com.selfmaintenance.repositories.recurso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
  List<Recurso> findByOficina_email(String emailPrestador);
  Recurso findByOficinaAndId(Oficina oficina, Long id);
}
