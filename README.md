# API Petiscaí

## 1. Introdução

### Título do Projeto/API
**API Petiscaí**

### Descrição Geral
A API Petiscaí é uma solução projetada para gerenciar o cadastro de bebidas alcoólicas e petiscos, abrangendo desde o registro de produtos até o gerenciamento de pedidos de clientes. Os dados são armazenados em um banco de dados MySQL, estruturado com tabelas para clientes, produtos, pedidos e status de pagamento.

Desenvolvida em **Java**, utilizando o framework **Spring Boot**, a API integra tecnologias como:
- **Swagger** para documentação
- **RabbitMQ** para comunicação assíncrona
- **MySQL** para persistência de dados

Este sistema é ideal para projetos que demandem um controle eficiente de pedidos e produtos, bem como para a construção de aplicativos voltados ao serviço de delivery.

### Objetivo
Automatizar e otimizar o controle de pedidos e produtos para restaurantes e serviços de delivery, focados em bebidas alcoólicas e petiscos, proporcionando eficiência e escalabilidade aos processos operacionais.

---

## 2. Guia de Início Rápido (Quick Start)

### Primeiros Passos: Executando o Projeto

1. Abra o **Spring Tool Suite (STS)** ou **Eclipse**.
2. No menu principal, selecione:  
   `File → Open Projects from File System...`
3. Clique em **Directory** e navegue até a pasta onde o repositório foi clonado.
4. Confirme a seleção e aguarde a importação do projeto.

#### Executando o Projeto
1. No **STS** ou **Eclipse**, localize o diretório raiz do projeto, identificado como `PETISCAÍ`.
2. Clique com o botão direito do mouse sobre o diretório principal do projeto.
3. Navegue até `Run As → Spring Boot App` para iniciar o servidor.
4. Após o projeto iniciar, abra o navegador (recomendamos o **Google Chrome**).
5. Acesse a URL:  
   ```url
   http://localhost:8000/swagger-ui/index.html#/

## 3. Descrição Técnica

### Estrutura do Projeto
O projeto segue a seguinte estrutura de pastas e arquivos principais:
Petiscai/ ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ └── acc.br.petiscai/ │ │ │ ├── ApplicationJava │ │ │ ├── config/ │ │ │ │ └── CorsWebConfig │ │ │ ├── controller/ │ │ │ │ ├── ClienteController │ │ │ │ ├── EstoqueController │ │ │ │ ├── PedidoController │ │ │ │ └── ProdutoController │ │ │ ├── dto/ │ │ │ │ ├── ClienteDto │ │ │ │ ├── ItemPedidoDto │ │ │ │ ├── PedidoDto │ │ │ │ └── ProdutoDto │ │ │ ├── entity/ │ │ │ │ ├── Cliente │ │ │ │ ├── Estoque │ │ │ │ ├── ItemPedido │ │ │ │ ├── Pagamento │ │ │ │ ├── Pedido │ │ │ │ └── Produto │ │ │ ├── producer/ │ │ │ │ ├── config/ │ │ │ │ │ └── RabbitMQConfig │ │ │ │ ├── controller/ │ │ │ │ │ └── UserController │ │ │ │ ├── dto/ │ │ │ │ │ ├── RegisterUserDto │ │ │ │ │ └── UserRegisteredPayload │ │ │ ├── repository/ │ │ │ │ ├── ClienteRepository │ │ │ │ ├── EstoqueRepository │ │ │ │ ├── PedidoRepository │ │ │ │ └── ProdutoRepository │ │ │ ├── service/ │ │ │ │ ├── ClienteService │ │ │ │ ├── EstoqueService │ │ │ │ ├── PedidoService │ │ │ │ └── ProdutoService │ └── resources/ │ └── application.properties ├── pom.xml


### Principais Diretórios e Arquivos

- **`src/main/java/acc.br.petiscai/`**
  - **`ApplicationJava`**: Classe principal para inicializar o projeto.
  - **`config/`**: Configurações gerais do projeto.
    - **`CorsWebConfig`**: Configuração do CORS.
  - **`controller/`**: Controladores para os endpoints da API.
    - **`ClienteController`**, **`EstoqueController`**, **`PedidoController`**, **`ProdutoController`**.
  - **`dto/`**: Data Transfer Objects (DTOs) utilizados para comunicação entre camadas.
    - **`ClienteDto`**, **`ItemPedidoDto`**, **`PedidoDto`**, **`ProdutoDto`**.
  - **`entity/`**: Entidades representando tabelas do banco de dados.
    - **`Cliente`**, **`Estoque`**, **`ItemPedido`**, **`Pagamento`**, **`Pedido`**, **`Produto`**.
  - **`producer/`**: Gerenciamento de comunicação assíncrona com RabbitMQ.
    - **`config/`**: Configuração do RabbitMQ.
    - **`controller/`**: Controlador para usuários.
    - **`dto/`**: DTOs específicos para mensagens RabbitMQ.
  - **`repository/`**: Repositórios para acesso ao banco de dados.
    - **`ClienteRepository`**, **`EstoqueRepository`**, **`PedidoRepository`**, **`ProdutoRepository`**.
  - **`service/`**: Serviços que implementam as regras de negócio.
    - **`ClienteService`**, **`EstoqueService`**, **`PedidoService`**, **`ProdutoService`**.

- **`src/main/resources/`**
  - **`application.properties`**: Configurações do projeto.

- **`pom.xml`**: Configuração do Maven para gerenciamento de dependências.


---

### Arquitetura do Sistema
A arquitetura do sistema é baseada no modelo Cliente-Servidor, com a seguinte configuração:
Cliente → API Petiscai → Banco de Dados MySQL


Essa estrutura permite o cadastro e gerenciamento de dados, incluindo clientes, produtos, pedidos e status de pagamento, com suporte a operações síncronas e assíncronas (via **RabbitMQ**).

---

### Endpoints da API

#### **ClienteController**
- `POST /api/cliente/save` - Criação de um novo cliente.
- `PUT /api/cliente/update/{id}` - Atualização dos dados de um cliente.
- `GET /api/cliente/findById/{id}` - Busca de um cliente pelo ID.
- `GET /api/cliente/findAll` - Busca de todos os clientes cadastrados.
- `DELETE /api/cliente/delete/{id}` - Exclusão de um cliente pelo ID.

#### **ProdutoController**
- `POST /api/produto/save` - Criação de um novo produto.
- `PUT /api/produto/update/{id}` - Atualização de um produto pelo ID.
- `GET /api/produto/findById/{id}` - Busca de um produto pelo ID.
- `GET /api/produto/findAll` - Busca de todos os produtos cadastrados.
- `DELETE /api/produto/delete/{id}` - Exclusão de um produto pelo ID.

#### **PedidoController**
- `POST /api/pedido/create` - Criação de um novo pedido (associando cliente e produto).
- `GET /api/pedido/{id}` - Busca de um pedido pelo ID.
- `GET /api/pedido/findAll` - Busca de todos os pedidos.

---

### Exemplo de Requisição e Resposta

**Requisição:**
```json
POST /api/cliente/save
{
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "123456789"
}


