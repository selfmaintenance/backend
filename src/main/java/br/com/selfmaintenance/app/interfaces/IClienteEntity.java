package br.com.selfmaintenance.app.interfaces;

import org.hibernate.validator.constraints.br.CPF;

public interface IClienteEntity {
    public String getEndereco();
    public void setEndereco(String endereco);
    public String getCpf();
    public void setCpf(@CPF String cpf);
}
