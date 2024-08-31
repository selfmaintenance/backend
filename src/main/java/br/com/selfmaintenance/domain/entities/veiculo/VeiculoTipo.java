package br.com.selfmaintenance.domain.entities.veiculo;

/**
 * [VeiculoTipo] é a enumeração que representa o tipo de veículo.
 * 
 * @see Veiculo
 * 
 * @version 1.0.0
 */
public enum VeiculoTipo {
  /**
   * Carro
   */
  CARRO("carro"),
  /**
   * Moto
   */
  MOTO("moto"),
  /**
   * Caminhão
   */
  CAMINHAO("caminhao"),
  /**
   * Ônibus
   */
  ONIBUS("onibus"),
  /**
   * Van
   */
  VAN("van"),
  /**
   * Caminhonete
   */
  CAMINHONETE("caminhonete"),
  /**
   * Trator
   */
  TRATOR("trator"),
  /**
   * Barco
   */
  BARCO("barco"),
  /**
   * Outro tipo não especificado
   */
  OUTRO("outro");

  private final String tipo;

  VeiculoTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getTipo() {
    return tipo;
  }
}