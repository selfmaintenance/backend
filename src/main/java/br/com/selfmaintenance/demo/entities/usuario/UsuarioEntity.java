package br.com.selfmaintenance.demo.entities.usuario;
import br.com.selfmaintenance.demo.interfaces.IUsuarioEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@MappedSuperclass
public abstract class UsuarioEntity implements IUsuarioEntity, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "idade", nullable = false)
    private int idade;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contato", nullable = false)
    private String contato;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "role", nullable = false)
    private UsuarioRole role;

    public UsuarioEntity() {}

    public UsuarioEntity(Long id, String nome, int idade, String email, String contato, String endereco, String sexo, String senha) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.contato = contato;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
        if (this.role == UsuarioRole.ADMINISTRADOR) {
            return List.of(
                    new SimpleGrantedAuthority(UsuarioRole.ADMINISTRADOR.getRole()),
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
