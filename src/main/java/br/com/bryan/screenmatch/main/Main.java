package br.com.bryan.screenmatch.main;

import br.com.bryan.screenmatch.model.DadosSerie;
import br.com.bryan.screenmatch.model.DadosTemporada;
import br.com.bryan.screenmatch.service.ConsumoAPI;
import br.com.bryan.screenmatch.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner leitura = new Scanner(System.in);

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=27205e74";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados converte = new ConverteDados();


    public void exibirMenu() throws JsonProcessingException {
        System.out.print("Digite o nome da série: ");

        String nomeSerie = leitura.nextLine().trim().replace(" ", "+");

        String json = consumo.obterDados(ENDERECO + nomeSerie +  APIKEY);

        DadosSerie dadosSerie = converte.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i<= dadosSerie.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie + "&season=" + i + APIKEY);
            DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

//        for(int i = 0; i < dadosSerie.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).nomeEpisodio());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.nomeEpisodio())));


        leitura.close();
    }
}
