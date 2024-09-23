package br.com.selfmaintenance.domain.entities.procedimento;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import jakarta.persistence.*;

/**
 * [Procedimento] é a entidade que representa um procedimento do sistema,
 * para um procedimento ser criado é necessário que ele tenha um nome, prestador, cliente e status.
 * O procedimento é vinculado a um prestador e um cliente, eles podem ter vários procedimentos.
 * Inicialmente um cliente abre um procedimento, o prestador pode aceitar o procedimento.
 *
 * @version 1.0.0
 * @see Cliente
 * @see Prestador
 */
@Entity
@Table(name = "procedimento")
public class Procedimento { // TODO: adicionar nome, preco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prestador_id", nullable = false)
    private Prestador prestador;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "descricao", nullable = false)
    private String nome;

    @Column(name = "data_solicitacao", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    private Timestamp dataSolicitacao;

    @Column(name = "data_aceite", nullable = true, columnDefinition = "TIMESTAMP")
    private Timestamp dataAceite;

    @Column(name = "data_agendamento", nullable = true, columnDefinition = "TIMESTAMP")
    private Timestamp dataAgendamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ABERTO'")
    private ProcedimentoStatus status;

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP", updatable = false)
    private Timestamp dataCriacao;

    @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP")
    private Timestamp dataAtualizacao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "procedimento_id")
    private List<Recurso> recursos = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.dataCriacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        this.dataSolicitacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    @PreUpdate
    public void onUpdate() {
        this.dataAtualizacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    public void adicionarRecurso(Recurso recurso) {
        this.recursos.add(recurso);
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setStatus(ProcedimentoStatus status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public ProcedimentoStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }
}