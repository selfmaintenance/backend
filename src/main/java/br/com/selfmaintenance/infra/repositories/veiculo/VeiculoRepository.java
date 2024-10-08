package br.com.selfmaintenance.infra.repositories.veiculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
  List<Veiculo> findByCliente_email(String emailCliente);
  Veiculo findByClienteAndId(Cliente cliente, Long id);
}