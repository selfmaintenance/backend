package br.com.selfmaintenance.app.interfaces;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUsuarioEntity extends UserDetails {
    public Long getId();
    public void setId(Long id);
    public String getNome();
    public void setNome(String nome);
    public String getEmail();
    public void setEmail(String email);
    public String getContato();
    public void setContato(String contato);
    public int getIdade();
    public void setIdade(int idade);
    public String getSexo();
    public void setSexo(String sexo);
    public String getSenha();
    public void setSenha(String senha);
    public UsuarioRole getRole();
    public void setRole(UsuarioRole role);

    @Override
    default String getUsername() {
        return getEmail();
    }

    @Override
    default String getPassword() {
        return getSenha();
    }
}
