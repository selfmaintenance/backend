package br.com.selfmaintenance.demo.interfaces;

import org.hibernate.validator.constraints.br.CPF;

public interface IClienteEntity extends IUsuarioEntity {
    public String getCpf();
    public void setCpf(@CPF String cpf);
}
