package br.com.selfmaintenance.demo.repositories;

import br.com.selfmaintenance.demo.entities.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
