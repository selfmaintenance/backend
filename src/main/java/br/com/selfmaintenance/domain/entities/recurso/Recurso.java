package br.com.selfmaintenance.domain.entities.recurso;

import br.com.selfmaintenance.domain.entities.usuario.Prestador;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Recurso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @ManyToOne
  @JoinColumn(name="prestador_id", nullable=false)
  private Prestador prestador;

  @Column(name="nome", nullable=false)
  private String nome;

  @Column(name="quantidade", nullable=false, columnDefinition="int default 0")
  private int quantidade;
  
  @Column(name="descricao", nullable=true)
  private String descricao; 

  public Recurso() {
  }

  public Recurso(Prestador prestador, String nome, int quantidade, String descricao) {
    this.prestador = prestador;
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