package br.com.selfmaintenance.domain.entities.usuario;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public abstract class UsuarioBase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "usuario_autenticavel_id", nullable = false)
  private UsuarioAutenticavel usuarioAutenticavel;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "cpf", nullable = true)
  private String cpf;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "contato", nullable = false)
  private String contato;

  @Column(name = "sexo", nullable = true)
  private String sexo;

  @Column(name = "senha", nullable = false)
  private String senha;

  @Column(name = "data_criacao", columnDefinition = "TIMESTAMP", updatable = false)
  private Date dataCriacao;
  
  @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP")
  private Date dataAtualizacao;

  public UsuarioBase() {
  }

  public UsuarioBase(
    UsuarioAutenticavel usuarioAutenticavel,
    String nome,
    String cpf,
    String email,
    String contato,
    String sexo,
    String senha
  ) {
    this.usuarioAutenticavel = usuarioAutenticavel;
    this.nome = nome;
    this.cpf = cpf;
    this.email = email;
    this.contato = contato;
    this.sexo = sexo;
    this.senha = senha;
  }

  @PrePersist
  public void onCreate() {
    this.dataCriacao = new Date();
  }

  @PreUpdate
  public void onUpdate() {
    this.dataAtualizacao = new Date();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContato() {
    return contato;
  }

  public void setContato(String contato) {
    this.contato = contato;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
      this.senha = senha;
    }
}
