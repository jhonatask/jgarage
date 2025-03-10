# jgarage Frontend
Escolhe React por ser facil implementacao de um SPA.
## Descrição
O projeto **jgarage-front** é uma aplicação frontend para gerenciamento de veículos em uma garagem. Ele permite adicionar, editar, deletar e listar veículos. Esta aplicação foi desenvolvida utilizando React com Next.js e Material-UI.

## Tecnologias Utilizadas
- **Framework**: Next.js
- **Linguagem**: TypeScript
- **Biblioteca de UI**: Material-UI
- **HTTP Client**: Axios

## Pré-requisitos
- Node.js (versão 18 ou superior)

## Instalação e Execução

1. Execucao do projeto: Clone projeto princial
   ```bash
   git clone https://github.com/seu-usuario/jgarage.git
   cd garage-front
2. 
    ```
    npm install
3. Inicie a aplicação:
    ```
    npm run dev

## Estrutura do Projeto
- pages/: Contém as páginas da aplicação.
- components/: Contém os componentes reutilizáveis.
- styles/: Contém os arquivos de estilo.
- services/: Contém os serviços para chamadas HTTP.

# API de Gerenciamento de Veículos
Escolhe quarkus como framework de backend por ser um supersonico e poder trabalhar com container e resolvendo muitos problemas de performace do java.

Este projeto é uma API RESTful desenvolvida com **Quarkus** para gerenciar um catálogo de veículos, incluindo funcionalidades de cadastro, atualização, exclusão, e relatórios sobre a distribuição de veículos.

## Tecnologias Utilizadas

- **Java  21**
- **Quarkus 3.8.0**
- **Hibernate ORM (Panache)**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Maven**
  
## Pré-requisitos

- **Java 21**
- **Maven**
- **Docker**

## Como Executar

### 1. Clonar o repositório:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd garage-backend
```
### 2. Configurar o Banco de Dados com Docker
No diretório raiz do projeto, execute o seguinte comando para subir o banco de dados PostgreSQL usando o Docker Compose:
```
docker-compose up -d
```
### 3. Rodar a API
```
./mvnw quarkus:dev
```
### 4. Popular o banco de dados
```sql
INSERT INTO veiculo (veiculo, marca, ano, cor, descricao, vendido, created, updated) VALUES
('Model S', 'Tesla', 2022, 'Preto', 'Sedan elétrico de alta performance', false, NOW(), NOW()),
('Mustang', 'Ford', 2020, 'Vermelho', 'Esportivo clássico com motor potente', true, NOW(), NOW()),
('Civic', 'Honda', 2019, 'Prata', 'Sedan compacto e econômico', false, NOW(), NOW()),
('Corolla', 'Toyota', 2021, 'Branco', 'Sedan confiável com ótimo consumo de combustível', true, NOW(), NOW()),
('Golf', 'Volkswagen', 2018, 'Azul', 'Hatchback esportivo com ótimo desempenho', false, NOW(), NOW()),
('Polo', 'Volkswagen', 2022, 'Cinza', 'Compacto com design moderno e eficiente', false, NOW(), NOW()),
('Q5', 'Audi', 2023, 'Preto', 'SUV de luxo com tecnologia avançada', false, NOW(), NOW()),
('A4', 'Audi', 2021, 'Branco', 'Sedan de luxo com performance refinada', true, NOW(), NOW()),
('X5', 'BMW', 2020, 'Preto', 'SUV de luxo com excelente performance', false, NOW(), NOW()),
('Series 3', 'BMW', 2019, 'Prata', 'Sedan com ótima dirigibilidade e luxo', true, NOW(), NOW()),
('Cherokee', 'Jeep', 2022, 'Vermelho', 'SUV off-road com robustez e conforto', false, NOW(), NOW()),
('Compass', 'Jeep', 2021, 'Cinza', 'SUV compacto e versátil', true, NOW(), NOW()),
('Tucson', 'Hyundai', 2020, 'Branco', 'SUV com design moderno e alta tecnologia', false, NOW(), NOW()),
('Elantra', 'Hyundai', 2019, 'Azul', 'Sedan espaçoso e econômico', true, NOW(), NOW()),
('Kona', 'Hyundai', 2023, 'Verde', 'SUV compacto elétrico', false, NOW(), NOW()),
('RAV4', 'Toyota', 2022, 'Prata', 'SUV híbrido com excelente eficiência', false, NOW(), NOW()),
('Camry', 'Toyota', 2020, 'Preto', 'Sedan premium com ótimo desempenho', true, NOW(), NOW()),
('Leaf', 'Nissan', 2023, 'Azul', 'Hatchback elétrico com boa autonomia', false, NOW(), NOW()),
('Altima', 'Nissan', 2021, 'Cinza', 'Sedan com tecnologia avançada e conforto', true, NOW(), NOW()),
('Kicks', 'Nissan', 2022, 'Branco', 'SUV compacto com boa relação custo-benefício', false, NOW(), NOW());
```
### Endpoints Disponiveis
Garage API
Operações relacionadas ao gerenciamento de veículos
```
GET
/veiculos
Pesquisar Veiculo e possivel passar filtros para esse endpoint
ex: marca,cor,ano

POST
/veiculos
Cadastra um novo veículo

GET
/veiculos/distribuicao-decadas
Distribuição de veículos por década

GET
/veiculos/distribuicao-fabricante
Distribuição de veículos por fabricante

GET
/veiculos/nao-vendidos
Informacao de quantos veiculos vendidos

GET
/veiculos/ultima-semana
Veiculos Cadastrados na ultima semana

GET
/veiculos/{id}
Busca um veículo por ID

PUT
/veiculos/{id}
Atualiza totalmente os dados de um veículo

DELETE
/veiculos/{id}
Deleta um veículo por ID

PATCH
/veiculos/{id}
Atualiza parcialmente os dados de um veículo
```