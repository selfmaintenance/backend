package br.com.selfmaintenance.app.records.procedimento;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.procedimento.ProcedimentoStatus;

import java.time.LocalDateTime;

public record DetalhesProcedimentoDTO(Long id, Long prestadorId, Long clienteId, String nome, String descricao,
                                      ProcedimentoStatus status, boolean ativo, LocalDateTime dataAceitacao,
                                      LocalDateTime dataAgendamento) {
    public DetalhesProcedimentoDTO(Procedimento procedimento) {
        this(
                procedimento.getId(),
                procedimento.getPrestador().getId(),
                procedimento.getCliente().getId(),
                procedimento.getNome(),
                procedimento.getDescricao(),
                procedimento.getStatus(),
                procedimento.isAtivo(),
                procedimento.getDataAceitacao(),
                procedimento.getDataAgendamento()
        );
    }
}
