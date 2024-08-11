package br.com.selfmaintenance.app.services;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import br.com.selfmaintenance.domain.entities.cliente.ClienteEntity;
import br.com.selfmaintenance.repositories.ClienteRepository;

@Service
public class ClienteService {
    private final UsuarioService usuarioService;
    private final ClienteRepository clienteRepository;

    public ClienteService(UsuarioService usuarioService, ClienteRepository clienteRepository) {
        this.usuarioService = usuarioService;
        this.clienteRepository = clienteRepository;
    }

    public void criar(ClienteEntity clienteEntity) throws BadRequestException {
        this.clienteRepository.save(clienteEntity);
    }
}
