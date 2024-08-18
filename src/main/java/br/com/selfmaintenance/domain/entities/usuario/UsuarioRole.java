package br.com.selfmaintenance.domain.entities.usuario;

public enum UsuarioRole {
  CLIENTE("cliente"),
  PRESTADOR("prestador");

  private final String role;

  UsuarioRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return this.role;
  }
}
