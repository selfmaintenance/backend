package br.com.selfmaintenance.domain.entities.cliente;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cliente")
public class ClienteEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cliente")
    private List<UsuarioEntity> usuarios;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(name = "endereco")
    private String endereco;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido")
    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

    public ClienteEntity() {
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public @CPF String getCpf() {
        return cpf;
    }

    public void setCpf(@CPF String cpf) {
        this.cpf = cpf;
    }

    public void setUsuario(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }

    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }
}
