package br.com.selfmaintenance.app.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUsuarioEntity extends UserDetails {
    String getEmail();
    String getSenha();
    void setSenha(String senha);

    @Override
    default String getUsername() {
        return getEmail();
    }

    @Override
    default String getPassword() {
        return getSenha();
    }
}
