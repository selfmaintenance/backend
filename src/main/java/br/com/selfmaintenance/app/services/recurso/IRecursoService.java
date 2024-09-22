package br.com.selfmaintenance.app.services.recurso;

import java.util.List;
import java.util.Map;

import br.com.selfmaintenance.app.records.recurso.CriarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.EditarRecursoDTO;
import br.com.selfmaintenance.app.records.recurso.RecursoResponseDTO;

public interface IRecursoService {
  /**
   * Cria um novo recurso no sistema.
   *
   * @param dados DTO com os dados do recurso
   * @param email email do usuário autenticado
   * @return um mapa contendo o ID do recurso criado
   */
  Map<String, Long> criar(CriarRecursoDTO dados, String email);

  /**
   * Edita um recurso existente no sistema.
   *
   * @param id ID do recurso a ser editado
   * @param dados DTO com os dados atualizados do recurso
   * @param email email do usuário autenticado
   * @return um DTO com os dados do recurso editado
   */
  RecursoResponseDTO editar(Long id, EditarRecursoDTO dados, String email);

  /**
   * Lista os recursos de uma oficina.
   *
   * @param email email do usuário autenticado
   * @return uma lista de DTOs com os dados dos recursos
   */
  List<RecursoResponseDTO> listar(String email);

  /**
   * Busca um recurso pelo ID no sistema.
   *
   * @param id ID do recurso a ser buscado
   * @param email email do usuário autenticado
   * @return um DTO com os dados do recurso buscado
   */
  RecursoResponseDTO buscar(Long id, String email);

  /**
   * Deleta um recurso do sistema.
   *
   * @param id ID do recurso a ser deletado
   * @param email email do usuário autenticado
   * @return um booleano indicando se o recurso foi deletado com sucesso
   */
  boolean deletar(Long id, String email);
}