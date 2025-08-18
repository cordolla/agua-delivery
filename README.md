# Agua Delivery

Este projeto é um sistema de microservices para uma aplicação de delivery de água, construído com Spring Boot e Spring Cloud.

## Sobre o Projeto

O "Agua Delivery" é uma aplicação de e-commerce completa, dividida em múltiplos serviços independentes que se comunicam para gerenciar produtos, pedidos e pagamentos. A arquitetura é projetada para ser escalável e resiliente, utilizando tecnologias modernas para containerização e orquestração de serviços.

### Tecnologias Utilizadas

* **Backend:**
    * Java 17
    * Spring Boot 3.3.2
    * Spring Cloud 2023.0.3
* **Banco de Dados:**
    * PostgreSQL
* **Mensageria:**
    * RabbitMQ
* **Comunicação e Orquestração de Microservices:**
    * Spring Cloud Gateway (API Gateway)
    * Spring Cloud Netflix Eureka (Service Discovery)
    * Spring Cloud OpenFeign (Cliente HTTP Declarativo)
* **Pagamentos:**
    * Mercado Pago SDK
* **Containerização:**
    * Docker & Docker Compose

## Arquitetura de Microservices

O sistema é composto pelos seguintes microservices, orquestrados pelo Docker Compose:

* **Discovery Service (Eureka):**
    * Responsável pelo registro e descoberta de todos os outros microservices na rede. Cada serviço se registra no Eureka Server para que possam se encontrar e se comunicar dinamicamente.
    * Porta Padrão: `8761`

* **Gateway Service:**
    * É o ponto de entrada único para todas as requisições do cliente. Ele roteia as requisições para os serviços apropriados com base no caminho da URL (`/api/products/**`, `/api/orders/**`, etc.) e pode lidar com autenticação, logging e outras preocupações transversais.
    * Porta Padrão: `8080`

* **Product Service:**
    * Gerencia o catálogo de produtos, incluindo criação, leitura e atualização de informações dos produtos.
    * Endpoint: `/api/products/**`
    * Porta Docker: `8081`

* **Order Service:**
    * Responsável por criar e gerenciar os pedidos dos clientes. Ele se comunica com o `product-service` (via Feign) para obter detalhes dos produtos e com o `payment-service` para processar o pagamento.
    * Endpoint: `/api/orders/**`
    * Porta Docker: `8082`

* **Payment Service:**
    * Processa os pagamentos utilizando a API do Mercado Pago. Ele recebe a solicitação de pagamento do `order-service`, gera um QR Code PIX e, após a confirmação do pagamento (recebida por webhook), notifica o `order-service` via RabbitMQ.
    * Endpoint: `/api/payments/**`
    * Porta Docker: `8083`

## Como Executar o Projeto

### Pré-requisitos

* Docker
* Docker Compose
* Git

### Passos para a Instalação

1.  **Clone o Repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd agua-delivery
    ```

2.  **Configure as Variáveis de Ambiente:**
    Crie um arquivo `.env` na raiz do projeto com o seu token de acesso do Mercado Pago, como mostrado no arquivo de exemplo.
    ```env
    MERCADOPAGO_ACCESS_TOKEN=SEU_ACCESS_TOKEN_DO_MERCADO_PAGO
    ```
    Você pode obter um token de teste no painel de desenvolvedores do Mercado Pago.

3.  **Suba os Containers com Docker Compose:**
    Execute o comando a seguir na raiz do projeto para construir as imagens e iniciar todos os containers.
    ```bash
    docker-compose up --build
    ```
    Este comando irá:
    * Construir a imagem Docker para cada microserviço, conforme definido em seus respectivos `Dockerfile`.
    * Iniciar os containers para PostgreSQL, RabbitMQ, e todos os microservices.
    * Criar os bancos de dados necessários (`products-service`, `orders-service`, `payments-service`) usando o script de inicialização do Postgres.

4.  **Verifique se os Serviços Estão Rodando:**
    * **Eureka Dashboard:** Acesse `http://localhost:8761` para ver o painel do Eureka e verificar se todos os serviços (`GATEWAY-SERVICE`, `ORDER-SERVICE`, `PAYMENT-SERVICE`, `PRODUCT-SERVICE`) estão registrados como `UP`.
    * **RabbitMQ Management:** Acesse `http://localhost:15672` para ver o painel de gerenciamento do RabbitMQ. Use `admin` como usuário e senha.

## API Endpoints

O acesso aos microservices deve ser feito através do **API Gateway** na porta `8080`.

### Product Service (`/api/products`)

* **`POST /api/products`**: Cria um novo produto.
    * **Body:**
        ```json
        {
          "name": "Água Mineral 20L",
          "description": "Galão de água mineral sem gás.",
          "price": 10.00,
          "stockQuantity": 100
        }
        ```

* **`GET /api/products/{id}`**: Busca um produto pelo ID.

### Order Service (`/api/orders`)

* **`POST /api/orders`**: Cria um novo pedido e gera um pagamento PIX.
    * **Body:**
        ```json
        {
          "customerName": "João da Silva",
          "whatsappNumber": "11999998888",
          "shippingAddress": "Rua das Flores, 123",
          "items": [
            {
              "productId": 1,
              "quantity": 2
            }
          ]
        }
        ```
    * **Resposta:**
        Retorna os detalhes do pedido, incluindo um QR Code em Base64 e um código "copia e cola" para o pagamento PIX.

### Payment Service (`/api/payments`)

* **`POST /api/payments/webhook?data.id={paymentId}`**: Webhook para receber a confirmação de pagamento do Mercado Pago. O Mercado Pago notifica este endpoint quando um pagamento é aprovado. O serviço então atualiza o status do pagamento e publica uma mensagem no RabbitMQ para que o `order-service` atualize o status do pedido para `PAID`.
