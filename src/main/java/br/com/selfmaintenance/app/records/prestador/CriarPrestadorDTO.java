package br.com.selfmaintenance.app.records.prestador;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarPrestadorDTO(
  @NotNull(message="Usuário autenticável não pode ser nulo")
  @Valid
  UsuarioAutenticavelPrestadorDTO usuarioAutenticavelPrestador,

  @NotBlank
  @CPF(message="CPF inválido")
  String cpf,

  String sexo
) {
}