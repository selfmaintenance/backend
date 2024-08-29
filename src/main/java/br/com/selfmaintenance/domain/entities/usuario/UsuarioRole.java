package br.com.selfmaintenance.domain.entities.usuario;

public enum UsuarioRole {
  CLIENTE("cliente"),
  OFICINA("oficina"), // -> prestador só pode existir no sistema se estiver ligado a uma oficina já existente
  PRESTADOR("prestador");
  
  private final String role;

  UsuarioRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return this.role;
  }
}
