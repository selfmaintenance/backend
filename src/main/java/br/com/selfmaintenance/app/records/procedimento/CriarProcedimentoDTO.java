package br.com.selfmaintenance.app.records.procedimento;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarProcedimentoDTO(
        @NotNull(message = "O prestadorId é obrigatório.")
        Long prestadorId,

        @NotNull(message = "O clienteId é obrigatório.")
        Long clienteId,

        @NotNull(message = "O nome é obrigatório.")
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres.")
        String nome,

        String descricao
) {

    public CriarProcedimentoDTO(Procedimento procedimento) {
        this(procedimento.getPrestador().getId(), procedimento.getCliente().getId(), procedimento.getNome(), procedimento.getDescricao());
    }
}
