package br.com.selfmaintenance.domain.entities.usuario.oficina;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * [Oficina] é a entidade que representa uma oficina do sistema, 
 * para uma oficina ser criada é necessário que ela tenha um nome, cnpj, email e senha.
 * Uma oficina é uma entidade que pode ter vários prestadores e recursos. Ele também é um usuário autenticável.
 * 
 * @see Prestador
 * @see Recurso
 * @see UsuarioAutenticavel
 * 
 * @version 1.0.0
 * 
 */
@Entity
@Table(name = "oficina")
public class Oficina {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "nome", nullable=false)
  private String nome;

  @Column(name = "cnpj", nullable=false)
  private String cnpj;
  
  @Column(name="email", nullable=false, unique=true)
  private String email;

  @Column(name="senha", nullable=false)
  private String senha;

  @Column(name = "data_criacao", columnDefinition = "TIMESTAMP", updatable = false)
  private Timestamp dataCriacao;
  
  @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP")
  private Timestamp dataAtualizacao;

  @OneToOne
  @JoinColumn(name = "usuario_autenticavel_id", nullable=false)
  private UsuarioAutenticavel usuarioAutenticavel;

  @OneToMany(mappedBy = "oficina")
  private List<Prestador> prestadores;

  @OneToMany(mappedBy = "oficina")
  private List<Recurso> recursos;

  public Oficina() {
  }

  public Oficina(
    UsuarioAutenticavel usuarioAutenticavel,
    String nome, 
    String cnpj, 
    String email, 
    String senha 
  ) {
    this.usuarioAutenticavel = usuarioAutenticavel;
    this.nome = nome;
    this.cnpj = cnpj;
    this.email = email;
    this.senha = senha;
  }

  public Oficina(
    Long id,
    UsuarioAutenticavel usuarioAutenticavel,
    String nome, 
    String cnpj, 
    String email, 
    String senha 
  ) {
    this.id = id;
    this.usuarioAutenticavel = usuarioAutenticavel;
    this.nome = nome;
    this.cnpj = cnpj;
    this.email = email;
    this.senha = senha;
  }

  @PrePersist
  public void onCreate() {
    this.dataCriacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
  }

  @PreUpdate
  public void onUpdate() {
    this.dataAtualizacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
  }

  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }
}