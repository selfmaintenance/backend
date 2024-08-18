package br.com.selfmaintenance.domain.entities.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;

@MappedSuperclass
public abstract class UsuarioBase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @OneToOne
  @JoinColumn(name="usuario_autenticavel_id", nullable=false)
  private UsuarioAutenticavel usuarioAutenticavel;

  @Column(name="nome", nullable=false)
  private String nome;

  @Column(name="cpf", nullable=true)
  private String cpf;

  @Column(name="cnpj", nullable=true)
  private String cnpj;

  @Column(name="email", nullable=false, unique=true)

  private String email;

  @Column(name="contato", nullable=false)
  private String contato;

  @Column(name="sexo", nullable=true)
  private String sexo;

  @Column(name="senha", nullable=false)
  private String senha;

  public UsuarioBase() {
  }

  public UsuarioBase(
    UsuarioAutenticavel usuarioAutenticavel,
    String nome,
    String cpf,
    String cnpj,
    String email,
    String contato,
    String sexo,
    String senha
  ) {
    this.usuarioAutenticavel = usuarioAutenticavel;
    this.nome = nome;
    this.cpf = cpf;
    this.cnpj = cnpj;
    this.email = email;
    this.contato = contato;
    this.sexo = sexo;
    this.senha = senha;
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

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
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
