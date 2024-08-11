package br.com.selfmaintenance.presentation.http.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.selfmaintenance.app.services.ClienteService;
import br.com.selfmaintenance.domain.entities.cliente.ClienteEntity;
import br.com.selfmaintenance.utils.RespostaApi;
import jakarta.validation.Valid;

@RequestMapping("/cliente")
@RestController
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("")
    public ResponseEntity<RespostaApi> criar(@RequestBody @Valid ClienteEntity cliente) throws BadRequestException {
        this.clienteService.criar(cliente);

        return ResponseEntity.ok(new RespostaApi(1, "Cliente criado com sucesso", cliente));
    }
}

