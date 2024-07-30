package br.com.selfmaintenance.app.interfaces;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import org.hibernate.validator.constraints.br.CPF;

public interface IClienteEntity {
    String getEndereco();
    void setEndereco(String endereco);
    String getCpf();
    void setCpf(@CPF String cpf);
    IUsuarioEntity getUsuarioEntity();
    void setUsuarioEntity(UsuarioEntity usuarioEntity);
}
