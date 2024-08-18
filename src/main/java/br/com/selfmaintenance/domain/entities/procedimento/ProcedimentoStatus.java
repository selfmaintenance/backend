package br.com.selfmaintenance.domain.entities.procedimento;

public enum ProcedimentoStatus {
  ABERTO("aberto"), // Procedimento foi aberto mas ainda não teve uma resposta
  AGENDADO("agendado"), // Procedimento foi agendado (foi aceito pelo funcionário)
  REALIZADO("realizado"), // Procedimento foi realizado / -> status final não pode ser alterado
  CANCELADO("cancelado"); // Procedimento foi cancelado / -> status final não pode ser alterado

  private final String status;

  ProcedimentoStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return this.status;
  }
}