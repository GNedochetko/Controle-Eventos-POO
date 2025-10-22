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

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
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

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    @Override
    public String toString() {
        return nome + " (" + sigla + ")";
    }
}

