package br.com.selfmaintenance.app.validations.usuario;

import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
  @Override
  public void initialize(ValidRole constraintAnnotation) {
  }

  @Override
  public boolean isValid(String valor, ConstraintValidatorContext context) {
    return UsuarioRole.CLIENTE.toString().equals(valor) ||
        UsuarioRole.PRESTADOR.toString().equals(valor);
  }
}
