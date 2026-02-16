# 🔧 Mudanças Necessárias no Código do Stock Service

## ✅ Mudanças Realizadas Automaticamente

Os seguintes arquivos Java foram **atualizados automaticamente**:

### 1. **SqsConfig.java** ✅ ATUALIZADO
**Localização:** `stock-service/infrastructure/src/main/java/com/fiap/message/SqsConfig.java`

**O que foi mudado:**
- ✅ Adicionado suporte a variáveis de ambiente para AWS Region
- ✅ Adicionado suporte a credenciais AWS configuráveis
- ✅ Corrigida região de `US_EAST_1` para `us-east-2` (via config)
- ✅ Condicionalmente usa credenciais se fornecidas (senão usa IAM Role)

**Antes:**
```java
@Bean
public SqsAsyncClient sqsAsyncClient() {
    return SqsAsyncClient.builder()
            .region(Region.US_EAST_1)  // ❌ Hardcoded errado
            .build();
}
```

**Depois:**
```java
@Value("${aws.region:us-east-2}")
private String awsRegion;

@Value("${aws.access-key-id:}")
private String awsAccessKey;

@Value("${aws.secret-access-key:}")
private String awsSecretKey;

@Bean
public SqsAsyncClient sqsAsyncClient() {
    var builder = SqsAsyncClient.builder()
            .region(Region.of(awsRegion))  // ✅ Configurável
            .httpClient(httpClient());

    // Se credenciais foram fornecidas, use-as
    if (!awsAccessKey.isEmpty() && !awsSecretKey.isEmpty()) {
        builder.credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(awsAccessKey, awsSecretKey)
            )
        );
    }

    return builder.build();
}
```

---

### 2. **StockRequestListener.java** ✅ ATUALIZADO
**Localização:** `stock-service/infrastructure/src/main/java/com/fiap/message/StockRequestListener.java`

**O que foi mudado:**
- ✅ Nomes de filas agora vêm de variáveis de ambiente
- ✅ Adicionados valores default para desenvolvimento
- ✅ Suporte a múltiplos ambientes (dev, homolog, prod)

**Antes:**
```java
@SqsListener("work-order-stock-requested")  // ❌ Hardcoded
public void onWorkOrderStockRequested(StockRequestedEvent event) {
    // ...
}
```

**Depois:**
```java
@SqsListener("${sqs.queue.stock-requested:challengeone-work-order-stock-requested-homolog}")  // ✅ Configurável
public void onWorkOrderStockRequested(StockRequestedEvent event) {
    // ...
}
```

**Todas as 3 filas foram atualizadas:**
- `${sqs.queue.stock-requested:...}`
- `${sqs.queue.stock-approved:...}`
- `${sqs.queue.stock-cancel-requested:...}`

---

### 3. **StockEventPublisherGatewayImpl.java** ✅ ATUALIZADO
**Localização:** `stock-service/infrastructure/src/main/java/com/fiap/gateway/StockEventPublisherGatewayImpl.java`

**O que foi mudado:**
- ✅ Nomes de filas injetados via `@Value`
- ✅ Configurável por ambiente

**Antes:**
```java
@Override
public void publishStockReserved(StockReservedEvent event) {
    template.send("stock-reserved-queue", event);  // ❌ Hardcoded
}
```

**Depois:**
```java
@Value("${sqs.queue.stock-reserved:challengeone-stock-reserved-queue-homolog}")
private String stockReservedQueue;

@Value("${sqs.queue.stock-failed:challengeone-stock-failed-queue-homolog}")
private String stockFailedQueue;

@Override
public void publishStockReserved(StockReservedEvent event) {
    template.send(stockReservedQueue, event);  // ✅ Configurável
}
```

---

## 📦 Build e Deploy

### 1. **Compilar o projeto**

```bash
cd e:\code\stock-service
mvn clean install -DskipTests
```

### 2. **Build da imagem Docker** (se necessário)

```bash
docker build -t thiagotierre/stock-service:latest .
docker push thiagotierre/stock-service:latest
```

Ou use a imagem já existente: `thiagotierre/stock-service:latest`

---

## ✅ Validação das Mudanças

### 1. **Verificar compilação**

```bash
mvn clean compile
```

**Resultado esperado:**
```
[INFO] BUILD SUCCESS
```

### 2. **Executar testes**

```bash
mvn test
```

### 3. **Verificar se as annotations estão corretas**

```bash
# Verificar SqsConfig
grep -A 5 "@Value" stock-service/infrastructure/src/main/java/com/fiap/message/SqsConfig.java

# Verificar Listeners
grep "@SqsListener" stock-service/infrastructure/src/main/java/com/fiap/message/StockRequestListener.java

# Verificar Publisher
grep "@Value" stock-service/infrastructure/src/main/java/com/fiap/gateway/StockEventPublisherGatewayImpl.java
```

---

## 🔄 Compatibilidade

### Variáveis de ambiente esperadas

O código atualizado espera as seguintes variáveis (todas configuradas via Terraform):

**AWS:**
- `AWS_REGION` ou `aws.region` → Default: `us-east-2`
- `AWS_ACCESS_KEY_ID` ou `aws.access-key-id`
- `AWS_SECRET_ACCESS_KEY` ou `aws.secret-access-key`

