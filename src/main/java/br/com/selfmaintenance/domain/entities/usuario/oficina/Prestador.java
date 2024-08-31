package br.com.selfmaintenance.domain.entities.usuario.oficina;

import java.util.List;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioAutenticavel;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioBase;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * [Prestador] é a entidade que representa um prestador do sistema,
 * para um prestador ser criado é necessário que ele tenha um nome, cpf, email, contato e senha.
 * O prestador é vinculado a uma oficina, ele é um usuário autenticável e herda as informações de usuário base.
 * 
 * @see Oficina
 * @see Procedimento
 * @see UsuarioBase
 * @see UsuarioAutenticavel
 * 
 */
@Entity
@Table(name="prestador")
public class Prestador extends UsuarioBase {
  @OneToMany(mappedBy = "prestador")
  private List<Procedimento> procedimentos;

  @ManyToOne
  @JoinColumn(name = "oficina_id", nullable = false)
  private Oficina oficina;

  public Prestador() {
    super();
  }

  public Prestador(
    Oficina oficina,
    UsuarioAutenticavel usuarioAutenticavel,
    String nome,
    String cpf,
    String email,
    String contato,
    String sexo,
    String senha
  ) {
    super(usuarioAutenticavel, nome, cpf, email, contato, sexo, senha);
    this.oficina = oficina;
  }

  public Oficina getOficina() {
    return this.oficina;
  }
}