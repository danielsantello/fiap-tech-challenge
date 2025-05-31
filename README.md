# Tech Challenge - Pós-Tech - FIAP

Projeto desenvolvido durante a fase I do curso de pós-graduação em Arquitetura e Desenvolvimento em JAVA - 8ADJT.

Membros do grupo 39:
Daniel Santello - RM 348666

## Repositórios

API principal:
https://github.com/danielsantello/fiap-tech-challenge

## Propósito do projeto

Fornecer um sistema para gerenciamento de pedidos para uma empresa do ramo de serviços de alimentação.

## Stack utilizada

* MySQL
* Docker
* Kubernetes

## Instalação do projeto

Este projeto está pronto para ser executado em um ambiente Docker. Por este motivo, será necessária apenas a instalação do Docker e/ou Kubernetes, não sendo necessária a instalação manual do projeto. Também não será necessária a instalação manual do banco de dados (MySQL).

Caso não tenha o Docker instalado, siga as instruções para seu sistema operacional na [documentação oficial do Docker](https://docs.docker.com/get-docker/).

Para executar em ambiente de desenvolvimento:

* Faça o `fork` e `clone` este repositório em seu computador;
* Entre no diretório local onde o repositório foi clonado;

### Executar em ambiente Kubernetes

Os arquivos para o Kubernetes se encontram no diretório ```k8s/```:

1. Crie o secrets como o exemplo abaixo ou use um de terceiros com as envs listadas:

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: fiap-tech-secrets
type: Opaque
data: # value = Base64
  db_username: dXNlcl90ZWNo
  db_password: dGVzdHRlc3Q=
  db_root_password: YWRtaW4xMjM=
  db_name: ZmlhcC1zb2F0LXByb2plY3RfZGI=
  jwt_secret: dTZCWTh3NHMzYXlHNjJvRzA1TVYxSE96eTllYm9UYVdoUWpIQ0ZpWmhjMjBFYlIwOGdzWlZPdUdQUGVVVUVJMg==
  user_pool_id: dXMtZWFzdC0xX1FtWkNMUElnWg==
  admin_pool_id: dXMtZWFzdC0xX252bnk0V1llRQ==
  pool_client_client_id: MTkydjlnZmVvYmxqM3NpczNmajA0a2o0ZDE=
  pool_admin_client_id: cHRmOHEwYW9qaDFkNnBicG1vOXJhaGc1dg==
```

2. Execute o comando `kubectl apply -f <./caminho/do/arquivo/secret.yaml>` no diretório raiz do projeto para gerar os secrets;

3. Execute os comandos abaixo para subir as ferramentas do ambiente Kubernetes:

Banco de dados:
```
kubectl apply -f k8s/db/db.pvc.yaml
kubectl apply -f k8s/db/db.deployment.yaml
kubectl apply -f k8s/db/db.svc.yaml
```

API:
```
kubectl apply -f k8s/api/api.deployment.yaml
kubectl apply -f k8s/api/api.svc.yaml
```

A API estará pronta para receber requisições a partir da URL base http://localhost:8080/.
**OBS**: Caso a URL base não esteja disponível em `localhost`, execute o comando `minikube ip` no terminal e utilize o IP disponibilizado no lugar de `localhost`. Exemplo: `192.168.49.2:8080/api/produto`.


### Docker Compose

Utilize o comando `docker compose up` para "construir" (*build*) e subir o servidor local, expondo a porta 8080 em `localhost`. Além do container da `api` também subirá o serviço `db` com o banco de dados de desenvolvimento.

**IMPORTANTE:** Esta API está programada para ser acessada a partir de `http://localhost:8080` e o banco de dados utiliza a porta `3306`. Certifique-se de que não existam outros recursos ocupando as portas `8080` e `3306` antes de subir o projeto.

Para derrubar o serviço, execute o comando `docker compose down`.

### Endpoints

Esta API fornece documentação no padrão OpenAPI.
Os endpoints disponíveis, suas descrições e dados necessários para requisição podem ser consultados e testados em ```/api-docs```.

### 1. Cadastrar Usuários

1.1 - Cadastro do usuário:
```json
{
  "nome": "produto 1",
  "preco": 0.1,
  "descricao": "demo 1",
  "categoriaId": "1c941831-c8cb-43a3-8d3f-2959a6fb7241",
  "imagens": [
    {
      "url": "demo.png"
    }
  ]
}
```

### 2. Pedido

3.1 Crie um pedido vazio usando o ```/pedido/iniciar-pedido``` passando o id do usuário;
3.2 Adicione um produto ao pedido usando o ```/pedido/{id}/adicionar-item```;
  A lista de produtos pode ser consultada via GET ```/produto```
3.3 Finalize o pedido em ```/pedido/realizar-pedido/{id-pedido}``` passando o método de pagamento escolhido via body;
  No momento existe somente um método disponível, que pode ser consultado via GET ```/metodo-pagamento```
  Deve ser gerado um id de fatura para ser utilizado no pagamento
3.4 Use POST ```/pagamento``` para simular o funcionamento do webhook mudando o status de pagamento do pedido para aprovado ou reprovado

**OBS**: Todos os dados necessários para envio das requisições, via parâmetros ou body, estão disponíveis em ```/api-docs```.

### 3. Preparo
4.1 Utilize o ```/pedido/iniciar-preparo/``` para pegar o próximo pedido da fila ou passar o id para furar a fila;
4.2 Utilize o ```/pedido/finalizar-preparo/{id}``` para marcar como pronto;
4.3 Utilize o ```/pedido/entregar-pedido/{id}``` para marcar como finalizado;

## Desenvolvimento do projeto

### Diagramas de fluxo

Foram utilizadas técnicas de Domain Driven Design para definição dos fluxos:

- Realização do pedido e pagamento
![diagrama do fluxo de pedido e pagamento](docs/domain-storytelling/images/pedido-pagamento.png)

- Preparação e entrega do pedido
![diagrama do fluxo de preparação e entrega](docs/domain-storytelling/images/preparo-retirada.png)

- Diagrama com a separação dos contextos delimitados
![diagrama dos contextos delimitados](docs/domain-storytelling/images/contextos-delimitados.png)

### Dicionário

* Cliente: Usuário que faz o pedido;
* Admin: Usuário com permissões de acesso à rotas de gerenciamento das entidades (ex. criação de novos produtos e categorias)
* Produto: É o alimento cadastrado pelo estabelecimento que será disponibilizado para o cliente escolher.
* Categoria: A definição do tipo de Produto
* Pedido: Solicitação realizada pelo cliente que contém itens.
* Itens do Pedido: São os produtos selecionados pelo cliente, e são vinculados a um pedido.
* Cozinha: Equipe que prepara os produtos do pedido.
* Status do Pedido: Em que etapa do processo o pedido se encontra
* Fatura: Registro relativo ao faturamento do pedido, onde detalhamos o meio de pagamento usado.
* Pagamento: Realização do pagamento da fatura de um pedido.
* Status de Pagamento: Identifica o atual estado da fatura, com ele identificamos se o pagamento foi efetuado, ocorreu algum erro, ou ainda não foi processado o pagamento.

### Estrutura do Projeto

O projeto foi reestruturado seguindo o padrão do clean architecture. 

- `datasources`:  comunicação dos serviços externos como banco de dados e checkout;
- `domain`:  contém a camada de domínio da aplicação com suas entidades, casos de uso e repositórios;
- `interfaceAdapters`: camada de interface do clean architecture com o controlador;
- `presenters`: camada externa de comunicacao externa onde se entra a API;

```shell
.
├── src
│   ├── datasources
│   │   ├── authentication
│   │   ├── database
│   │   └── paymentProvider
│   ├── domain
│   │   ├── entities
│   │   ├── repositories
│   │   └── useCases
│   ├── interfaceAdapters
│   │   └── controllers
│   ├── presenters
│   │   └── api
│   └── @types
│       └── express

```

### Domain

Contém a camada de domínio da aplicação e as lógicas de negócio.

```shell
│   ├── domain
│   │   ├── entities
│   │   │   ├── categoria.ts
│   │   │   ├── fatura.ts
│   │   │   ├── ImagemProduto.ts
│   │   │   ├── itemPedido.ts
│   │   │   ├── metodoDePagamento.ts
│   │   │   ├── Pagamento.ts
│   │   │   ├── pedido.ts
│   │   │   ├── produto.ts
│   │   │   ├── types
│   │   │   │   ├── CategoriaType.ts
│   │   │   │   ├── CobrancaType.ts
│   │   │   │   ├── itensPedidoType.ts
│   │   │   │   ├── metodoPagamentoType.ts
│   │   │   │   ├── PagamentoType.ts
│   │   │   │   ├── pedidoService.type.ts
│   │   │   │   ├── pedidoType.ts
│   │   │   │   └── produtoType.ts
│   │   │   └── valueObjects
│   │   │       ├── cpf.ts
│   │   │       └── email.ts
│   │   ├── repositories
│   │   │   ├── authenticationRepository.ts
│   │   │   ├── categoriaRepository.ts
│   │   │   ├── checkoutRepository.ts
│   │   │   ├── faturaRepository.ts
│   │   │   ├── metodoPagamentoRepository.ts
│   │   │   ├── pagamentoRepository.ts
│   │   │   ├── pedidoRepository.ts
│   │   │   └── produtoRepository.ts
│   │   └── useCases
│   │       ├── categoriaUseCase.ts
│   │       ├── faturaUseCase.ts
│   │       ├── metodoPagamentoUseCase.ts
│   │       ├── pagamentoUseCase.ts
│   │       ├── pedidoUseCase.ts
│   │       └── produtoUseCase.ts
```

O diretório `domain` contém as entidades definidoras do negócio, como `usuario`, `pedido` e `categorias` e seus casos de uso. A interface entre a camada de domínio e o restante da aplicação foi definida através do uso de interfaces em `repositories`.

### datasources e presenters

```shell
├── src
│   ├── datasources
│   │   ├── authentication
│   │   │   └── authentication.ts
│   │   ├── database
│   │   │   ├── config
│   │   │   │   ├── db.config.ts
│   │   │   │   └── interfaces
│   │   │   │       └── db.config.interface.ts
│   │   │   ├── models
│   │   │   │   ├── categoriaModel.ts
│   │   │   │   ├── faturaModel.ts
│   │   │   │   ├── index.ts
│   │   │   │   ├── itemPedidoModel.ts
│   │   │   │   ├── metodoDePagamentoModel.ts
│   │   │   │   ├── pagamentoModel.ts
│   │   │   │   ├── pedidoModel.ts
│   │   │   │   ├── produtoImagensModel.ts
│   │   │   │   └── produtoModel.ts
│   │   │   ├── repository
│   │   │   │   ├── categoriaDatabaseRepository.ts
│   │   │   │   ├── faturaDatabaseRepository.ts
│   │   │   │   ├── metodoPagamentoDatabaseRepository.ts
│   │   │   │   ├── pagamentoDatabaseRepository.ts
│   │   │   │   ├── pedidoDatabaseRepository.ts
│   │   │   │   └── produtoDatabaseRepository.ts
│   │   │   └── seeders
│   │   │       ├── cria-categorias.ts
│   │   │       └── cria-metodo-de-pagamento.ts
│   │   └── paymentProvider
│   │       └── checkoutRepository.ts

│   ├── presenters
│   │   └── api
│   │       ├── config
│   │       │   ├── interfaces
│   │       │   │   └── server.config.interface.ts
│   │       │   └── server.config.ts
│   │       ├── index.ts
│   │       ├── middleware
│   │       │   └── auth.ts
│   │       ├── routers
│   │       │   ├── categoriaRouter.ts
│   │       │   ├── index.ts
│   │       │   ├── metodoPagamentoRouter.ts
│   │       │   ├── pagamentoRouter.ts
│   │       │   ├── pedidoRouter.ts
│   │       │   ├── produtoRouter.ts
│   │       │   ├── schemas
│   │       │   │   ├── categoriaRouter.schema.ts
│   │       │   │   ├── metodoPagamentoRouter.schema.ts
│   │       │   │   ├── pagamentoRouter.schema.ts
│   │       │   │   ├── pedidoRouter.schema.ts
│   │       │   │   └── produtoRouter.schema.ts
│   │       │   └── utils.ts
│   │       └── swaggerConfig.ts
```
Nos datasources e presenters foram implementados os métodos necessários para autenticação dos usuários. 
