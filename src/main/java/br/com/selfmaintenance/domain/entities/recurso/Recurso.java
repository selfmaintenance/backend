package br.com.selfmaintenance.domain.entities.recurso;

import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

/**
 * [Recurso] é a entidade que representa um recurso do sistema, para um recurso ser criado é necessário que ele tenha um nome, quantidade e descrição
 * as demais informações são preenchidas automaticamente pelo sistema. O recurso é vinculado a uma oficina, pode ser criado pela Oficina ou pelo Prestador.
 *
 * @version 1.0.0
 * @see Oficina
 * @see Prestador
 */
@Table(name = "recursos")
@Entity(name = "Recurso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Recurso { // TODO: adicionar preco
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oficina_id", nullable = false)
    private Oficina oficina;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    @Column(nullable = false, columnDefinition = "int default 0")
    private int quantidade;

    @Getter
    @Setter
    private String descricao;

    @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP")
    private Timestamp dataAtualizacao;

    @PreUpdate
    public void onUpdate() {
        this.dataAtualizacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    public Recurso(Oficina oficina, String nome, int quantidade, String descricao) {
        this.oficina = oficina;
        this.nome = nome;
        this.quantidade = quantidade;
        this.descricao = descricao;
    }

    public Recurso(Long id, Oficina oficina, String nome, int quantidade, String descricao) {
        this.id = id;
        this.oficina = oficina;
        this.nome = nome;
        this.quantidade = quantidade;
        this.descricao = descricao;
    }

}