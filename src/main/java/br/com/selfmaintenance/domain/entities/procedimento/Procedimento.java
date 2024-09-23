package br.com.selfmaintenance.domain.entities.procedimento;

import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * [Procedimento] é a entidade que representa um procedimento do sistema,
 * para um procedimento ser criado é necessário que ele tenha um nome, prestador e cliente.
 * O procedimento é vinculado a um prestador e um cliente, eles podem ter vários procedimentos.
 * Inicialmente um cliente abre um procedimento e o prestador pode aceitar o procedimento.
 *
 * @version 1.0.0
 * @see Cliente
 * @see Prestador
 */
@Table(name = "procedimentos")
@Entity(name = "Procedimento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Procedimento {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestador_id")
    private Prestador prestador;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Setter
    private String nome;
    @Setter
    private String descricao;

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP", updatable = false)
    private Timestamp dataCriacao;

    @Column(name = "data_aceitacao")
    private LocalDateTime dataAceitacao;

    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcedimentoStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "procedimento_id")
    private List<Recurso> recursos = new ArrayList<>();

    private boolean ativo;

    public Procedimento(Prestador prestador, Cliente cliente, @NotNull(message = "O nome é obrigatório.") @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres.") String nome, String descricao) {
        this.prestador = prestador;
        this.cliente = cliente;
        this.nome = nome;
        this.descricao = descricao;
        this.status = ProcedimentoStatus.ABERTO;
        this.ativo = true;
    }

    @PrePersist
    public void onCreate() {
        this.dataCriacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    public void cancelar() {
        this.ativo = false;
    }


    public void aceitar(LocalDateTime dataAgendamento) {
        this.dataAceitacao = LocalDateTime.now();
        this.dataAgendamento = dataAgendamento;
        this.status = ProcedimentoStatus.AGENDADO;
    }

    public void finalizar() {
        this.status = ProcedimentoStatus.REALIZADO;
    }

    public void adicionarRecurso(Recurso recurso) {
        if (recurso != null && !this.recursos.contains(recurso)) {
            this.recursos.add(recurso);
        }
    }
}