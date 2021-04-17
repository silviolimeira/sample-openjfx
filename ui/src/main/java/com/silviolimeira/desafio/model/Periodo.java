package com.silviolimeira.desafio.model;

public class Periodo implements Comparable<Periodo> {

    private String entrada;
    private String saida;

    public Periodo(String entrada, String saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    public String toString() {
        return this.entrada + " " + this.saida;
    }

    @Override
    public int compareTo(Periodo o) {
        return o.toString().compareToIgnoreCase(this.toString());
    }
}