package br.unicentro.appeventos.model;

import java.time.LocalDate;

public class Evento {
    private int eventoId;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double precoIngresso;
    private Cidade cidade;

    public Evento() {
    }

    public Evento(int eventoId, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, double precoIngresso, Cidade cidade) {
        this.eventoId = eventoId;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.precoIngresso = precoIngresso;
        this.cidade = cidade;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        if (dataFim != null && dataInicio.isAfter(dataFim))
            throw new IllegalArgumentException("Data de início não pode ser depois da data final.");
        this.dataInicio = dataInicio;
    }

    public void setDataFim(LocalDate dataFim) {
        if (dataInicio != null && dataFim.isBefore(dataInicio))
            throw new IllegalArgumentException("Data final não pode ser antes da data inicial.");
        this.dataFim = dataFim;
    }


    public LocalDate getDataFim() {
        return dataFim;
    }

    public double getPrecoIngresso() {
        return precoIngresso;
    }

    public void setPrecoIngresso(double precoIngresso) {
        if(precoIngresso < 0)
            throw new IllegalArgumentException("Preço do ingresso não pode ser negativo");
        this.precoIngresso = precoIngresso;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        if (cidade == null)
            throw new IllegalArgumentException("Cidade não pode ser nula");
        this.cidade = cidade;
    }


    @Override
    public String toString() {
        return nome + " - " + cidade.getNome() + ", " + cidade.getEstado().getNome();
    }
}

