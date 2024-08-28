package br.com.selfmaintenance.app.records.recurso;

import java.util.Optional;

public record EditarRecursoDTO(
  Optional<String> nome,
  Optional<Integer> quantidade,
  Optional<String> descricao
) {
}