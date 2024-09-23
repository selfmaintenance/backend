package br.com.selfmaintenance.app.services.procedimento;

import br.com.selfmaintenance.app.records.procedimento.CriarProcedimentoDTO;
import br.com.selfmaintenance.app.records.procedimento.DetalhesProcedimentoDTO;
import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.usuario.oficina.Prestador;
import br.com.selfmaintenance.infra.repositories.procedimento.ProcedimentoRepository;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.usuario.oficina.PrestadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProcedimentoService implements IProcedimentoService {

    private final ProcedimentoRepository procedimentoRepository;
    private final ClienteRepository clienteRepository;
    private final PrestadorRepository prestadorRepository;

    public ProcedimentoService(ProcedimentoRepository procedimentoRepository, ClienteRepository clienteRepository, PrestadorRepository prestadorRepository) {
        this.procedimentoRepository = procedimentoRepository;
        this.clienteRepository = clienteRepository;
        this.prestadorRepository = prestadorRepository;
    }

    // Criar ou atualizar um procedimento
    public DetalhesProcedimentoDTO criar(CriarProcedimentoDTO dados) {
        // Verifica se o cliente existe
        Optional<Cliente> clienteOptional = clienteRepository.findById(dados.clienteId());
        if (clienteOptional.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        // Verifica se o prestador existe
        Optional<Prestador> prestadorOptional = prestadorRepository.findById(dados.prestadorId());
        if (prestadorOptional.isEmpty()) {
            throw new IllegalArgumentException("Prestador não encontrado.");
        }

        var procedimento = new Procedimento(
                prestadorOptional.get(),
                clienteOptional.get(),
                dados.nome(),
                dados.descricao()
        );

        // Salvar o procedimento
        Procedimento novoProcedimento = procedimentoRepository.save(procedimento);

        // Retorna o DTO de detalhamento
        return new DetalhesProcedimentoDTO(novoProcedimento);
    }

    // Buscar procedimento por ID
    public Procedimento buscarPorId(Long id) {
        return procedimentoRepository.getReferenceById(id);
    }

    // Buscar todos os procedimentos
    public Page<Procedimento> listar(Pageable paginacao) {
        return procedimentoRepository.findAll(paginacao);
    }

}
