package br.com.bryan.buscarserie.main;

import br.com.bryan.buscarserie.model.DadosEpisodio;
import br.com.bryan.buscarserie.model.DadosSerie;
import br.com.bryan.buscarserie.model.DadosTemporada;
import br.com.bryan.buscarserie.model.Episodios;
import br.com.bryan.buscarserie.service.ConsumoAPI;
import br.com.bryan.buscarserie.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {
    private Scanner leitura = new Scanner(System.in);

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    Dotenv dotenv = Dotenv.load();
    private String APIKEY = dotenv.get("APIKEY");

    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados converte = new ConverteDados();


    public void exibirMenu() throws JsonProcessingException {
        System.out.print("Digite o nome da série: ");

        String nomeSerie = leitura.nextLine().trim().replace(" ", "+");

        String json = consumo.obterDados(ENDERECO + nomeSerie + APIKEY);

        DadosSerie dadosSerie = converte.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i<= dadosSerie.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie + "&season=" + i + APIKEY);
            DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios()
                .forEach(e -> System.out.println(e.nomeEpisodio())));

        List<DadosEpisodio> dadosEpisodios = temporadas
                .stream()
                .flatMap(t -> t.episodios().stream())
                .toList();

        System.out.println("\nTOP 10 EPISÓDIOS");

        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(10)
                .map(e -> e.nomeEpisodio().toUpperCase())
                .forEach(System.out::println);

        System.out.println("-----------------------------------------------------------");

        List<Episodios> episodios = temporadas.stream()
                        .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodios(t.temporada(), d)))
                        .toList();
        episodios.forEach(System.out::println);

        System.out.println("-----------------------------------------------------------");

        System.out.println("A partir de que ano você deseja ver os episódio? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getNomeEpisodio() +
                                " Data lançamento: " + e.getDataLancamento().format(formatador)
                ));
    }
}
