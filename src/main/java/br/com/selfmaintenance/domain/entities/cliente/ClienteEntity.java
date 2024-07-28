package br.com.selfmaintenance.domain.entities.cliente;

import br.com.selfmaintenance.app.interfaces.IClienteEntity;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.br.CPF;


@Entity
@Table(name = "cliente")
public class ClienteEntity implements IClienteEntity {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private UsuarioEntity usuarioEntity;

    @Column(name="endereco")
    private String endereco;

    @CPF(message = "CPF fornecido inválido")
    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

    public ClienteEntity(String endereco, String cpf) {
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public ClienteEntity() {
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    @Override
    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
