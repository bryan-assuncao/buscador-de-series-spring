package br.com.bryan.buscarserie;

import br.com.bryan.buscarserie.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Buscador implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Buscador.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        Principal main = new Principal();
        main.exibirMenu();
    }
}