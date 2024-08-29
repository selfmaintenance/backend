package br.com.selfmaintenance.domain.entities.recurso;

import br.com.selfmaintenance.domain.entities.usuario.oficina.Oficina;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recurso")
public class Recurso {
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

  public Recurso() {
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