package br.com.selfmaintenance.infra.repositories.procedimento;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.procedimento.ProcedimentoStatus;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

    Page<Procedimento> findAll(Pageable paginacao);

    // Buscar procedimentos por clienteId
    List<Procedimento> findByClienteId(Long clienteId);

    // Buscar procedimentos por prestadorId
    List<Procedimento> findByPrestadorId(Long prestadorId);

    // Buscar procedimentos por status
    List<Procedimento> findByStatus(ProcedimentoStatus status);

    // Buscar procedimentos agendados após uma data específica
    List<Procedimento> findByDataAgendamentoAfter(LocalDateTime dataAgendamento);

    // Buscar um procedimento específico por ID, cliente e prestador
    Optional<Procedimento> findByIdAndClienteAndPrestador(Long id, Cliente cliente, Prestador prestador);

    // Buscar todos os procedimentos aceitos (exemplo de query customizada)
    List<Procedimento> findByDataAceitacaoIsNotNull();
}
