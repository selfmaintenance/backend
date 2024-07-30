package br.com.selfmaintenance.repositories;

import br.com.selfmaintenance.domain.entities.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
