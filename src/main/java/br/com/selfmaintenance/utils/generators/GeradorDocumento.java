package br.com.selfmaintenance.utils.generators;

import java.util.Random;

public class GeradorDocumento {
  private static final Random random = new Random();

  public static String gerarCPF() {
    int[] numeros = new int[9];
    int soma = 0;

    // Gerar os primeiros 9 dígitos do CPF
    for (int i = 0; i < 9; i++) {
      numeros[i] = GeradorDocumento.random.nextInt(10);
      soma += numeros[i] * (10 - i);
    }

    // Calcular o primeiro dígito verificador
    int primeiroDigitoVerificador = 11 - (soma % 11);
    if (primeiroDigitoVerificador >= 10) {
      primeiroDigitoVerificador = 0;
    }

    soma = 0;
    for (int i = 0; i < 9; i++) {
      soma += numeros[i] * (11 - i);
    }
    soma += primeiroDigitoVerificador * 2;

    // Calcular o segundo dígito verificador
    int segundoDigitoVerificador = 11 - (soma % 11);
    if (segundoDigitoVerificador >= 10) {
      segundoDigitoVerificador = 0;
    }

    // Construir o CPF no formato XXX.XXX.XXX-XX
    StringBuilder cpf = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      cpf.append(numeros[i]);
      if (i == 2 || i == 5) {
        cpf.append(".");
      }
    }
    cpf.append("-").append(primeiroDigitoVerificador).append(segundoDigitoVerificador);

    return cpf.toString();
  }

  public static String gerarCNPJ() {
    int[] numeros = new int[12];
    int soma = 0;

    // Gerar os primeiros 12 dígitos do CNPJ
    for (int i = 0; i < 12; i++) {
      numeros[i] = GeradorDocumento.random.nextInt(10);
      if (i < 4) {
        soma += numeros[i] * (5 - i);
      } else {
        soma += numeros[i] * (13 - i);
      }
    }

    // Calcular o primeiro dígito verificador
    int primeiroDigitoVerificador = 11 - (soma % 11);
    if (primeiroDigitoVerificador >= 10) {
      primeiroDigitoVerificador = 0;
    }

    soma = 0;
    for (int i = 0; i < 12; i++) {
      if (i < 5) {
        soma += numeros[i] * (6 - i);
      } else {
        soma += numeros[i] * (14 - i);
      }
    }
    soma += primeiroDigitoVerificador * 2;

    // Calcular o segundo dígito verificador
    int segundoDigitoVerificador = 11 - (soma % 11);
    if (segundoDigitoVerificador >= 10) {
      segundoDigitoVerificador = 0;
    }

    // Construir o CNPJ no formato XX.XXX.XXX/XXXX-XX
    StringBuilder cnpj = new StringBuilder();
    for (int i = 0; i < 12; i++) {
      cnpj.append(numeros[i]);
      if (i == 1 || i == 4) {
        cnpj.append(".");
      }
      if (i == 7) {
        cnpj.append("/");
      }
    }
    cnpj.append("-").append(primeiroDigitoVerificador).append(segundoDigitoVerificador);

    return cnpj.toString();
  }
}