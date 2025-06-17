# API de Gerenciamento de Usuários

Projeto simples de uma API REST construída com **Spring Boot** e **Java 21**, para gerenciamento de usuários.

## Tecnologias e Ferramentas

- Java 21
- Spring Boot
- Maven
- Spring Data JPA
- PostgreSQL (Docker)
- Jakarta Validation
- Swagger (OpenAPI)

## Estrutura do Projeto

O projeto está organizado em camadas para facilitar a manutenção e evolução:

- **controllers**  
Contém o `UsuariosController`, responsável pelos endpoints de criação e autenticação de usuários.

- **components**  
Inclui o `CryptoComponent`, responsável pela criptografia de senhas.

- **dtos**  
Define os objetos de transferência de dados:  
`CriarUsuarioRequestDTO`, `CriarUsuarioResponseDTO`, `AutenticarUsuarioRequestDTO`, `AutenticarUsuarioResponseDTO`.  
As classes de request utilizam validações com Jakarta Validation.

- **entities**  
Contém a entidade `Usuario`, que representa o domínio.

- **exceptions**  
Exceções personalizadas:  
`AcessoNegadoException`, `EmailJaCadastradoException`.

- **handlers**  
Implementa o `GlobalExceptionHandler`, responsável pelo tratamento global de exceções.

- **repositories**  
Contém o `UsuarioRepository`, interface de acesso aos dados.

- **services**  
Inclui o `UsuarioService`, onde fica a lógica de negócios.

## Banco de Dados

O projeto utiliza um banco **PostgreSQL** executado em um container Docker.

## Documentação da API

A documentação dos endpoints está disponível via **Swagger**, acessível após subir a aplicação.


## Como executar o projeto

**Clone o repositório**

```bash
git clone https://https://github.com/a-devrepo/projetoUsuariosApi
cd projetoUsuariosApi
```
**Suba o banco de dados**

```bash
docker-compose up -d
```
**Execute a aplicação**

```bash
./mvnw spring-boot:run
```
**Acesse a documentação Swagger**

```bash
http://localhost:8082/swagger-ui/index.html
```