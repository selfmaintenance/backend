package br.com.selfmaintenance.domain.entities.usuario;

public enum UsuarioRole {
    CLIENTE("cliente"),
    FUNCIONARIO("funcionario"),
    ADMIN("admin");

    private final String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
