package br.com.selfmaintenance.app.services.usuario;

import java.util.Map;

import br.com.selfmaintenance.app.records.usuario.CriarUsuarioDTO;
import br.com.selfmaintenance.utils.exceptions.ServiceException;

/**
 * [IUsuarioService] é a interface que define os métodos para a camada de serviço de usuários do sistema.
 * 
 * @version 1.0.0
 */
public interface IUsuarioService {
    
  /**
   * [criar] cria um usuário no sistema.
   * 
   * @param dados é o DTO com os dados do usuário
   * 
   * @return um mapa com o id do usuário criado
   * @throws ServiceException se ocorrer um erro ao criar o usuário
   */
  Map<String, Long> criar(CriarUsuarioDTO dados) throws ServiceException;
}
