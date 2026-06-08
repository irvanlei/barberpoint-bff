# 🔗 BarberPoint - BFF (Backend for Frontend)

## Descrição

Backend para Frontend (BFF) que agrega dados de múltiplos microserviços e consumidores, implementado com **Spring Boot 3.3**. Responsável por orquestração, proxy de requisições e agregação de dados.

## Arquitetura

```
src/main/java/com/barberpoint/bff/
├── api/
│   └── controllers/       # REST endpoints do BFF
├── application/
│   ├── usecases/          # Casos de uso de orquestração e agregação
│   └── dtos/              # DTOs agregados
└── infrastructure/
    └── clients/           # HTTP clients para microserviços e Azure Function
```

O BFF atua como camada de orquestração entre frontend e múltiplos serviços distribuídos. Ele agrega dados de microserviços de agendamento, clientes/barbeiros e Azure Functions em um único contrato de API.

Padrões aplicados:
- **Clean Architecture**
- **Vertical Slice** na organização de código
- **API Gateway + BFF + Microservice + Database Service + Serverless**
- **Event-Driven Architecture** para integração de dados e fallback de chamadas

## Responsabilidades

1. **Agregação de Dados**: Combina dados de múltiplas fontes em um único response
2. **Proxy de Requisições**: Roteia requisições para microserviços
3. **Orquestração**: Coordena chamadas entre serviços
4. **Normalização**: Padroniza responses de diferentes APIs

## Fluxo de Dados

```
Cliente (Frontend)
    ↓
[BFF - Agregador]
    ├→ [MS Agendamentos] (MongoDB)
    ├→ [MS Clientes-Barbeiros] (SQL)
    └→ [Azure Function] (Cálculos)
    ↓
Response Consolidado (JSON)
```

## Tecnologias

- **Java 22**
- **Spring Boot 3.3.0**
- **Spring WebClient** (chamadas HTTP assíncronas)
- **RestTemplate** (chamadas síncronas)
- **Lombok**
- **MapStruct**
- **SpringDoc OpenAPI**
- **JUnit 5** + **WireMock** (testes)

## Pré-requisitos

- Java 22+
- Maven 3.8+
- Microserviços rodando (portas 8081, 8082)
- Azure Function configurada

## Como Rodar Localmente

### 1. Iniciar todos os serviços (docker-compose)

```bash
# Clone o repositório BFF
git clone https://github.com/seu-usuario/barberpoint-bff.git
cd barberpoint-bff

# Use docker-compose para orquestrar tudo
docker-compose up -d

# Verifique o status
curl http://localhost:8080/api/actuator/health
```

### 2. Manualmente

```bash
# Certifique-se de que os serviços estão rodando:
# - MS Agendamentos: localhost:8081
# - MS Clientes-Barbeiros: localhost:8082
# - Azure Function: https://...azurewebsites.net

mvn clean spring-boot:run
```

## Endpoints Disponíveis

### Swagger UI
- **URL**: `http://localhost:8080/api/swagger-ui.html`

### Endpoint Obrigatório: Agregação

```http
GET /api/bff/aggregated-data?clienteId=123&data=2026-04-27
```

**Response**:
```json
{
  "cliente": {
    "id": "123",
    "nome": "João Silva",
    "email": "joao@example.com"
  },
  "agendamentos": [
    {
      "id": "1",
      "barbeiroId": "456",
      "servicoNome": "Corte",
      "dataHora": "2026-04-27T14:00:00Z"
    }
  ],
  "barbeiros": [
    {
      "id": "456",
      "nome": "Pedro Santos",
      "email": "pedro@example.com"
    }
  ],
  "relatorio": {
    "totalClientes": 50,
    "totalReceita": 2500.00,
    "taxaOcupacao": 85,
    "tempoMedioAtendimento": 45
  }
}
```

### Proxy de Agendamentos

```http
GET /api/bff/agendamentos/{id}
POST /api/bff/agendamentos
PUT /api/bff/agendamentos/{id}
DELETE /api/bff/agendamentos/{id}
```

### Proxy de Clientes e Barbeiros

```http
GET /api/bff/clientes
GET /api/bff/barbeiros
GET /api/bff/servicos
```

## Testes

```bash
# Testes unitários
mvn clean test

# Testes de integração (com WireMock)
mvn clean test -DskipIntegrationTests=false
```

## Build e Deploy

### Docker

```bash
docker build -t seu-usuario/barberpoint-bff:v1.0 .
docker push seu-usuario/barberpoint-bff:v1.0
```

## Configuração

### application.properties

```properties
spring.application.name=barberpoint-bff
server.port=8080

# URLs dos Microserviços
ms.agendamentos.url=http://localhost:8081/api
ms.clientes-barbeiros.url=http://localhost:8082/api
azure.function.url=https://barberpoint-function.azurewebsites.net/api
```

## Circuit Breaker (Resilience4j)

```yaml
resilience4j:
  circuitbreaker:
    instances:
      agendamentos:
        registerHealthIndicator: true
        slidingWindowSize: 10
        failureRateThreshold: 50
      clientes-barbeiros:
        registerHealthIndicator: true
        slidingWindowSize: 10
        failureRateThreshold: 50
```

## Monitoramento

```http
GET /api/actuator/health
GET /api/actuator/metrics
GET /api/actuator/prometheus
```

---

## Autores
- Irvanlei de Abreu
- João Yutaka
- Fellipe
- Nelson
- Allan
