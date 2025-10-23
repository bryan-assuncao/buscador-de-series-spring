package br.com.bryan.buscarserie.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodios {
    private Integer temporada;
    private String nomeEpisodio;
    private Integer numeroEpisodio;
    private double avaliacao;
    private LocalDate dataLancamento;

    public Episodios(Integer temporada, String nomeEpisodio, Integer numeroEpisodio, double avaliacao, LocalDate dataLancamento) {
        this.temporada = temporada;
        this.nomeEpisodio = nomeEpisodio;
        this.numeroEpisodio = numeroEpisodio;
        this.avaliacao = avaliacao;
        this.dataLancamento = dataLancamento;
    }

    public Episodios(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.nomeEpisodio = dadosEpisodio.nomeEpisodio();
        this.numeroEpisodio = dadosEpisodio.numeroEpisodio();

        try{
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException e){
            this.avaliacao = 0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeParseException e){
            this.dataLancamento = null;
        }
    }

    @Override
    public String toString() {
        return "Episodios{" +
                "temporada=" + temporada +
                ", nomeEpisodio='" + nomeEpisodio + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento +
                '}';
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getNomeEpisodio() {
        return nomeEpisodio;
    }

    public void setNomeEpisodio(String nomeEpisodio) {
        this.nomeEpisodio = nomeEpisodio;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }


}
