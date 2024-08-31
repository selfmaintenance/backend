package br.com.selfmaintenance.domain.entities.procedimento;

/**
 * [ProcedimentoStatus] é a enumeração que representa os status possíveis de um procedimento.
 * 
 * @see Procedimento
 * 
 * @version 1.0.0
 */
public enum ProcedimentoStatus {
  /**
   * Procedimento foi aberto mas ainda não teve uma resposta
   */
  ABERTO("aberto"),
  /**
   * Procedimento foi agendado
   */
  AGENDADO("agendado"),
  /**
   * Procedimento foi realizado
   */ 
  REALIZADO("realizado"),
  /**
   * Procedimento foi cancelado
   */
  CANCELADO("cancelado");

  private final String status;

  ProcedimentoStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return this.status;
  }
}