Aqui está uma sugestão de README.MD para o seu projeto, com base nos arquivos que você forneceu.

-----

# Buscador de Séries

Essa é uma aplicação de console (CLI) desenvolvida em Java e Spring Boot. O objetivo deste projeto é consumir a API do [OMDB](http://www.omdbapi.com/) para buscar e exibir informações detalhadas sobre séries de TV.

Este projeto foi criado como um exercício prático para o uso de Spring Boot sem uma interface web, focando no consumo de APIs, desserialização de JSON e manipulação de coleções de dados com Streams.

## Funcionalidades Principais

A aplicação permite ao usuário:

  * **Buscar uma Série:** O usuário digita o nome de uma série para buscar na OMDB API.
  * **Listar Informações Gerais:** Exibe o título, o total de temporadas e a avaliação geral (IMDb) da série encontrada.
  * **Listar Todos os Episódios:** Itera por todas as temporadas disponíveis e exibe uma lista de todos os episódios, formatados por temporada.
  * **Top 10 Episódios:** Filtra e exibe os 10 episódios com a melhor avaliação de toda a série.
  * **Buscar Episódio por Título:** Permite ao usuário digitar um trecho do nome de um episódio para buscar e exibir seus detalhes (temporada, número, nome, avaliação e data de lançamento).
  * **Filtrar Episódios por Ano:** Solicita um ano ao usuário e exibe todos os episódios lançados a partir daquela data.
  * **Calcular Média por Temporada:** Agrupa todos os episódios por temporada e calcula a média de avaliação para cada uma.
  * **Exibir Estatísticas Gerais:** Mostra estatísticas gerais de avaliação de todos os episódios (média, melhor avaliação, pior avaliação e contagem total).

## Tecnologias Utilizadas

  * **Java 17**
  * **Spring Boot 3.5.6** (sem web, usando `spring-boot-starter`)
  * **Maven** (para gerenciamento de dependências)
  * **Jackson Databind** (para desserialização de JSON)
  * **dotenv-java** (para gerenciamento de chaves de API)
  * **Java HTTP Client** (para fazer requisições à API)

## Estrutura do Projeto

  * `Buscador.java`: Classe principal que inicializa o Spring Boot e executa a lógica principal como um `CommandLineRunner`.
  * `principal/Principal.java`: Contém todo o fluxo de interação com o usuário e a lógica de orquestração das chamadas de serviço.
  * `service/ConsumoAPI.java`: Classe responsável por fazer a requisição HTTP para a API da OMDB e retornar o JSON como string.
  * `service/ConverteDados.java`: Implementação da interface `IConverteDados`, usando o Jackson ObjectMapper para converter o JSON em DTOs (Records).
  * `model/`: Contém os Records (DTOs) usados para mapear a resposta da API (`DadosSerie`, `DadosTemporada`, `DadosEpisodio`) e a classe `Episodios` para manipulação interna dos dados.

## Pré-requisitos

  * Java 17 ou superior.
  * Maven.
  * Uma chave de API válida do **OMDB API**. Você pode obter a sua em [omdbapi.com](http://www.omdbapi.com/apikey.aspx).

## Como Configurar e Executar

1.  **Clone o repositório:**

    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    cd buscador-de-series-spring
    ```

2.  **Crie o arquivo de ambiente:**
    Na raiz do projeto, crie um arquivo chamado `.env`. O `.gitignore` já está configurado para ignorar este arquivo.

3.  **Adicione sua API Key:**
    Dentro do arquivo `.env`, adicione sua chave da OMDB API da seguinte forma:

    ```
    APIKEY=SUA_CHAVE_AQUI
    ```

4.  **Execute a aplicação:**
    Utilize o Maven Wrapper para executar a aplicação:

    *No Linux/macOS:*

    ```bash
    ./mvnw spring-boot:run
    ```

    *No Windows:*

    ```bash
    ./mvnw.cmd spring-boot:run
    ```

5.  **Interaja com o console:**
    Após a inicialização, o console solicitará que você digite o nome de uma série para iniciar o processo.
