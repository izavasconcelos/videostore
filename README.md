# Locadora de filmes

## Tecnologias utilizadas

* [Java8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
* [PostgreSQL](https://www.postgresql.org/download/)
* [Spring Boot](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.4.3/reference/html/)
* [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)
* [Lombok](https://projectlombok.org/api/lombok/package-summary.html)

## Testes unitários

* [JUnit](https://junit.org/junit5/docs/current/user-guide/)
* [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

## Rodando a aplicação:

- Para utilizar a aplicação inicialmente crie o banco de dados
> CREATE DATABASE movie_store_db;

- Após criar o banco, rode o script que se encontra na pasta ~/local-database/scripts/**br_video_store_vFINAL.sql**
> Serão criadas 3 tabelas: movies, users e rent_movie;

- **movies** (id, title, director, available, unavailable):
  Responsável por guardar as informações dos filmes da locadora
  

- **users** (id,	email, name, password, logged):
  Responsável por guardar as informações dos usuários da locadora
  

- **rent_movie** (email,	id_movie):
  Responsável por guardar as informações dos filmes locados
  
  
Após a criação completa do BD é necessário configurar o *application.properties* com os dados do seu banco
```
PATH: ~/src/main/resources/application.properties

spring.database.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/movie_store_db
spring.datasource.username=seu_user_name
spring.datasource.password=sua_senha
```

**Pronto, basta fazer o build do projeto e rodar a aplicação.**


## Utilizando a aplicação:

É possível fazer 7 requisições para a aplicação:
> - Pesquisar um filme pelo título
>>GET /v1/movies/search?title=Filme%20Teste
> ###
> - Listar todos os filmes disponíveis para locação
>>GET /v1/movies
> ###
> - Criar um novo usuário
>>POST /v1/user
> ###
> - Fazer login de um usuário cadastrado
>>PUT /v1/user/login
> ###
> - Fazer logout de um usuário cadastrado
>>PUT /v1/user/logout
> ###
> - Alugar um filme disponível
>>POST /v1/rent
> ###
> - Devolver um filme alugado
>>DEL /v1/rent


vv  **Abaixo mais detalhes de como utilizar cada requisição.** vv
###
- ### Pesquisar filme pelo título (retorna também os indisponíveis)
> Enviar **title** como parâmetro na requisição. Ex:
```
GET /v1/movies/search?title=Filme%20Teste
```
> Resposta será um JSON com as infos do filme ou vazio caso não encontre nenhum filme.

```
[
    {
        "id": 7,
        "title": "Filme Teste",
        "director": "Duff",
        "totalAvailable": 1
    }
]
```
###
- ### Listar todos os filmes disponíveis
> Fazer uma request sem parâmetros
```
GET /v1/movies
```
> Resposta será uma lista JSON com as infos de todos filmes disponíveis:
```
[
    {
        "id": 1,
        "title": "Filme 7",
        "director": "Tarja Turunen",
        "totalAvailable": 2
    },
    {
        "id": 2,
        "title": "Filme 8",
        "director": "Alissa",
        "totalAvailable": 1
    },
    ...
]
```
*Caso não exista filmes disponíveis retornará uma lista vazia.*

###
- ### Criando um usuário
> Enviar uma request param pelo body com email, name e password. Ex:
```
POST /v1/user
```
```
{
    "email":"email@gmail.com",
    "name":"Name",
    "password":"123456"
}
```
> Resposta será um JSON com as infos do usuário e o mesmo já ficará logado podendo fazer locações
````
{
    "email": "email@gmail.com",
    "logged": true
}
````

### Importante:
- **email** não pode ser nulo e nem ter mais que 100 caracteres.
- **name** não pode ser nulo e nem ter mais que 150 caracteres.
- **password** não pode ser nulo e nem ter menos que 6 ou mais que 20 caracteres.

*Caso uma dessas validações não seja respeitada retorna como resposta status code 400 (Bad Request).*

*Caso o usuário já seja cadastrado retorna como resposta status code 400 (Bad Request).*
###
- ### Fazendo login
> Enviar uma request param pelo body com email e password. Ex:
```
PUT /v1/user/login
```
```
{
    "email":"vasconcelos.izas@gmail.com",
    "password":"123456"
}
```
> Resposta será um JSON com as infos do usuário mostrando login ativo e o status code 200 OK.
```
{
"email": "iza@gmail.com",
"logged": true
}
```

### Importante:
- **email** não pode ser nulo e nem ter mais que 100 caracteres.
- **password** não pode ser nulo e nem ter menos que 6 ou mais que 20 caracteres.

*Caso uma das validações acima não seja respeitada retorna como resposta status code 400 (Bad Request).*

*Caso o usuário não seja cadastrado retorna como resposta status code 404 (Not Found).*
###
- ### Fazer logout
> Enviar uma request param pelo body com email para fazer logout. Ex:
```
PUT /v1/user/logout
```
```
{
    "email":"iza@gmail.com"
}
```
> Resposta será um JSON com as infos do usuário mostrando login inativo e o status code 200 OK.
```
{
    "email": "iza@gmail.com",
    "logged": false
}
```

### Importante:
- **email** não pode ser nulo e nem ter mais que 100 caracteres.

*Caso a validação acima não seja respeitada retorna como resposta status code 400 (Bad Request).*

*Caso o usuário não seja cadastrado retorna como resposta status code 404 (Not Found).*

###
- ### Locando um filme
> Enviar uma request param pelo body com email e id do filme (movieId) para ser locado. Ex:
```
POST /v1/rent
```
```
{
    "email":"iza@gmail.com",
    "movieId":3
}
```
> Resposta será um JSON com as infos da request e o status code 200 OK.
```
{
    "email":"iza@gmail.com",
    "movieId":3
}
```


### Importante:
- **email** não pode ser nulo e nem ter mais que 100 caracteres.
- **movieId** não pode ser nulo.

*Caso uma das validações acima não seja respeitada retorna como resposta status code 400 (Bad Request).*

*Caso o usuário não seja cadastrado retorna como resposta status code 400 (Bad Request).*

*Caso o usuário seja cadastrado mas seja uma locação duplicada retorna como resposta status code 400 (Bad Request).*

*Caso o usuário não esteja logado retorna como resposta status code 400 (Bad Request).*

*Caso o filme não exista ou não esteja disponível retorna como resposta status code 400 (Bad Request).*

###
- ### Devolvendo um filme
> Enviar uma request param pelo body com email e id do filme (movieId) para ser devolvido. Ex:
```
DEL /v1/rent
```
```
{
    "email":"iza@gmail.com",
    "movieId":3
}
```
> Resposta será um JSON com as infos da request e o status code 200 OK.
```
{
    "email":"iza@gmail.com",
    "movieId":3
}
```

### Importante:
- **email** não pode ser nulo e nem ter mais que 100 caracteres.
- **movieId** não pode ser nulo.

*Caso uma das validações acima não seja respeitada retorna como resposta status code 400 (Bad Request).*

*Caso o usuário não seja cadastrado retorna como resposta status code 400 (Bad Request).*

*Caso o usuário seja cadastrado mas não exista locação do filme retorna como resposta status code 400 (Bad Request).*

*Caso o usuário não esteja logado retorna como resposta status code 400 (Bad Request).*

*Caso o filme não exista retorna como resposta status code 400 (Bad Request).*

###
### POSTMAN
- É possivel testar a aplicação com uma collection do postman disponível no projeto:
  ```
  ./videoStore.postman_collection.json
  ```