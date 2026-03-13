package com.banco.conta.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.conta.api.dto.CriarContaRequest;
import com.banco.conta.api.dto.DepositoRequest;
import com.banco.conta.api.dto.SaqueRequest;
import com.banco.conta.api.dto.TransferenciaRequest;
import com.banco.conta.api.entity.ContaCorrente;
import com.banco.conta.api.entity.MovimentacaoConta;
import com.banco.conta.api.service.ContaService;


@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaCorrente> criarConta( @RequestBody CriarContaRequest request) {

        return ResponseEntity.ok(contaService.criarConta(request));
    }

    @PostMapping("/deposito")
    public ResponseEntity<String> depositar(
            @RequestBody DepositoRequest request) {

        contaService.depositar(request);

        return ResponseEntity.ok("Depósito realizado");
    }

    @PostMapping("/saque")
    public ResponseEntity<String> sacar(
            @RequestBody SaqueRequest request) {

        contaService.sacar(request);

        return ResponseEntity.ok("Saque realizado");
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> transferir(
            @RequestBody TransferenciaRequest request) {

        contaService.transferir(request);

        return ResponseEntity.ok("Transferência realizada");
    }
    
    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<MovimentacaoConta>> extrato(
            @PathVariable UUID id,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        if(dataInicio != null && dataFim != null) {
            return ResponseEntity.ok(
                    contaService.extratoPeriodo(id, dataInicio, dataFim)
            );
        }

        return ResponseEntity.ok(contaService.extrato(id));
    }
}