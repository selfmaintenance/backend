package br.com.selfmaintenance.domain.entities.recurso;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * [Recurso] é a entidade que representa um recurso do sistema, para um recurso ser criado é necessário que ele tenha um nome, quantidade e descrição
 * as demais informações são preenchidas automaticamente pelo sistema. O recurso é vinculado a uma oficina, pode ser criado pela Oficina ou pelo Prestador.
 * 
 * @see Oficina
 * @see Prestador
 * 
 * @version 1.0.0
 * 
 */
@Entity
@Table(name = "recurso")
public class Recurso { // TODO: adicionar preco
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @ManyToOne
  @JoinColumn(name="oficina_id", nullable=false)
  private Oficina oficina;

  @Column(name="nome", nullable=false)
  private String nome;

  @Column(name="quantidade", nullable=false, columnDefinition="int default 0")
  private int quantidade;
  
  @Column(name="descricao", nullable=true)
  private String descricao;

  @Column(name = "data_criacao", columnDefinition = "Timestamp", nullable = false)
  private Timestamp dataCriacao;
  
  @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP")
  private Timestamp dataAtualizacao;

  public Recurso() {
  }

  @PrePersist
  public void onCreate() {
    this.dataCriacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
  }

  @PreUpdate
  public void onUpdate() {
    this.dataAtualizacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
  }

  public Recurso(Long id, Oficina oficina, String nome, int quantidade, String descricao) {
    this.id = id;
	this.oficina = oficina;
    this.nome = nome;
    this.quantidade = quantidade;
    this.descricao = descricao;
  }
  
  public Recurso(Oficina oficina, String nome, int quantidade, String descricao) {
    this.oficina = oficina;
    this.nome = nome;
    this.quantidade = quantidade;
    this.descricao = descricao;
  }

  public Long getId() {
    return this.id;
  }

  public String getNome() {
    return this.nome;
  }

  public int getQuantidade() {
    return this.quantidade;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }


}