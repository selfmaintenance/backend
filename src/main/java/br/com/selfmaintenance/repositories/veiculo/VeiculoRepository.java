package br.com.selfmaintenance.repositories.veiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;

@Repository
public interface VeiculoRepository extends  JpaRepository<Veiculo, Long> {
}