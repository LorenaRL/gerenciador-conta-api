package com.banco.conta.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.banco.conta.api.dto.CriarContaRequest;
import com.banco.conta.api.dto.DepositoRequest;
import com.banco.conta.api.dto.SaqueRequest;
import com.banco.conta.api.dto.TransferenciaRequest;
import com.banco.conta.api.entity.ContaCorrente;
import com.banco.conta.api.entity.MovimentacaoConta;
import com.banco.conta.api.enums.TipoMovimentacao;
import com.banco.conta.api.repository.ContaRepository;
import com.banco.conta.api.repository.MovimentacaoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ContaService(
            ContaRepository contaRepository,
            MovimentacaoRepository movimentacaoRepository) {

        this.contaRepository = contaRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public ContaCorrente criarConta(CriarContaRequest request) {

        ContaCorrente conta = new ContaCorrente();

        conta.setId(UUID.randomUUID());
        conta.setNumeroConta(String.valueOf(new Random().nextInt(99999)));
        conta.setDigitoVerificador("1");
        conta.setCpfCnpj(request.getCpfCnpj());
        conta.setSaldo(BigDecimal.ZERO);
        conta.setDataCriacao(LocalDateTime.now());
        
        log.info("Iniciando criação de conta chave:{}", conta.getId());
        
        return contaRepository.save(conta);
    }

    @Transactional
    public void depositar(DepositoRequest request) {

        ContaCorrente conta = contaRepository
                .buscarContaParaAtualizacao(
                        request.getNumeroConta(),
                        request.getDigito())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setSaldo(conta.getSaldo().add(request.getValor()));

        registrarMovimentacao(conta, request.getValor(), TipoMovimentacao.DEPOSITO);
    }

    @Transactional
    public void sacar(SaqueRequest request) {

        ContaCorrente conta = contaRepository
                .buscarContaParaAtualizacao(
                        request.getNumeroConta(),
                        request.getDigito())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (conta.getSaldo().compareTo(request.getValor()) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        conta.setSaldo(conta.getSaldo().subtract(request.getValor()));

        registrarMovimentacao(conta, request.getValor(), TipoMovimentacao.SAQUE);
    }

    @Transactional
    public void transferir(TransferenciaRequest request) {

        ContaCorrente origem = contaRepository
                .buscarContaParaAtualizacao(
                        request.getContaOrigem(),
                        request.getDigitoOrigem())
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

        ContaCorrente destino = contaRepository
                .buscarContaParaAtualizacao(
                        request.getContaDestino(),
                        request.getDigitoDestino())
                .orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));

        if (origem.getSaldo().compareTo(request.getValor()) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        origem.setSaldo(origem.getSaldo().subtract(request.getValor()));
        destino.setSaldo(destino.getSaldo().add(request.getValor()));

        registrarMovimentacao(origem, request.getValor(), TipoMovimentacao.TRANSFERENCIA_SAIDA);
        registrarMovimentacao(destino, request.getValor(), TipoMovimentacao.TRANSFERENCIA_ENTRADA);
    }

    public List<MovimentacaoConta> extrato(UUID contaId) {

        return movimentacaoRepository.findByContaId(contaId);
    }

    private void registrarMovimentacao(
            ContaCorrente conta,
            BigDecimal valor,
            TipoMovimentacao tipo) {
    	
    	log.info("Iniciando movimentação {} da conta {}", tipo, conta);
    	
        MovimentacaoConta mov = new MovimentacaoConta();

        mov.setId(UUID.randomUUID());
        mov.setConta(conta);
        mov.setValor(valor);
        mov.setTipoMovimentacao(tipo);
        mov.setDataMovimentacao(LocalDateTime.now());

        log.info("Registrando a  movimentação {} da conta {}", tipo, conta);
        movimentacaoRepository.save(mov);
        log.info("movimentação {} da conta {} CONCLUÍDA", tipo, conta);
    }
    
	public List<MovimentacaoConta> extratoPeriodo(
	        UUID contaId,
	        LocalDate dataInicio,
	        LocalDate dataFim) {

	    LocalDateTime inicio = dataInicio.atStartOfDay();
	    LocalDateTime fim = dataFim.atTime(23,59,59);

	    return movimentacaoRepository
	            .findByContaIdAndDataMovimentacaoBetween(
	                    contaId,
	                    inicio,
	                    fim
	            );
	}
}
