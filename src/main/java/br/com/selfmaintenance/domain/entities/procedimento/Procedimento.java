package br.com.selfmaintenance.domain.entities.procedimento;

import java.util.Date;

import br.com.selfmaintenance.domain.entities.usuario.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.Prestador;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="procedimento")
public class Procedimento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @ManyToOne
  @JoinColumn(name="prestador_id", nullable=false)
  private Prestador prestador;

  @ManyToOne
  @JoinColumn(name="cliente_id", nullable=false)
  private Cliente cliente;

  @Column(name="descricao", nullable=false)
  private String nome;

  @Column(name="data_solicitacao", nullable=false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date dataSolicitacao;
  
  @Column(name="data_aceite", nullable=true, columnDefinition = "TIMESTAMP")
  private Date dataAceite;

  @Column(name="data_agendamento", nullable=true, columnDefinition = "TIMESTAMP")
  private Date dataAgendamento;

  @Enumerated(EnumType.STRING)
  @Column(name="status", nullable=false, columnDefinition = "VARCHAR(255) DEFAULT 'ABERTO'")
  private ProcedimentoStatus status;

  public Procedimento() {
  }
}