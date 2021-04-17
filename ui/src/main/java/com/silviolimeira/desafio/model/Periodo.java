package com.silviolimeira.desafio.model;

import javafx.beans.property.SimpleStringProperty;

public class Periodo {

    private String entrada;
    private String saida;

    public Periodo(String entrada, String saida) {
        this.entrada = entrada; new SimpleStringProperty(entrada);
        this.saida = saida; new SimpleStringProperty(saida);
    }

    public String getEntrada() {
        return entrada; //return entrada.get();
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada; //entrada.set(fName);
    }

    public String getSaida() {
        return saida; //return saida.get();
    }

    public void setSaida(String saida) {
        saida = saida; //saida.set(fName);
    }

    public String toString() {
        return this.entrada + " " + this.saida;
    }

}