package br.com.bryan.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio (@JsonAlias("Title") String nomeEpisodio,
                             @JsonAlias("Episode") Integer numeroEpisodio,
                             @JsonAlias("imdbRating") String avaliacao,
                             @JsonAlias("Released") String dataLancamento){
}
