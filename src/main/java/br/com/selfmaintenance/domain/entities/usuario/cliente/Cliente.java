package br.com.selfmaintenance.domain.entities.usuario.cliente;

import java.util.List;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioBase;
import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * [Cliente] é a entidade que representa um cliente do sistema,
 * para um cliente ser criado é necessário que ele tenha um nome, cpf, email, contato e senha.
 * O cliente é um usuário autenticável e herda as informações de usuário base. Ele pode ter vários veículos e procedimentos.
 * 
 * @see UsuarioBase
 * @see UsuarioAutenticavel
 * @see Veiculo
 * @see Procedimento
 * 
 * @version 1.0.0
 */
@Entity
@Table(name="cliente")
public class Cliente extends UsuarioBase {
  @OneToMany(mappedBy = "cliente")
  private List<Veiculo> veiculos;

  @OneToMany(mappedBy = "cliente")
  private List<Procedimento> procedimentos;

  public Cliente() {
    super();
  }

  public Cliente(
    UsuarioAutenticavel usuarioAutenticavel,
    String nome,
    String cpf,
    String email,
    String contato,
    String sexo,
    String senha
  ) {
    super(usuarioAutenticavel, nome, cpf, email, contato, sexo, senha);
  }
}