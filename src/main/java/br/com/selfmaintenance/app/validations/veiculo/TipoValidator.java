package br.com.selfmaintenance.app.validations.veiculo;

import br.com.selfmaintenance.domain.entities.veiculo.VeiculoTipo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TipoValidator implements ConstraintValidator<ValidTipo, String> {
  @Override
  public void initialize(ValidTipo constraintAnnotation) {
  }

  @Override
  public boolean isValid(String valor, ConstraintValidatorContext context) {
    if (valor == null) {
      return true;
    }
    return VeiculoTipo.CARRO.toString().equals(valor) ||
      VeiculoTipo.MOTO.toString().equals(valor) ||
      VeiculoTipo.CAMINHAO.toString().equals(valor) ||
      VeiculoTipo.ONIBUS.toString().equals(valor) ||
      VeiculoTipo.VAN.toString().equals(valor) ||
      VeiculoTipo.CAMINHONETE.toString().equals(valor) ||
      VeiculoTipo.TRATOR.toString().equals(valor) ||
      VeiculoTipo.BARCO.toString().equals(valor) ||
      VeiculoTipo.OUTRO.toString().equals(valor);
  }
}