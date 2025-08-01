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
**Na raiz do projeto execute**

```bash
docker-compose up -d
```

**Acesse a documentação Swagger**

```bash
http://localhost:8082/swagger-ui/index.html
```

### Como utilizar a autenticação via Swagger

1. **Criar um novo usuário**

   Use o endpoint `POST /api/v1/usuarios` e envie o seguinte JSON:

   ```json
   {
     "nome": "João Silva",
     "email": "joao@email.com",
     "senha": "Zji4$tPwBSbcvdK0?QZghVGH*KR!QTh77"
   }
   ```

2. **A resposta será um objeto JSON contendo os dados do usuário criado:**

   ```json
   {
     "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
     "nome": "João Silva",
     "email": "joao@email.com",
     "dataHoraCriacao": "2025-08-01T03:47:30.316Z"
   }
   ```

3. **Autenticar e obter o token**

   Acesse o endpoint `POST /api/v1/usuarios/autenticar` e envie:

   ```json
   {
     "email": "joao@email.com",
     "senha": "Zji4$tPwBSbcvdK0?QZghVGH*KR!QTh77"
   }
   ```
   
   A resposta será um json contendo o token:
   
   ```json
   {
  	 "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
     "nome": "João Silva",
     "email": "joao@email.com",
     "dataHoraAcesso": "2025-08-01T03:57:45.539Z",
     "dataHoraExpiracao": "2025-08-01T03:57:45.539Z",
     "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiNDRkYjRhOS01NDc0LTQ1MDctOGQwNS0wNzJiN2JlOTAzMTAiLCJpYXQiOjE3NTQwMDE2MzEsImV4cCI6MTc1NDAwNTIzMX0.DDiCx2WeQOjcyZ82x5gvDOa53rTR7QGBTJIdCbNprgQ"
	}
    ```