package br.com.selfmaintenance.app.validations.veiculo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = TipoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTipo {
  String message() default "Tipo de veículo inválido";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
