package br.com.selfmaintenance.domain.entities.usuario;

import java.util.List;

import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente extends UsuarioBase {
  @OneToMany(mappedBy = "cliente")
  private List<Veiculo> veiculos;

  public Cliente(
    Long id,
    UsuarioAutenticavel usuarioAutenticavel,
    String nome,
    String cpf,
    String email,
    String contato,
    String sexo,
    String senha
  ) {
    super(id, usuarioAutenticavel, nome, cpf, null, email, contato, sexo, senha);
  }

  public List<Veiculo> getVeiculos() {
    return veiculos;
  }

  public void setVeiculos(List<Veiculo> veiculos) {
    this.veiculos = veiculos;
  }
}