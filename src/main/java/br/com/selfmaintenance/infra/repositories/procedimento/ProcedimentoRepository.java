package br.com.selfmaintenance.infra.repositories.procedimento;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}
