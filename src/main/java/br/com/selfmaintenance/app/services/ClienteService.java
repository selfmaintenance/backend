package br.com.selfmaintenance.app.services;

import br.com.selfmaintenance.domain.entities.cliente.ClienteEntity;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioEntity;
import br.com.selfmaintenance.repositories.ClienteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final UsuarioService usuarioService;
    private final ClienteRepository clienteRepository;

    public ClienteService(UsuarioService usuarioService, ClienteRepository clienteRepository) {
        this.usuarioService = usuarioService;
        this.clienteRepository = clienteRepository;
    }

    public void criar(ClienteEntity clienteEntity) throws BadRequestException {
        UsuarioEntity usuarioEntity = this.usuarioService.criar(clienteEntity.getUsuarioEntity());
        this.clienteRepository.save(clienteEntity);
    }
}
