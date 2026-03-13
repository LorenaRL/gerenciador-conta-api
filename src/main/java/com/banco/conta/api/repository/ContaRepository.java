package com.banco.conta.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banco.conta.api.entity.ContaCorrente;

import jakarta.persistence.LockModeType;

public interface ContaRepository extends JpaRepository<ContaCorrente, UUID> {

	 @Lock(LockModeType.PESSIMISTIC_WRITE)
	    @Query("""
	        SELECT c
	        FROM ContaCorrente c
	        WHERE c.numeroConta = :numero
	        AND c.digitoVerificador = :digito
	    """)
	    Optional<ContaCorrente> buscarContaParaAtualizacao(
	            @Param("numero") String numero,
	            @Param("digito") String digito);
}
