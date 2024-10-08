package br.com.selfmaintenance.domain.entities.veiculo;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

import br.com.selfmaintenance.domain.entities.usuario.cliente.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

/**
 * [Veiculo] é a entidade que representa um veículo do sistema,
 * para um veículo ser criado é necessário que ele tenha um cliente, placa, tipo, chassi e renavam.
 * O veículo é vinculado a um cliente, eles podem ter vários veículos.
 * 
 * @see Cliente
 * 
 * @version 1.0.0
 * 
 */
@Entity
@Table(name="veiculo", indexes={
  // @Index(name="placa_index", columnList="placa", unique=false)
})
public class Veiculo {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="cliente_id", nullable=false)
  private Cliente cliente;

  @Column(name="placa", nullable=false)
  private String placa;

  @Enumerated(EnumType.STRING)
  @Column(name="tipo", nullable=false)
  private VeiculoTipo tipo;

  @Column(name="marca", nullable=true)
  private String marca;

  @Column(name="modelo", nullable=true)
  private String modelo;

  @Column(name="ano", nullable=true)
  private int ano;

  @Column(name="chassi", nullable=false)
  private String chassi;

  @Size(min = 9, max = 11, message = "Renavam deve ter 11 caracteres")
  @Column(name="renavam", nullable=false)
  private String renavam;

  @Column(name="cor", nullable=true)
  private String cor;

  @Column(name = "data_criacao", columnDefinition = "TIMESTAMP", updatable = false)
  private Timestamp dataCriacao;
  
  @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP")
  private Timestamp dataAtualizacao;
  
  public Veiculo() {
  }

  @PrePersist
  public void onCreate() {
    this.dataCriacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
  }

  @PreUpdate
  public void onUpdate() {
    this.dataAtualizacao = Timestamp.from(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
  }

  public Veiculo(Cliente cliente, String placa, VeiculoTipo tipo, String marca, String modelo, int ano, String chassi, String renavam, String cor) {
    this.cliente = cliente;
    this.placa = placa;
    this.tipo = tipo;
    this.marca = marca;
    this.modelo = modelo;
    this.ano = ano;
    this.chassi = chassi;
    this.renavam = renavam;
    this.cor = cor;
  }

  public Long getId() {
    return this.id;
  }
  
  public String getPlaca() {
    return this.placa;
  }

  public VeiculoTipo getTipo() {
    return this.tipo;
  }

  public String getMarca() {
    return this.marca;
  }

  public String getModelo() {
    return this.modelo;
  }

  public int getAno() {
    return this.ano;
  }

  public String getChassi() {
    return this.chassi;
  }

  public String getRenavam() {
    return this.renavam;
  }

  public String getCor() {
    return this.cor;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  public void setCor(String cor) {
    this.cor = cor;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTipo(VeiculoTipo tipo) {
    this.tipo = tipo;
  }
}