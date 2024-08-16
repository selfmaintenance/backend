package br.com.selfmaintenance.domain.entities.veiculo;

import br.com.selfmaintenance.domain.entities.usuario.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "veiculo", indexes={
  // @Index(name="placa_index", columnList="placa", unique=false)
})
public class Veiculo { // TODO: Adicionar coluna para tipo de veículo (carro, moto, caminhão, onibus, van, caminhonete, trator, barco, outro)
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable=false)
  private Cliente cliente;

  @NotBlank(message = "Placa não pode ser vazia")
  @Size(min = 7, max = 7, message = "Placa deve ter 7 caracteres")
  @Column(name = "placa", nullable=false)
  private String placa;

  @NotBlank(message = "Tipo não pode ser vazio")
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo", nullable=false)
  private VeiculoTipo tipo;

  @Column(name = "marca", nullable=true)
  private String marca;

  @Column(name = "modelo", nullable=true)
  private String modelo;

  @Column(name = "ano", nullable=true)
  private int ano;

  @Column(name = "chassi", nullable=false)
  private String chassi;

  @Size(min = 9, max = 11, message = "Renavam deve ter 11 caracteres")
  @Column(name = "renavam", nullable=false)
  private String renavam;

  @Column(name = "cor", nullable=true)
  private String cor;

  public Veiculo() {
  }
}