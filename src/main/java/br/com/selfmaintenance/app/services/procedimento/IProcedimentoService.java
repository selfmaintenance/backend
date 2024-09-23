package br.com.selfmaintenance.app.services.procedimento;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;

import java.util.List;
import java.util.Optional;

public interface IProcedimentoService {
    Procedimento criarProcedimento(Procedimento procedimento);

    List<Procedimento> listarProcedimentos();

    Optional<Procedimento> buscarProcedimentoPorId(Long id);

    Optional<Procedimento> atualizarProcedimento(Long id, Procedimento procedimentoAtualizado);

    boolean deletarProcedimento(Long id);

    Optional<Procedimento> adicionarRecurso(Long id, Recurso recurso);
}
