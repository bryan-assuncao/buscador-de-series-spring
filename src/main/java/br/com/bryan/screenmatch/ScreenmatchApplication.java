package br.com.bryan.screenmatch;

import br.com.bryan.screenmatch.main.Main;
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
        Main main = new Main();
        main.exibirMenu();
    }
}