# API de Gerenciamento de Produtos

API RESTful desenvolvida com Spring Boot para controle, cadastro e manutenção de um catálogo de produtos. O projeto exemplifica boas práticas de arquitetura REST, separação clara de responsabilidades em camadas, persistência com JPA/Hibernate, validação de dados com Bean Validation, documentação interativa com Swagger/OpenAPI e tratamento global de exceções.

---

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.x / 4.x
  - Spring Web
  - Spring Data JPA
  - Validation (Jakarta Validation / Hibernate Validator)
  - Spring Boot DevTools
- **Banco de Dados:** H2 Database (Banco em memória)
- **Documentação:** Springdoc OpenAPI / Swagger UI (v2.8.5)
- **Utilitários:** Lombok (Redução de boilerplate)
- **Gerenciador de Dependências:** Maven

---

## 🏗️ Arquitetura e Organização de Pacotes

O projeto adota uma estrutura organizada por responsabilidades técnicas (`br.com.aula.api_produtos`):

```text
src/main/java/br/com/aula/api_produtos/
├── config/        # Configurações globais (OpenAPI, Carga inicial de dados)
├── controller/    # Camada HTTP - Endpoints REST
├── dto/           # Contratos de entrada e saída (Java Records)
├── entity/        # Modelo de persistência JPA (Tabela tb_produto)
├── exception/     # Tratamento global de erros e exceções customizadas
├── mapper/        # Conversão entre Entities e DTOs
├── repository/    # Abstração de acesso aos dados (Spring Data JPA)
└── service/       # Regras de negócio e orquestração de casos de uso
```

### Fluxo de Dados

```text
HTTP/JSON ──> Request DTO ──> Controller ──> Service ──> Mapper ──> Entity ──> Repository ──> Database (H2)
                                  │             │
                                  ▼             ▼
                            Validation     Response DTO ──> Controller ──> HTTP/JSON
```

---

## 📌 Endpoints da API

Caminho base: `/api/v1/produtos`

| Método | Endpoint | Descrição | Corpo da Requisição (Request) | Resposta Sucesso |
|---|---|---|---|---|
| `POST` | `/api/v1/produtos` | Cadastra um novo produto | `ProdutoCreateRequest` (JSON) | `201 Created` + `ProdutoResponse` |
| `GET` | `/api/v1/produtos` | Lista todos os produtos | N/A | `200 OK` + List<`ProdutoResponse`> |
| `GET` | `/api/v1/produtos/{id}` | Busca produto por ID | N/A (ID no Path) | `200 OK` + `ProdutoResponse` |
| `GET` | `/api/v1/produtos?nome={termo}` | Filtra produtos por nome | N/A (Query Param `nome`) | `200 OK` + List<`ProdutoResponse`> |
| `PUT` | `/api/v1/produtos/{id}` | Atualiza completamente um produto | `ProdutoUpdateRequest` (JSON) | `200 OK` + `ProdutoResponse` |
| `DELETE` | `/api/v1/produtos/{id}` | Remove um produto do catálogo | N/A (ID no Path) | `204 No Content` |

---

## 📄 Modelos de Dados (DTOs & Entity)

### Entidade (`Produto`)
- `id`: Long (Chave Primária, Auto-incremento)
- `nome`: String (Obrigatório, máx 100 caracteres)
- `preco`: BigDecimal (Obrigatório)
- `ativo`: Boolean (Obrigatório, default `true` no cadastro)

### Request DTOs (Records)
- **`ProdutoCreateRequest`**: `nome` (Not Blank, 3-100 chars), `preco` (Not Null, Positive).
- **`ProdutoUpdateRequest`**: `nome` (Not Blank, 3-100 chars), `preco` (Not Null, Positive), `ativo` (Not Null).

### Response DTO (Record)
- **`ProdutoResponse`**: `id`, `nome`, `preco`, `ativo`.

---

## ⚠️ Tratamento de Exceções

A aplicação possui um `@RestControllerAdvice` (`GlobalExceptionHandler`) que padroniza todas as respostas de erro HTTP através do DTO `ErroResponse`:

- **`404 Not Found`**: Lançado via `ProdutoNaoEncontradoException` quando o ID informado não existe.
- **`400 Bad Request`**: Lançado em violações de regras de negócio (ex: produto com nome duplicado) ou falhas na Bean Validation (`MethodArgumentNotValidException`).

---

## ⚙️ Configurações da Aplicação

Definidas em `application.properties`:

- **Porta do Servidor:** `8181`
- **Banco de Dados H2 Console:** `/h2-console` (JDBC URL: `jdbc:h2:mem:produtosdb`)
- **Documentação OpenAPI:** `/api-docs`
- **Interface Swagger UI:** `/swagger-ui.html`

---

## 🧪 Carga Inicial de Dados

Na inicialização da aplicação, a classe `CargaDadosInicial` popula o banco de dados com dados de teste caso a tabela esteja vazia (ex: Notebook Dell Inspiron, Mouse Gamer, Teclado Mecânico, Monitor 29", Fone de ouvido Bluetooth).

---

## 🛠️ Como Executar o Projeto

1. Certifique-se de ter o **Java 21** e o **Maven** instalados.
2. Clone este repositório.
3. Execute o projeto via Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse a documentação interativa no navegador:
   - **Swagger UI:** [http://localhost:8181/swagger-ui.html](http://localhost:8181/swagger-ui.html)
   - **Console H2:** [http://localhost:8181/h2-console](http://localhost:8181/h2-console)
