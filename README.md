# Projeto LV Bank

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white )
![H2 Database](https://img.shields.io/badge/H2%20Database-000000?style=for-the-badge&logo=h2&logoColor=white )
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white )

## Sobre o Projeto

O `projeto LV Bank` é um projeto pessoal desenvolvido em Java que simula um sistema de conta bancária. Este projeto foi criado com o objetivo de aprofundar conhecimentos em Programação Orientada a Objetos (POO), persistência de dados com JDBC e manipulação de banco de dados SQL, utilizando o H2 Database.

Ao longo de sua evolução, o projeto demonstrou um amadurecimento significativo nas práticas de desenvolvimento, passando de uma implementação inicial focada em lógica em memória para uma solução robusta com persistência de dados e uma arquitetura bem definida.

## Funcionalidades

- **Criação e Gerenciamento de Contas:** Permite a criação de novas contas bancárias e o gerenciamento de informações de clientes.
- **Operações Bancárias:** Suporta operações essenciais como depósito, saque e transferências via Pix.
- **Persistência de Dados:** Utiliza JDBC para interagir com o H2 Database, garantindo que todas as transações e dados das contas sejam armazenados de forma segura.
- **Tratamento de Exceções:** Implementa exceções personalizadas para lidar com cenários como saldo insuficiente (`InsufficientBalanceException`) e valores negativos (`NegativeValueException`), aumentando a robustez da aplicação.
- **Precisão Financeira:** Todas as operações monetárias são realizadas com `BigDecimal` para garantir a exatidão nos cálculos.

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação principal.
- **JDBC (Java Database Connectivity)**: API para conexão e execução de queries em bancos de dados.
- **H2 Database**: Banco de dados relacional leve e embutido, ideal para desenvolvimento e testes.
- **Maven**: Ferramenta de automação de build e gerenciamento de dependências.

## Estrutura do Projeto

Abaixo, a organização das camadas da aplicação para garantir a separação de responsabilidades:

```text
src/main/java/br/com/lucasvicente/contabancaria/
├── aplication/     # Classe Main e inicialização do sistema
├── controller/     # Intermediação entre entrada de dados e serviços
├── dao/            # Data Access Objects (Implementação do JDBC)
├── database/       # Configurações de conexão e scripts SQL iniciais
├── entites/        # Modelos de dados (Account, Bank, Person, PixKey)
├── exceptions/     # Exceções de negócio (InsufficientBalance, etc.)
└── service/        # Regras de negócio e validações lógicas
```

## Como Executar o Projeto

Para executar este projeto, siga os passos abaixo:

### Pré-requisitos

- Java Development Kit (JDK) 17 ou superior instalado.
- Apache Maven instalado.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/lvpcdev/java-projeto-lv-bank.git
    cd java-projeto-lv-bank
    ```

2.  **Compile o projeto com Maven:**
    ```bash
    mvn clean install
    ```

3.  **Execute a aplicação:**
    ```bash
    mvn exec:java -Dexec.mainClass="br.com.lucasvicente.contabancaria.aplication.Main"
    ```

    A aplicação iniciará o servidor web do H2 Database e você poderá interagir com o sistema através do console.

---

**Autor:** Lucas Vicente

**GitHub:** [lvpcdev](https://github.com/lvpcdev )

---
