
# README

## Visão Geral

Este projeto é uma aplicação composta por três diferentes módulos, cada um desempenhando um papel essencial na arquitetura de segurança baseada em OAuth2 com JWT. Os módulos são:

1. **Authorization Server** (`authorization-server`)
2. **Resource Server** (`resource-server`)
3. **Client Server** (`client-server`)

## Estrutura da Aplicação

### 1. Authorization Server

O Authorization Server é responsável por autenticar usuários e emitir tokens de acesso. Ele inclui a configuração de segurança, armazenamento de tokens, e configurações específicas do cliente.

- **Arquivos Principais**:
    - `AuthorizationServerApplication.java`: Classe principal para executar o servidor de autorização.
    - `AuthorizationServerConfig.java`: Configuração do servidor de autorização.
    - `ClientStoreConfig.java`: Configuração do armazenamento do cliente.
    - `SecurityFilterConfig.java`: Configuração dos filtros de segurança.
    - `TokenStoreConfig.java`: Configuração do armazenamento de tokens.
    - `UserStoreConfig.java`: Configuração dos detalhes dos usuários.
    - `application.properties`: Arquivo de configuração da aplicação.

### 2. Resource Server

O Resource Server é responsável por verificar a validade dos tokens e fornecer acesso aos recursos protegidos. Utiliza JWT para a verificação dos tokens emitidos pelo Authorization Server.

- **Arquivos Principais**:
    - `ResourceServerApplication.java`: Classe principal para executar o servidor de recursos.
    - `TasksController.java`: Controlador que gerencia as requisições aos recursos.
    - `application.yaml`: Arquivo de configuração da aplicação.

```yaml
spring:
  application:
    name: resource-server
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9001
server:
  port: 9090
```

### 3. Client Server

O Client Server é a aplicação que faz requisições ao Resource Server utilizando tokens obtidos a partir do Authorization Server.

- **Arquivos Principais**:
    - `ClientController.java`: Controlador que gerencia as requisições aos recursos protegidos.
    - `application.yaml`: Arquivo de configuração da aplicação.

## Configuração e Execução

### Pré-requisitos

- JDK 17
- Maven

### Passos para Execução

1. **Clonar o repositório**:
   ```bash
   git clone <URL do repositório>
   cd <diretório do repositório>
   ```

2. **Compilar e empacotar a aplicação**:
   ```bash
   mvn clean install
   ```

3. **Executar o Authorization Server**:
   ```bash
   cd authorization-server
   mvn spring-boot:run
   ```

4. **Executar o Resource Server**:
   ```bash
   cd ../resource-server
   mvn spring-boot:run
   ```

5. **Executar o Client Server**:
   ```bash
   cd ../client-server
   mvn spring-boot:run
   ```

### Configurações Importantes

#### Authorization Server (`authorization-server/src/main/resources/application.properties`)

Personalize as configurações conforme necessário:
```properties
server.port=9001
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.security.oauth2.client.provider.authserver.jwk-set-uri=http://localhost:9001/.well-known/jwks.json
```

#### Resource Server (`resource-server/src/main/resources/application.yaml`)

Certifique-se de que `issuer-uri` aponte para o Authorization Server correto:
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:9001
server:
  port: 9090
```

#### Client Server (`client-server/src/main/resources/application.yaml`)

Configure de acordo com o Resource Server:
```yaml
server:
  port: 8080

spring:
  security:
    oauth2:
      client:
        registration:
          authserver:
            client-id: <seu-client-id>
            client-secret: <seu-client-secret>
            scope: read,write
            authorization-grant-type: authorization_code
            redirect-uri: '{baseUrl}/login/oauth2/code/{registrationId}'
        provider:
          authserver:
            authorization-uri: http://localhost:9001/oauth/authorize
            token-uri: http://localhost:9001/oauth/token
            user-info-uri: http://localhost:9001/userinfo
            jwk-set-uri: http://localhost:9001/.well-known/jwks.json
        user:
          name-attribute: sub
```

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.
