# Projeto LV Bank

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Gson](https://img.shields.io/badge/Gson-4285F4?style=for-the-badge&logo=google&logoColor=white)

## Sobre o Projeto

O `projeto LV Bank` é um projeto pessoal desenvolvido em Java que simula um sistema bancário, exposto como uma **API REST**. O projeto foi criado com o objetivo de aprofundar conhecimentos em Programação Orientada a Objetos (POO), persistência de dados com JDBC, modelagem de banco de dados relacional (PostgreSQL) e, mais recentemente, no funcionamento de APIs HTTP.

A API foi implementada **sem nenhum framework web** (sem Spring), utilizando apenas a classe `HttpServer` do próprio JDK. A decisão foi intencional: entender manualmente o que frameworks como o Spring Boot automatizam (roteamento, serialização, injeção de dependência) antes de adotá-los, para um aprendizado mais sólido.

Ao longo de sua evolução, o projeto passou por um amadurecimento significativo: de uma implementação inicial com lógica em memória e interação via console, para uma API REST completa, com persistência em PostgreSQL e arquitetura em camadas bem definida.

## Funcionalidades

- **Gerenciamento de Pessoas:** CRUD completo de clientes (`Person`).
- **Gerenciamento de Contas:** Criação, consulta, atualização e remoção de contas bancárias, sempre vinculadas a uma pessoa.
- **Operações Bancárias:** Depósito e saque, com validação de saldo insuficiente e valores negativos.
- **Chaves Pix:** CRUD completo de chaves Pix, vinculadas a uma conta, com consulta filtrada por conta.
- **Persistência de Dados:** JDBC puro para interagir com PostgreSQL, sem ORM.
- **Serialização JSON:** Gson para conversão entre objetos Java e JSON, tanto na entrada (parse do corpo da requisição) quanto na saída (resposta da API).
- **DTOs de Request/Response:** Separação entre o que a API recebe e o que ela expõe, evitando vazamento de dados sensíveis (como senhas) nas respostas.
- **Tratamento de Exceções:** Exceções de negócio personalizadas (`InsufficientBalanceException`, `NegativeValueException`), retornadas como respostas HTTP de erro adequadas.
- **Precisão Financeira:** Todas as operações monetárias utilizam `BigDecimal`.

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação principal, com uso de `record` para DTOs.
- **HttpServer (JDK)**: Servidor HTTP nativo do Java, usado para expor a API sem depender de frameworks web.
- **JDBC**: Conexão e execução de queries diretamente em SQL.
- **PostgreSQL**: Banco de dados relacional.
- **Gson**: Serialização e desserialização de JSON.
- **Maven**: Build e gerenciamento de dependências.

## Estrutura do Projeto

```text
src/main/java/br/com/lucasvicente/contabancaria/
├── aplication/       # ServidorBanco: inicialização do HttpServer e registro das rotas
├── controller/       # Camada HTTP (HttpHandler): roteamento, leitura/escrita de requisições
├── service/          # Regras de negócio e validações
├── dao/              # Acesso a dados via JDBC (SQL puro)
├── dto/
│   ├── requests/      # DTOs de entrada (o que a API recebe)
│   └── responses/      # DTOs de saída (o que a API expõe)
├── entites/          # Modelos de domínio (Account, Person, PixKey)
├── exceptions/        # Exceções de negócio personalizadas
└── database/          # Configuração de conexão com o PostgreSQL
```

A arquitetura segue o fluxo: **Controller (HTTP) → Service (regra de negócio) → DAO (dados)**, com cada camada conhecendo apenas a camada imediatamente abaixo dela.

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/people` | Lista todas as pessoas |
| `GET` | `/people/{id}` | Busca uma pessoa por ID |
| `POST` | `/people` | Cria uma nova pessoa |
| `PUT` | `/people/{id}` | Atualiza uma pessoa |
| `DELETE` | `/people/{id}` | Remove uma pessoa |
| `GET` | `/accounts` | Lista todas as contas |
| `GET` | `/accounts/{id}` | Busca uma conta por ID |
| `POST` | `/accounts` | Cria uma nova conta |
| `PUT` | `/accounts/{id}` | Atualiza uma conta |
| `DELETE` | `/accounts/{id}` | Remove uma conta |
| `POST` | `/accounts/{id}/deposit` | Realiza um depósito |
| `POST` | `/accounts/{id}/withdraw` | Realiza um saque |
| `GET` | `/pixkeys` | Lista todas as chaves Pix |
| `GET` | `/pixkeys?accountId={id}` | Lista as chaves Pix de uma conta |
| `GET` | `/pixkeys/{id}` | Busca uma chave Pix por ID |
| `POST` | `/pixkeys` | Cria uma nova chave Pix |
| `PUT` | `/pixkeys/{id}` | Atualiza uma chave Pix |
| `DELETE` | `/pixkeys/{id}` | Remove uma chave Pix |

## Como Executar o Projeto

### Pré-requisitos

- Java Development Kit (JDK) 21 ou superior.
- Apache Maven.
- PostgreSQL em execução localmente (ou ajustar as credenciais de conexão em `database/DatabaseConnection.java`).

### Passos

1. **Clone o repositório:**
    ```bash
    git clone https://github.com/lvpcdev/java-projeto-lv-bank.git
    cd java-projeto-lv-bank
    ```

2. **Configure o banco de dados:**
   Crie um banco PostgreSQL e ajuste a string de conexão, usuário e senha em `DatabaseConnection.java`.

3. **Compile o projeto com Maven:**
    ```bash
    mvn clean install
    ```

4. **Execute o servidor:**
    ```bash
    mvn exec:java -Dexec.mainClass="br.com.lucasvicente.contabancaria.aplication.ServidorBanco"
    ```
   O servidor sobe na porta `8080`. A partir daí, os endpoints listados acima podem ser testados via Postman, Insomnia ou qualquer cliente HTTP.

## Próximos Passos

- Migração da API para Spring Boot, aplicando o entendimento consolidado sobre o que o framework automatiza (roteamento, injeção de dependência, serialização).
- Implementação de um frontend simples para consumo da API.

---

**Autor:** Lucas Vicente
**GitHub:** [lvpcdev](https://github.com/lvpcdev)
