package br.com.selfmaintenance.app.records.usuario.dtos;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CriarUsuarioDTO(
  @NotNull(message="Usuário autenticável não pode ser nulo")
  @Valid
  UsuarioAutenticavelDTO usuarioAutenticavel,

  @CPF(message="CPF inválido")
  String cpf,

  @CNPJ(message="CNPJ inválido")
  String cnpj,

  String sexo
) {
}
