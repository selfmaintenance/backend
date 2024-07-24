package br.com.selfmaintenance.demo.entities.cliente;

import br.com.selfmaintenance.demo.interfaces.IClienteEntity;
import br.com.selfmaintenance.demo.entities.usuario.UsuarioEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.br.CPF;


@Entity
@Table(name = "cliente")
public class ClienteEntity extends UsuarioEntity implements IClienteEntity {
    @CPF(message = "CPF fornecido inválido")
    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

    public ClienteEntity() {
        super();
    }

    public ClienteEntity(Long id, String nome, int idade, String email, String contato, String endereco, String sexo, String senha) {
        super(id, nome, idade, email, contato, endereco, sexo, senha);
    }

    @Override
    public @CPF String getCpf() {
        return cpf;
    }

    @Override
    public void setCpf(@CPF String cpf) {
        this.cpf = cpf;
    }
}
