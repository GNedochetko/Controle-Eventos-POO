package br.unicentro.appeventos.model;

public class Estado {
    private int estadoId;
    private String nome;
    private String sigla;
    private String regiao;

    public Estado() {
    }

    public Estado(int estadoId, String nome, String sigla, String regiao) {
        this.estadoId = estadoId;
        this.nome = nome;
        this.sigla = sigla;
        this.regiao = regiao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    @Override
    public String toString() {
        return nome + " (" + sigla + ")";
    }
}

