package br.com.selfmaintenance.app.services.procedimento;

import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import br.com.selfmaintenance.infra.repositories.procedimento.ProcedimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcedimentoService {

    private final ProcedimentoRepository procedimentoRepository;

    public ProcedimentoService(ProcedimentoRepository procedimentoRepository) {
        this.procedimentoRepository = procedimentoRepository;
    }

    public Procedimento criarProcedimento(Procedimento procedimento) {
        return procedimentoRepository.save(procedimento);
    }

    public List<Procedimento> listarProcedimentos() {
        return procedimentoRepository.findAll();
    }

    public Optional<Procedimento> buscarProcedimentoPorId(Long id) {
        return procedimentoRepository.findById(id);
    }

    public Optional<Procedimento> atualizarProcedimento(Long id, Procedimento procedimentoAtualizado) {
        return procedimentoRepository.findById(id).map(procedimento -> {
            procedimento.setNome(procedimentoAtualizado.getNome());
            procedimento.setRecursos(procedimentoAtualizado.getRecursos());
            procedimento.setStatus(procedimentoAtualizado.getStatus());
            return procedimentoRepository.save(procedimento);
        });
    }

    public boolean deletarProcedimento(Long id) {
        return procedimentoRepository.findById(id).map(procedimento -> {
            procedimentoRepository.delete(procedimento);
            return true;
        }).orElse(false);
    }

    public Optional<Procedimento> adicionarRecurso(Long id, Recurso recurso) {
        return procedimentoRepository.findById(id).map(procedimento -> {
            procedimento.adicionarRecurso(recurso);
            return procedimentoRepository.save(procedimento);
        });
    }

}
