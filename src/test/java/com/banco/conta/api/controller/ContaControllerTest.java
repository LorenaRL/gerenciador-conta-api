package com.banco.conta.api.controller;


	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.banco.conta.api.dto.CriarContaRequest;
import com.banco.conta.api.dto.DepositoRequest;
import com.banco.conta.api.dto.SaqueRequest;
import com.banco.conta.api.dto.TransferenciaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

	@SpringBootTest
	@AutoConfigureMockMvc
	public class ContaControllerTest {

	    @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @Test
	    void deveCriarConta() throws Exception {

	        CriarContaRequest request = new CriarContaRequest();
	        request.setCpfCnpj("12345678900");

	        mockMvc.perform(post("/contas")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk());
	    }

	    @Test
	    void deveRealizarDeposito() throws Exception {

	        DepositoRequest request = new DepositoRequest();
	        request.setNumeroConta("12345");
	        request.setDigito("1");
	        request.setValor(BigDecimal.valueOf(500));

	        mockMvc.perform(post("/contas/deposito")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk());
	    }

	    @Test
	    void deveRealizarSaque() throws Exception {

	        SaqueRequest request = new SaqueRequest();
	        request.setNumeroConta("12345");
	        request.setDigito("1");
	        request.setValor(BigDecimal.valueOf(100));

	        mockMvc.perform(post("/contas/saque")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk());
	    }

	    @Test
	    void deveRealizarTransferencia() throws Exception {

	        TransferenciaRequest request = new TransferenciaRequest();
	        request.setContaOrigem("12345");
	        request.setDigitoOrigem("1");
	        request.setContaDestino("67890");
	        request.setDigitoDestino("1");
	        request.setValor(BigDecimal.valueOf(50));

	        mockMvc.perform(post("/contas/transferencia")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk());
	    }

	    @Test
	    void deveConsultarExtrato() throws Exception {

	        UUID contaId = UUID.randomUUID();

	        mockMvc.perform(get("/contas/{id}/extrato", contaId))
	                .andExpect(status().isOk());
	    }
	}