## Locadora de filmes

- Para utilizar a aplicação inicialmente crie o banco de dados
> CREATE DATABASE movie_store_db;

- Após criar o banco, rode o script que se encontra na pasta ~/local-database/scripts/**br_video_store_vFINAL.sql**
> Será criado 3 tabelas: movies, users e rent_movie;



## Utilizando a aplicação:

### Pesquisar filme pelo título
> Enviar **title** como parâmetro na requisição
```
GET /v1/movies/search?title=name
```
> Resposta: um JSON com as infos do filme ou vazio caso não encontre nenhum filme
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

### Listar todos os filmes disponíveis
> Fazer uma request sem parâmetros
```
GET /v1/movies
```
> Resposta: uma lista JSON com as infos de todos filmes disponíveis
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

### Criar usuário
> Enviar uma request param pelo body com email, nome e senha. Ex:
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
    "id": 4,
    "email": "email@gmail.com",
    "name": "Name",
    "login": true
}
````
> Caso o email já seja cadastrado retorna o body vazio e status code 409;

### Fazer login
```
PUT /v1/user/login
```

### Fazer logout
```
PUT /v1/user/logout
```

### Locar um filme
```

```

### Devolver um filme
```

```