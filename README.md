# Desafio Fullstack – Instalações Escolares

## Sobre o Projeto

Aplicação full-stack que permite realizar upload e visualização de arquivos CSV contendo informações das dependências das escolas do estado de São Paulo.
O backend foi desenvolvido em Java com Spring Boot e o frontend em React. A aplicação roda de forma integrada com PostgreSQL via Docker Compose.


## Principais Tecnologias

- React
- Java Spring Boot
- Spring Security & JWT
- PostgreSQL
- Docker


## Features Implementadas

- Registro e login de usuários com autenticação via JWT. 
- Logout através da barra principal. 
- Upload de arquivos CSV das dependências escolares de São Paulo (dados oficiais). 
- Tabela interativa com CRUD completo (criação, visualização, atualização e remoção de dados).
- Testes automatizados.

**IMPORTANTE:** 

- Tratamento de inconsistências do dataset: 
  - `CODESC` utilizado como chave única, evitando inserções duplicadas. 
  - Remoção de caracteres invisíveis (ex.: `\uFEFF`). 
  - Resolução de headers duplicados: o segundo `CODESC` se torna `CODESC1`, e assim por diante. 


## Pré-Requisitos

Para rodar esse projeto, tenha certeza de que possui o **Docker** instalado. Você pode encontrar as instruções de instalação aqui:

- [Docker Documentation](https://docs.docker.com/)


## Como Rodar o Projeto

1. **Clone o repositório:**

```bash
   git clone git@github.com:tiagooc93/houer-challenge.git
   cd houer-challenge
```

2. **Configure as variaveis de ambiente:**

É preciso criar um arquivo .env com as configurações das variáveis. Para simplificar esse processo, coloquei um .env.example no repositório, então é necessário somente copiá-lo para o arquivo .env, utilizando do seguinte comando:

```bash
cp .env.example .env
```

3. **Iniciar a aplicação utilizando o Docker Compose:**


Então utilize o docker para rodar o projeto inteiro com base no arquivo de configuração docker-compose.

```bash
sudo docker-compose up
```

## Uso

Por padrão, os serviços estarão acessíveis nos seguintes endereços:

    Frontend: http://localhost:3000

    Backend: http://localhost:8080
    
Apenas abra o browser e visite o endereço:

```bash
localhost:3000/login
``````

Caso não queira se registrar, utilize o usuário de testes ja disponível para logar:

```bash
usuário: test@test.com
senha: 123
``````
