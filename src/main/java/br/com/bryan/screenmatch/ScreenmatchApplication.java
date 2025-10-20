package br.com.bryan.screenmatch;

import br.com.bryan.screenmatch.model.DadosEpisodio;
import br.com.bryan.screenmatch.model.DadosSerie;
import br.com.bryan.screenmatch.service.ConsumoAPI;
import br.com.bryan.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        json = consumoAPI.obterDados("http://www.omdbapi.com/?t=the+office&season=7&episode=11&apikey=27205e74");
        DadosEpisodio dadosEpisodio = converteDados.obterDados(json, DadosEpisodio.class);
        System.out.println(dadosEpisodio);
	}
}
