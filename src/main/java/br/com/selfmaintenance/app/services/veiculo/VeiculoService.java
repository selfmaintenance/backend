package br.com.selfmaintenance.app.services.veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.EditarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;
import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import br.com.selfmaintenance.domain.entities.veiculo.Veiculo;
import br.com.selfmaintenance.domain.entities.veiculo.VeiculoTipo;
import br.com.selfmaintenance.infra.repositories.usuario.ClienteRepository;
import br.com.selfmaintenance.infra.repositories.veiculo.VeiculoRepository;

/**
 * @author David Saymmon
 * 
 * [VeiculoService] é a classe que representa a camada de serviço de veículos do sistema.
 * 
 * @see IVeiculoService
 * 
 * @version 1.0.0
 */
@Service
public class VeiculoService implements IVeiculoService {
  private final VeiculoRepository veiculoRepository;
  private final ClienteRepository clienteRepository;

  public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
    this.veiculoRepository = veiculoRepository;
    this.clienteRepository = clienteRepository;
  }

  /**
   * [criar] é o método que cria um veículo no sistema.
   * 
   * @param dados é o DTO com os dados do veículo
   * @param emailCliente é o email do cliente que está criando o veículo
   * 
   * @see CriarVeiculoDTO
   * @see Veiculo
   * @see Cliente
   * 
   * @return um mapa com o id do veículo criado
   */
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

  /**
   * [editar] é o método que edita um veículo no sistema.
   * 
   * @param id é o id do veículo a ser editado
   * @param dados é o DTO com os dados do veículo
   * @param emailCliente é o email do cliente que está editando o veículo
   * 
   * @see EditarVeiculoDTO
   * @see Veiculo
   * @see Cliente
   * @see VeiculoResponseDTO
   * 
   * @return um DTO com os dados do veículo editado
   */
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
  
  /**
   * [listar] é o método que lista os veículos de um cliente no sistema.
   * 
   * @param emailCliente é o email do cliente que está listando os veículos
   * 
   * @see Veiculo
   * @see Cliente
   * @see VeiculoResponseDTO
   * 
   * @return uma lista de DTOs com os dados dos veículos
   */
  public List<VeiculoResponseDTO> listar(String emailCliente) {
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

  /**
   * [buscar] é o método que busca um veículo no sistema.
   * 
   * @param id é o id do veículo a ser buscado
   * @param emailCliente é o email do cliente que está buscando o veículo
   * 
   * @see Veiculo
   * @see Cliente
   * @see VeiculoResponseDTO
   * 
   * @return um DTO com os dados do veículo buscado
   */
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