package br.com.bryan.screenmatch;

import br.com.bryan.screenmatch.model.DadosEpisodio;
import br.com.bryan.screenmatch.model.DadosSerie;
import br.com.bryan.screenmatch.model.DadosTemporada;
import br.com.bryan.screenmatch.service.ConsumoAPI;
import br.com.bryan.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("http://www.omdbapi.com/?t=the+office&apikey=27205e74");
        System.out.println(json);

        System.out.println("----------------------------------------------------------------------");

        ConverteDados converteDados = new ConverteDados();

        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        System.out.println("----------------------------------------------------------------------");

        json = consumoAPI.obterDados("http://www.omdbapi.com/?t=the+office&season=7&episode=11&apikey=27205e74");
        DadosEpisodio dadosEpisodio = converteDados.obterDados(json, DadosEpisodio.class);
        System.out.println(dadosEpisodio);

        System.out.println("----------------------------------------------------------------------");

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i<= dadosSerie.totalTemporadas(); i++){
            json = consumoAPI.obterDados("http://www.omdbapi.com/?t=the+office&season=" + i + "&apikey=27205e74");
            DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);


    }
}