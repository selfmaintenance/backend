package br.com.selfmaintenance.app.services.usuario.prestador;

import java.util.Map;

import br.com.selfmaintenance.app.records.prestador.CriarPrestadorDTO;
import br.com.selfmaintenance.utils.exceptions.ServiceException;

/**
 * [IPrestadorService] é a interface que define os métodos para a camada de serviço de prestadores do sistema.
 * 
 * @version 1.0.0
 */
public interface IPrestadorService {
  /**
   * [criar] cria um prestador no sistema.
   * 
   * @param dados é o DTO com os dados do prestador
   * @param emailOficina é o email da oficina que está criando o prestador
   * 
   * @return um mapa com o id do prestador criado
   * @throws ServiceException se ocorrer um erro ao criar o prestador
   */
  Map<String, Long> criar(CriarPrestadorDTO dados, String emailOficina) throws ServiceException;
}
