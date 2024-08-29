package br.com.selfmaintenance.app.records.prestador;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioAutenticavelPrestadorDTO(
  @NotBlank(message="Nome não pode ser vazio")
  @Size(min=3, max=30, message="Nome deve ter no mínimo 3 caracteres e no máximo 30 caracteres")
  String nome,

  @NotBlank(message="Email não pode ser vazio")
  @Email(message="Email inválido")
  String email,

  @NotBlank(message="Contato não pode ser vazio")
  String contato,

  @Size(min=6, max=20, message="Tamanho da senha deve tá no intervalo fechado entre 6 e 20")
  String senha
) {
}