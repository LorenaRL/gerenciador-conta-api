package com.banco.conta.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaRequest {

    private String contaOrigem;
    private String digitoOrigem;

    private String contaDestino;
    private String digitoDestino;

    private BigDecimal valor;
    
    

}