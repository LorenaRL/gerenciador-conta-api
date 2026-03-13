package com.banco.conta.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.conta.api.entity.MovimentacaoConta;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoConta, UUID> {

    List<MovimentacaoConta> findByContaId(UUID contaId);
    
    List<MovimentacaoConta> findByContaIdAndDataMovimentacaoBetween(
            UUID contaId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    );
}