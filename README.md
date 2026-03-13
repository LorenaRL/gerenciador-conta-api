# Gerenciador de Conta Corrente API

API REST desenvolvida em **Java 17** utilizando **Spring Boot**, com persistência de dados em **PostgreSQL** e mapeamento objeto-relacional através do **Hibernate (JPA)**.

A aplicação tem como objetivo realizar a **manutenção de contas correntes**, permitindo operações financeiras básicas como abertura de conta, depósito, saque, transferência entre contas e consulta de extrato.

Cada operação financeira gera um registro de movimentação no banco de dados, garantindo **rastreabilidade e histórico das transações realizadas**.

---

# Funcionalidades

A API permite realizar as seguintes operações:

- Abertura de conta corrente
- Depósito em conta
- Saque em conta
- Transferência entre contas
- Consulta de extrato
- Consulta de extrato por período

---

# Endpoints da API

| Método | Endpoint | Descrição |
|------|------|------|
POST | `/contas` | Criação de conta corrente |
POST | `/contas/deposito` | Realizar depósito |
POST | `/contas/saque` | Realizar saque |
POST | `/contas/transferencia` | Transferência entre contas |
GET | `/contas/{id}/extrato` | Consulta de extrato |


---

# Tecnologias utilizadas

- Java 17  
- Spring Boot  
- PostgreSQL  
- Hibernate / JPA  
- Maven  
- Lombok  
- Spring Boot Actuator  
- Swagger (springdoc-openapi)

---

# Estrutura do projeto

O projeto segue uma arquitetura em camadas:

---

## Documentação da API

A documentação da API pode ser acessada através do **Swagger UI**:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Monitoramento

A aplicação possui endpoints de monitoramento utilizando **Spring Boot Actuator**.

Endpoint de verificação de saúde da aplicação:

[http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

# Banco de dados

O banco utilizado é o **PostgreSQL**.

Schema utilizado:

```sql
CREATE SCHEMA debcred;

---
