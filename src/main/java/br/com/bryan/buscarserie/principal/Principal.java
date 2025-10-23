package br.com.bryan.buscarserie.principal;

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
import java.util.stream.Collectors;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final Dotenv dotenv = Dotenv.load();
    private final String APIKEY = "&apikey=" + dotenv.get("APIKEY");

    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverteDados converte = new ConverteDados();

    private List<Episodios> episodios = new ArrayList<>();

    public void exibirMenu() throws JsonProcessingException {
        System.out.print("\nDigite o nome da série: ");
        String nomeSerie = leitura.nextLine().trim().replace(" ", "+");

        String json = consumo.obterDados(ENDERECO + nomeSerie + APIKEY);
        DadosSerie dadosSerie = converte.obterDados(json, DadosSerie.class);

        if (dadosSerie == null || dadosSerie.titulo() == null || dadosSerie.totalTemporadas() == 0) {
            System.out.println("Série não encontrada ou sem temporadas disponíveis.");
            return;
        }

        System.out.println("\n================= INFORMAÇÕES DA SÉRIE =================");
        System.out.println("Título: " + dadosSerie.titulo());
        System.out.println("Total de Temporadas: " + dadosSerie.totalTemporadas());
        System.out.println("Avaliação geral: " + dadosSerie.avaliacao());
        System.out.println("========================================================\n");

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie + "&season=" + i + APIKEY);
            DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
            if (dadosTemporada != null && dadosTemporada.episodios() != null) {
                temporadas.add(dadosTemporada);
            }
        }

        episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodios(t.temporada(), d)))
                .toList();

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (DadosTemporada t : temporadas) {
            System.out.println("---------- TEMPORADA " + t.temporada() + " ----------");
            for (DadosEpisodio e : t.episodios()) {
                System.out.printf("%2d - %-40s Avaliação: %s  Lançamento: %s%n",
                        e.numeroEpisodio(),
                        e.nomeEpisodio(),
                        e.avaliacao(),
                        e.dataLancamento() != null ? e.dataLancamento() : "N/A"
                );
            }
            System.out.println();
        }

        System.out.println("========== TOP 10 EPISÓDIOS ==========");
        episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .sorted(Comparator.comparing(Episodios::getAvaliacao).reversed())
                .limit(10)
                .forEach(e -> System.out.printf("%-40s Avaliação: %.1f%n",
                        e.getNomeEpisodio().toUpperCase(),
                        e.getAvaliacao()));
        System.out.println("=====================================\n");

        System.out.print("Digite o nome do episódio: ");
        String tituloEpisodio = leitura.nextLine().trim();
        Optional<Episodios> episodioBuscado = episodios.stream()
                .filter(e -> e.getNomeEpisodio().toUpperCase().contains(tituloEpisodio.toUpperCase()))
                .findFirst();

        System.out.println("-----------------------------------------------------------");
        if (episodioBuscado.isPresent()) {
            Episodios e = episodioBuscado.get();
            System.out.println("EPISÓDIO ENCONTRADO");
            System.out.println("Temporada: " + e.getTemporada());
            System.out.println("Número: " + e.getNumeroEpisodio());
            System.out.println("Nome: " + e.getNomeEpisodio());
            System.out.println("Avaliação: " + e.getAvaliacao());
            System.out.println("Data de lançamento: " + (e.getDataLancamento() != null ? e.getDataLancamento().format(formatador) : "N/A"));
        } else {
            System.out.println("EPISÓDIO NÃO ENCONTRADO.");
        }
        System.out.println("-----------------------------------------------------------");

        int ano = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("A partir de que ano você deseja ver os episódios? ");
                ano = Integer.parseInt(leitura.nextLine().trim());
                valido = true;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida! Digite um número válido para o ano.");
            }
        }

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        System.out.println("\n========== EPISÓDIOS A PARTIR DE " + ano + " ==========");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.printf("Temporada: %d  Episódio: %-30s Data lançamento: %s%n",
                        e.getTemporada(),
                        e.getNomeEpisodio(),
                        e.getDataLancamento().format(formatador)
                ));
        System.out.println("========================================\n");

        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodios::getTemporada,
                        Collectors.averagingDouble(Episodios::getAvaliacao)));

        System.out.println("========== MÉDIA DE AVALIAÇÕES POR TEMPORADA ==========");
        avaliacaoPorTemporada.forEach((t, m) -> System.out.printf("Temporada %d: %.2f%n", t, m));
        System.out.println("=======================================================\n");

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodios::getAvaliacao));

        System.out.println("========== ESTATÍSTICAS GERAIS ==========");
        System.out.printf("Média: %.2f%n", est.getAverage());
        System.out.printf("Melhor avaliação: %.1f%n", est.getMax());
        System.out.printf("Pior avaliação: %.1f%n", est.getMin());
        System.out.printf("Quantidade de episódios avaliados: %d%n", (int) est.getCount());
        System.out.println("=======================================");
    }
}
