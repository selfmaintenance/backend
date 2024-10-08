package br.com.selfmaintenance.domain.entities.usuario;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * [UsuarioAutenticavel] é a entidade que representa um usuário autenticável do sistema, 
 * para um usuário ser criado é necessário que ele tenha um nome, email, contato e senha.
 * É usado para mapear todos os usuários do sistema, independente do seu papel.
 * 
 * @version 1.0.0
 */
@Entity
@Table(name="usuario_autenticavel")
public class UsuarioAutenticavel implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "contato", nullable = false)
  private String contato;

  @Column(name = "senha", nullable = false)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private UsuarioRole role;

  public UsuarioAutenticavel() {
  }

  public UsuarioAutenticavel(
    String nome, 
    String email, 
    String contato, 
    String senha, 
    UsuarioRole role
  ) {
    this.nome = nome;
    this.email = email;
    this.contato = contato;
    this.senha = senha;
    this.role = role;
  }

  public UsuarioAutenticavel(
    Long id,
    String nome, 
    String email, 
    String contato, 
    String senha, 
    UsuarioRole role
  ) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.contato = contato;
    this.senha = senha;
    this.role = role;
  }

  public void criptografarSenha() {
    PasswordEncoder bcrypt = new BCryptPasswordEncoder();
    this.senha = bcrypt.encode(this.senha); 
  }

  public Long getId() {
    return this.id;
  }

  public String getContato() {
    return this.contato;
  }

  public String getEmail() {
    return this.email;
  }

  @Override
  public String getUsername() {
    return this.nome;
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  public UsuarioRole getRole() {
    return this.role;
  }

  @Override
  public Collection<? extends GrantedAuthority>getAuthorities() {
    switch(this.role) {
      case OFICINA -> {
        return List.of(
          new SimpleGrantedAuthority(UsuarioRole.OFICINA.getRole()),
          new SimpleGrantedAuthority(UsuarioRole.PRESTADOR.getRole())
        );
      }
      case PRESTADOR -> {
          return List.of(
            new SimpleGrantedAuthority(UsuarioRole.PRESTADOR.getRole())
          );
      }
      case CLIENTE -> {
          return List.of(
            new SimpleGrantedAuthority(UsuarioRole.CLIENTE.getRole())
          );
      }
      default -> {
        return null;
      }
    }
  }
}
