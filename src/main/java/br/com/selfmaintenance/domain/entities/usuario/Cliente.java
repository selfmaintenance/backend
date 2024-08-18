package br.com.selfmaintenance.domain.entities.usuario;

import java.util.List;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente extends UsuarioBase {
  @OneToMany(mappedBy = "cliente")
  private List<Veiculo> veiculos;

  @OneToMany(mappedBy = "cliente")
  private List<Procedimento> procedimentos;

  public Cliente(
    UsuarioAutenticavel usuarioAutenticavel,
    String nome,
    String cpf,
    String cnpj,
    String email,
    String contato,
    String sexo,
    String senha
  ) {
    super(usuarioAutenticavel, nome, cpf, cnpj, email, contato, sexo, senha);
  }
}