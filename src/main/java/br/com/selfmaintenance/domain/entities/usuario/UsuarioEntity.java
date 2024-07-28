package br.com.selfmaintenance.domain.entities.usuario;
import br.com.selfmaintenance.app.interfaces.IUsuarioEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements IUsuarioEntity, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    public UsuarioEntity(Long id, String nome, int idade, String email, String contato, String sexo, String senha) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.contato = contato;
        this.sexo = sexo;
        this.senha = senha;
    }

    public UsuarioEntity() {

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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
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
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
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
