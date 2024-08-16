package br.com.selfmaintenance.domain.entities.usuario;

import java.util.List;

import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prestador")
public class Prestador extends UsuarioBase {

  @OneToMany(mappedBy = "prestador")
  private List<Recurso> recursos;
  
  public Prestador(
    Long id,
    UsuarioAutenticavel usuarioAutenticavel,
    String nome,
    String cpf,
    String cnpj,
    String email,
    String contato,
    String sexo,
    String senha
  ) {
    super(id, usuarioAutenticavel, nome, cpf, cnpj, email, contato, sexo, senha);
  }
}