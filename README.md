# Cadastro de Carros API

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Maven](https://img.shields.io/badge/Maven-4.0.0-orange)
![Database](https://img.shields.io/badge/Database-H2_In_Memory-red)

## 📝 Descrição

Este projeto é uma API REST simples para o cadastro e gerenciamento de carros, desenvolvida com Spring Boot. Ele demonstra conceitos de CRUD (Create, Read, Update, Delete) utilizando Spring Web, Spring Data JPA (com Hibernate como provedor) e um banco de dados H2 em memória.

## ✨ Funcionalidades

* Criar novos registros de carros (marca, modelo, ano).
* Listar todos os carros cadastrados.
* Buscar um carro específico pelo seu ID.
* Atualizar os dados de um carro existente.
* Deletar um carro pelo seu ID.
* Buscar carros por marca.
* Console do banco de dados H2 acessível via navegador para consulta e inspeção.

## 🚀 Tecnologias Utilizadas

* **Java 17+:** Linguagem de programação principal.
* **Spring Boot 3.x:** Framework para criação rápida de aplicações Spring.
* **Spring Web:** Para construção de APIs RESTful.
* **Spring Data JPA:** Para facilitar o acesso a dados e persistência.
* **Hibernate:** Implementação JPA utilizada por baixo dos panos pelo Spring Data JPA.
* **H2 Database:** Banco de dados relacional em memória, ideal para desenvolvimento e testes.
* **Maven:** Ferramenta para gerenciamento de dependências e build do projeto.

## ✅ Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

* **JDK 17 ou superior:** [OpenJDK](https://openjdk.java.net/) ou outra distribuição.
* **Maven 3.6+:** [Apache Maven](https://maven.apache.org/download.cgi) (Opcional, pois o projeto inclui o Maven Wrapper).
* **Git:** Para clonar o repositório. [Git SCM](https://git-scm.com/).
* Uma ferramenta para testar APIs (Opcional, mas recomendado): Postman, Insomnia, ou `curl`.

## ⚙️ Como Configurar e Executar

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO_GITHUB>
    cd cadastro-carros
    ```
    *(Substitua `<URL_DO_SEU_REPOSITORIO_GITHUB>` pela URL real do seu projeto no GitHub)*

2.  **Execute a aplicação usando o Maven Wrapper:**

    * No Linux ou macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    * No Windows (Prompt de Comando ou PowerShell):
        ```bash
        ./mvnw.cmd spring-boot:run
        ```

3.  **Alternativa (usando IDE):** Importe o projeto como um projeto Maven em sua IDE (IntelliJ IDEA, Eclipse, VS Code) e execute a classe principal `br.com.cadastrocarros.CadastroCarrosApplication`.

A aplicação estará rodando em `http://localhost:8087` (a porta foi alterada de 8080 para 8087 no `application.properties`).

## 🗄️ Acessando o Banco de Dados H2

Durante a execução da aplicação, você pode acessar o console web do banco de dados H2 para visualizar as tabelas, dados e executar consultas SQL diretamente.

1.  Abra seu navegador e acesse: `http://localhost:8087/h2-console`
2.  Na tela de login, utilize as seguintes informações (conforme configurado em `application.properties`):
    * **Driver Class:** `org.h2.Driver`
    * **JDBC URL:** `jdbc:h2:mem:carrodb`
    * **User Name:** `sa`
    * **Password:** (deixe em branco)
3.  Clique em "Connect".

## 📡 Endpoints da API

A API base está disponível em `http://localhost:8087/api/carros`.

---

### 1. Criar Carro

* **Método:** `POST`
* **URL:** `/api/carros`
* **Corpo da Requisição (JSON):**
    ```json
    {
        "marca": "Marca Exemplo",
        "modelo": "Modelo Exemplo",
        "ano": 2024
    }
    ```
* **Resposta (Sucesso):** `201 Created` com o objeto do carro criado no corpo.
* **Resposta (Erro):** `500 Internal Server Error` em caso de falha.

---

### 2. Listar Todos os Carros

* **Método:** `GET`
* **URL:** `/api/carros`
* **Resposta (Sucesso):** `200 OK` com uma lista JSON de carros. Retorna `204 No Content` se não houver carros.

---

### 3. Buscar Carro por ID

* **Método:** `GET`
* **URL:** `/api/carros/{id}`
    * Exemplo: `/api/carros/1`
* **Parâmetro de URL:** `{id}` - ID do carro a ser buscado.
* **Resposta (Sucesso):** `200 OK` com o objeto do carro encontrado.
* **Resposta (Erro):** `404 Not Found` se o ID não existir.

---

### 4. Atualizar Carro por ID

* **Método:** `PUT`
* **URL:** `/api/carros/{id}`
    * Exemplo: `/api/carros/1`
* **Parâmetro de URL:** `{id}` - ID do carro a ser atualizado.
* **Corpo da Requisição (JSON):** Objeto JSON com os *novos* dados do carro.
    ```json
    {
        "marca": "Marca Atualizada",
        "modelo": "Modelo Atualizado",
        "ano": 2025
    }
    ```
* **Resposta (Sucesso):** `200 OK` com o objeto do carro atualizado.
* **Resposta (Erro):** `404 Not Found` se o ID não existir.

---

### 5. Deletar Carro por ID

* **Método:** `DELETE`
* **URL:** `/api/carros/{id}`
    * Exemplo: `/api/carros/1`
* **Parâmetro de URL:** `{id}` - ID do carro a ser deletado.
* **Resposta (Sucesso):** `204 No Content` (sem corpo na resposta).
* **Resposta (Erro):** `404 Not Found` se o ID não existir, `500 Internal Server Error` em caso de outra falha.

---

### 6. Buscar Carros por Marca

* **Método:** `GET`
* **URL:** `/api/carros/marca/{marca}`
    * Exemplo: `/api/carros/marca/Toyota`
* **Parâmetro de URL:** `{marca}` - Nome da marca a ser buscada.
* **Resposta (Sucesso):** `200 OK` com uma lista JSON de carros da marca especificada. Retorna `204 No Content` se nenhum carro for encontrado para a marca.

---

## 🔧 Configuração

As principais configurações da aplicação podem ser encontradas no arquivo `src/main/resources/application.properties`, incluindo:

* `server.port`: Porta em que a aplicação roda (definida como 8087).
* `spring.datasource.*`: Configurações de conexão com o banco de dados H2.
* `spring.h2.console.*`: Configurações para habilitar e definir o caminho do console H2.
* `spring.jpa.*`: Configurações do Spring Data JPA e Hibernate (dialeto, DDL auto, mostrar SQL).

## 🏗️ Estrutura do Projeto (Simplificada)

```text
cadastro-carros/
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/cadastrocarros/
│   │   │       ├── CadastroCarrosApplication.java  # Classe principal
│   │   │       ├── model/                          # Entidades JPA (Ex: Carro.java)
│   │   │       ├── repository/                     # Interfaces Spring Data JPA (Ex: CarroRepository.java)
│   │   │       └── controller/                     # Controladores REST (Ex: CarroController.java)
│   │   └── resources/
│   │       ├── application.properties        # Configurações da aplicação
│   │       ├── static/                       # Arquivos estáticos (CSS, JS, Imagens)
│   │       └── templates/                    # Templates HTML (se usar Thymeleaf/outros)
│   └── test/
│       └── java/
│           └── br/com/cadastrocarros/
│               └── CadastroCarrosApplicationTests.java # Testes
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml                                   # Arquivo de configuração do Maven

