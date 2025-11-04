package br.unicentro.appeventos.model;

public class Cidade {
    private int cidadeId;
    private String nome;
    private int populacao;
    private double area;
    private Estado estado;

    public Cidade() {}

    public Cidade(int cidadeId, String nome, int populacao, double area, Estado estado) {
        this.cidadeId = cidadeId;
        this.nome = nome;
        this.populacao = populacao;
        this.area = area;
        this.estado = estado;
    }

    public int getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(int cidadeId) {
        this.cidadeId = cidadeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return nome + " - " + estado.getSigla();
    }
}

