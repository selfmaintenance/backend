package br.com.selfmaintenance.demo.entities.usuario;

public enum UsuarioRole {
    CLIENTE("cliente"),
    FUNCIONARIO("funcionario"),
    ADMINISTRADOR("administrador");

    private final String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
