package br.com.selfmaintenance.domain.entities.cliente;

import br.com.selfmaintenance.app.interfaces.IClienteEntity;
import br.com.selfmaintenance.app.interfaces.IUsuarioEntity;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "cliente")
public class ClienteEntity implements IClienteEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UsuarioEntity usuarioEntity;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(name = "endereco")
    private String endereco;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido")
    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

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

    @Override
    public IUsuarioEntity getUsuarioEntity() {
        return this.usuarioEntity;
    }

    @Override
    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }
}