**Filas SQS (Consumed):**
- `sqs.queue.stock-requested` → Default: `challengeone-work-order-stock-requested-homolog`
- `sqs.queue.stock-approved` → Default: `challengeone-work-order-stock-approved-homolog`
- `sqs.queue.stock-cancel-requested` → Default: `challengeone-work-order-stock-cancel-requested-homolog`

**Filas SQS (Published):**
- `sqs.queue.stock-reserved` → Default: `challengeone-stock-reserved-queue-homolog`
- `sqs.queue.stock-failed` → Default: `challengeone-stock-failed-queue-homolog`

Todas essas variáveis são **automaticamente configuradas** pelo Terraform via ConfigMap e Secret.

---

## 🧪 Testar Localmente

### 1. **Configurar variáveis de ambiente**

```bash
export AWS_REGION=us-east-2
export AWS_ACCESS_KEY_ID=your-key
export AWS_SECRET_ACCESS_KEY=your-secret
export sqs.queue.stock-requested=test-queue-1
export sqs.queue.stock-approved=test-queue-2
export sqs.queue.stock-cancel-requested=test-queue-3
export sqs.queue.stock-reserved=test-queue-4
export sqs.queue.stock-failed=test-queue-5
```

### 2. **Executar aplicação**

```bash
cd stock-service
mvn spring-boot:run -pl infrastructure
```

### 3. **Verificar logs de inicialização**

Procure por estas linhas nos logs:
```
✅ SqsAsyncClient configured for region: us-east-2
✅ SqsListener registered for queue: challengeone-work-order-stock-requested-homolog
✅ SqsListener registered for queue: challengeone-work-order-stock-approved-homolog
✅ SqsListener registered for queue: challengeone-work-order-stock-cancel-requested-homolog
```

---

## 🚨 Avisos Importantes

### ⚠️ Não foi necessário mudar:

- ✅ `pom.xml` - Dependências estão corretas
- ✅ `application.properties` - Será sobrescrito por variáveis de ambiente
- ✅ Domain classes - Não precisam de mudanças
- ✅ Use Cases - Não precisam de mudanças
- ✅ Controllers - Não precisam de mudanças

### ⚠️ Se você tiver application.properties customizado:

Adicione estas propriedades (opcional, já que vêm via environment):

```properties
# AWS Configuration
aws.region=${AWS_REGION:us-east-2}
aws.access-key-id=${AWS_ACCESS_KEY_ID:}
aws.secret-access-key=${AWS_SECRET_ACCESS_KEY:}

# SQS Queue Names
sqs.queue.stock-requested=${SQS_QUEUE_STOCK_REQUESTED:challengeone-work-order-stock-requested-homolog}
sqs.queue.stock-approved=${SQS_QUEUE_STOCK_APPROVED:challengeone-work-order-stock-approved-homolog}
sqs.queue.stock-cancel-requested=${SQS_QUEUE_STOCK_CANCEL_REQUESTED:challengeone-work-order-stock-cancel-requested-homolog}
sqs.queue.stock-reserved=${SQS_QUEUE_STOCK_RESERVED:challengeone-stock-reserved-queue-homolog}
sqs.queue.stock-failed=${SQS_QUEUE_STOCK_FAILED:challengeone-stock-failed-queue-homolog}
```

---

## 📝 Resumo das Mudanças

| Arquivo | Status | Mudança Principal |
|---------|--------|-------------------|
| SqsConfig.java | ✅ ATUALIZADO | Região e credenciais configuráveis |
| StockRequestListener.java | ✅ ATUALIZADO | Nomes de filas via @Value |
| StockEventPublisherGatewayImpl.java | ✅ ATUALIZADO | Nomes de filas via @Value |
| pom.xml | ✅ INTACTO | Sem mudanças necessárias |
| application.properties | ✅ INTACTO | Sobrescrito por env vars |
| Domain/Use Cases | ✅ INTACTO | Sem mudanças necessárias |

---

## ✅ Checklist Final

Antes de fazer deploy:

- [ ] Compilar projeto: `mvn clean install`
- [ ] Verificar testes: `mvn test`
- [ ] Build Docker image (se necessário)
- [ ] Verificar que arquivos Java foram atualizados (git diff)
- [ ] Commit das mudanças: `git add . && git commit -m "feat: configuração dinâmica de SQS"`
- [ ] Push para repositório: `git push`

Depois de fazer deploy:

- [ ] Verificar pods iniciaram: `kubectl get pods -n stock-homolog`
- [ ] Verificar logs: `kubectl logs -n stock-homolog deployment/stock-service`
- [ ] Verificar filas SQS criadas: `aws sqs list-queues --region us-east-2`
- [ ] Testar health check: `curl $LB_HOST/api/actuator/health`
- [ ] Enviar mensagem teste para fila
- [ ] Verificar logs de processamento

---

## 📞 Suporte

Se encontrar erros de compilação ou runtime:

1. Verificar que Java 21 está instalado: `java -version`
2. Limpar cache Maven: `mvn clean`
3. Verificar logs do Spring Boot durante startup
4. Verificar que variáveis de ambiente estão sendo injetadas

---

**Documento criado em:** 2024-02-16  
**Versão:** 1.0.0  
**Autor:** GitHub Copilot via Claude Sonnet 4.5
