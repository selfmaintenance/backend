package br.com.selfmaintenance.app.records.recurso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarRecursoDTO(
  @NotBlank(message="Nome do recurso não pode ser vazio")
  String nome,

  @NotNull(message="Quantidade do recurso não pode ser vazia")
  int quantidade,

  String descricao
) {
}
