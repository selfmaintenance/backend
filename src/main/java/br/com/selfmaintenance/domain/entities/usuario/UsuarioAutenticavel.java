package br.com.selfmaintenance.domain.entities.usuario;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario_autenticavel")
public class UsuarioAutenticavel implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable=false)
    private String nome;

    @Email(message = "Email fornecido inválido")
    @Column(name = "email", nullable=false)
    private String email;

    @NotBlank(message = "Contato não pode ser vazio")
    @Column(name = "contato", nullable=false)
    private String contato;

    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(name = "senha", nullable=false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable=false)
    private UsuarioRole role;

    public UsuarioAutenticavel() {
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public Collection<? extends GrantedAuthority>getAuthorities() {
        switch(this.role) {
            case ADMIN -> {
                return List.of(
                    new SimpleGrantedAuthority(UsuarioRole.ADMIN.getRole()),
                    new SimpleGrantedAuthority(UsuarioRole.PRESTADOR.getRole()),
                    new SimpleGrantedAuthority(UsuarioRole.CLIENTE.getRole())
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
