package com.banco.conta.api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(schema = "debcred", name = "conta_corrente")
public class ContaCorrente {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "numero_conta", nullable = false, length = 10)
    private String numeroConta;

    @Column(name = "digito_verificador", nullable = false, length = 2)
    private String digitoVerificador;

    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;

    @Column(name = "saldo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}