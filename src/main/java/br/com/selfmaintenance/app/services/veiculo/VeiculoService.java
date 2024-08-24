package br.com.selfmaintenance.app.services.veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.EditarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;
import br.com.selfmaintenance.domain.entities.usuario.Cliente;
import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;
import br.com.selfmaintenance.domain.entities.veiculo.VeiculoTipo;
import br.com.selfmaintenance.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.repositories.veiculo.VeiculoRepository;

@Service
public class VeiculoService {
  private final VeiculoRepository veiculoRepository;
  private final ClienteRepository clienteRepository;

  public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
    this.veiculoRepository = veiculoRepository;
    this.clienteRepository = clienteRepository;
  }

  public Map<String, Long> criar(CriarVeiculoDTO dados, String emailCliente) {
    Cliente cliente = this.clienteRepository.findByEmail(emailCliente);
    Veiculo veiculoSalvo = this.veiculoRepository.save(new Veiculo(
      cliente,
      dados.placa(),
      VeiculoTipo.valueOf(dados.tipo()),
      dados.marca(),
      dados.modelo(),
      dados.ano(),
      dados.chassi(),
      dados.renavam(),
      dados.cor()
    ));

    return Map.of("idVeiculo", veiculoSalvo.getId());
  }

  public VeiculoResponseDTO editar(Long id, EditarVeiculoDTO dados, String emailCliente) {
    Cliente cliente = this.clienteRepository.findByEmail(emailCliente);
    Veiculo veiculo = this.veiculoRepository.findByClienteAndId(cliente, id);

    if (veiculo == null) {
      return null;
    }

    dados.marca().ifPresent(veiculo::setMarca);
    dados.modelo().ifPresent(veiculo::setModelo);
    dados.ano().ifPresent(veiculo::setAno);
    dados.cor().ifPresent(veiculo::setCor);
    if (dados.tipo() != null) {
      veiculo.setTipo(VeiculoTipo.valueOf(dados.tipo()));
    }
    
    this.veiculoRepository.save(veiculo);

    return new VeiculoResponseDTO(
      veiculo.getId(),
      veiculo.getPlaca(),
      veiculo.getTipo(),
      veiculo.getMarca(),
      veiculo.getModelo(),
      veiculo.getAno(),
      veiculo.getChassi(),
      veiculo.getRenavam(),
      veiculo.getCor()
    );
  }
  
  public List<VeiculoResponseDTO> listar(String emailCliente) {
    Cliente cliente = this.clienteRepository.findByEmail(emailCliente);
    List<Veiculo> veiculos =  this.veiculoRepository.findByCliente_email(emailCliente);

    List<VeiculoResponseDTO> veiculosResponse = new ArrayList<>();
    for (Veiculo veiculo : veiculos) {
      veiculosResponse.add(new VeiculoResponseDTO(
        veiculo.getId(),
        veiculo.getPlaca(),
        veiculo.getTipo(),
        veiculo.getMarca(),
        veiculo.getModelo(),
        veiculo.getAno(),
        veiculo.getChassi(),
        veiculo.getRenavam(),
        veiculo.getCor()
      ));
    }

    return veiculosResponse;
  }

  public VeiculoResponseDTO buscar(Long id, String emailCliente) {
    Cliente cliente = this.clienteRepository.findByEmail(emailCliente);
    Veiculo veiculo = this.veiculoRepository.findByClienteAndId(cliente, id);

    if (veiculo == null) {
      return null;
    }

    return new VeiculoResponseDTO(
      veiculo.getId(),
      veiculo.getPlaca(),
      veiculo.getTipo(),
      veiculo.getMarca(),
      veiculo.getModelo(),
      veiculo.getAno(),
      veiculo.getChassi(),
      veiculo.getRenavam(),
      veiculo.getCor()
    );
  }

  public boolean deletar(Long id, String emailCliente) {
    Cliente cliente = this.clienteRepository.findByEmail(emailCliente);
    Veiculo veiculo = this.veiculoRepository.findByClienteAndId(cliente, id);

    if (veiculo == null) {
      return false;
    }
    this.veiculoRepository.delete(veiculo);
    return true;
  }
}