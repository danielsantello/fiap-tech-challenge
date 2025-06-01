# Tech Challenge - Pós-Tech - FIAP

Curso de Pós-Graduação em Arquitetura e Desenvolvimento em JAVA - Turma 8ADJT - Fase I

## Repositório

https://github.com/danielsantello/fiap-tech-challenge

## Propósito do projeto

Desenvolver um backend completo e robusto utilizando o framework Spring Boot, com foco no gerenciamento de usuários, incluindo operações de criação, atualização, exclusão e validação de login. O projeto será configurado para rodar em um ambiente Docker, utilizando Docker Compose, o que permitirá a orquestração dos serviços e a integração com um banco de dados relacional, como PostgreSQL, MySQL ou H2. A configuração com Docker Compose garantirá que a aplicação seja facilmente replicável e escalável, permitindo a implantação em diversos ambientes de forma consistente e eficiente. Além disso, o projeto será desenvolvido seguindo as melhores práticas de arquitetura e segurança, de modo que o sistema seja não apenas funcional, mas também seguro, escalável e de fácil manutenção

## Tecnologias utilizadas

* Spring Boot
* MySQL
* Docker
* Docker Compose
* Maven

## Instalação do projeto

Este projeto está pronto para ser executado em um ambiente Docker. Por este motivo, será necessária apenas a instalação do Docker, não sendo necessária a instalação manual do projeto. Também não será necessária a instalação manual do banco de dados (MySQL).

Caso não tenha o Docker instalado, siga as instruções para seu sistema operacional na [documentação oficial do Docker](https://docs.docker.com/get-docker/).

Para executar o projeto, siga os passos abaixo:

Clone o código desse repositório em seu computador:

``` bash
git clone https://github.com/danielsantello/fiap-tech-challenge.git
```

Entre no diretório local onde o repositório foi clonado:

``` bash
cd fiap-tech-challenge
```

Suba as aplicações com o comando:

``` bash
docker-compose up --build
```

O comando anterior subirá o servidor local, expondo a porta 8080 em `localhost`. Além do container da `api` também subirá o serviço `db` com o banco de dados de desenvolvimento.

**IMPORTANTE:** Esta API está programada para ser acessada a partir de `http://localhost:8080` e o banco de dados utiliza a porta `3306`. Certifique-se de que não existam outros recursos ocupando as portas `8080` e `3306` antes de subir o projeto.

Para derrubar o serviço, execute o comando:

``` bash
docker compose down
```

### Endpoints

Esta API fornece documentação no padrão OpenAPI.
Os endpoints disponíveis, suas descrições e dados necessários para requisição podem ser consultados e testados em `http://localhost:8080/swagger-ui/index.html`.
