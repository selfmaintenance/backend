package br.com.selfmaintenance.domain.entities.usuario;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.selfmaintenance.domain.entities.cliente.ClienteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "idade", nullable = false)
    private int idade;

    @Email(message = "Email fornecido inválido")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Contato não pode ser vazio")
    @Column(name = "contato", nullable = false)
    private String contato;

    @NotBlank(message = "Sexo não pode ser vazio")
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UsuarioRole role;

    public UsuarioEntity() {
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
        if (this.role == UsuarioRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority(UsuarioRole.ADMIN.getRole()),
                    new SimpleGrantedAuthority(UsuarioRole.FUNCIONARIO.getRole()),
                    new SimpleGrantedAuthority(UsuarioRole.CLIENTE.getRole())
            );
        } else if (this.role == UsuarioRole.FUNCIONARIO) {
            return List.of(
                    new SimpleGrantedAuthority(UsuarioRole.FUNCIONARIO.getRole())
            );
        } else if (this.role == UsuarioRole.CLIENTE) {
            return List.of(
                    new SimpleGrantedAuthority(UsuarioRole.CLIENTE.getRole())
            );
        }

        return null;
    }
}
