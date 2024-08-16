package br.com.selfmaintenance.domain.entities.veiculo;

public enum VeiculoTipo {
    CARRO("carro"),
    MOTO("moto"),
    CAMINHAO("caminhao"),
    ONIBUS("onibus"),
    VAN("van"),
    CAMINHONETE("caminhonete"),
    TRATOR("trator"),
    BARCO("barco"),
    OUTRO("outro");
  
    private final String tipo;

    VeiculoTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}