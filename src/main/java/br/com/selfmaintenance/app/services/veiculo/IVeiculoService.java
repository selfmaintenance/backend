package br.com.selfmaintenance.app.services.veiculo;

import java.util.List;
import java.util.Map;

import br.com.selfmaintenance.app.records.veiculo.CriarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.EditarVeiculoDTO;
import br.com.selfmaintenance.app.records.veiculo.VeiculoResponseDTO;

/**
 * [IVeiculoService] é a interface que define os métodos para a camada de serviço de veículos do sistema.
 * 
 * @version 1.0.0
 */
public interface IVeiculoService {
  /**
   * [criar] cria um veículo no sistema.
   * 
   * @param dados é o DTO com os dados do veículo
   * @param emailCliente é o email do cliente que está criando o veículo
   * 
   * @return um mapa com o id do veículo criado
   */
  Map<String, Long> criar(CriarVeiculoDTO dados, String emailCliente);

  /**
   * [editar] edita um veículo no sistema.
   * 
   * @param id é o id do veículo a ser editado
   * @param dados é o DTO com os dados do veículo
   * @param emailCliente é o email do cliente que está editando o veículo
   * 
   * @return um DTO com os dados do veículo editado
   */
  VeiculoResponseDTO editar(Long id, EditarVeiculoDTO dados, String emailCliente);

  /**
   * [listar] lista os veículos de um cliente no sistema.
   * 
   * @param emailCliente é o email do cliente que está listando os veículos
   * 
   * @return uma lista de DTOs com os dados dos veículos
   */
  List<VeiculoResponseDTO> listar(String emailCliente);

  /**
   * [buscar] busca um veículo no sistema.
   * 
   * @param id é o id do veículo a ser buscado
   * @param emailCliente é o email do cliente que está buscando o veículo
   * 
   * @return um DTO com os dados do veículo buscado
   */
  VeiculoResponseDTO buscar(Long id, String emailCliente);

  /**
   * [deletar] deleta um veículo do sistema.
   * 
   * @param id é o id do veículo a ser deletado
   * @param emailCliente é o email do cliente que está deletando o veículo
   * 
   * @return true se o veículo foi deletado, false caso contrário
   */
  boolean deletar(Long id, String emailCliente);
}
