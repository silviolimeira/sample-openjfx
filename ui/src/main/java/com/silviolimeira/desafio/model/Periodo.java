package com.silviolimeira.desafio.model;

import javafx.beans.property.SimpleStringProperty;

public class Periodo {

    private final SimpleStringProperty entrada;
    private final SimpleStringProperty saida;

    public Periodo(String entrada, String saida) {
        this.entrada = new SimpleStringProperty(entrada);
        this.saida = new SimpleStringProperty(saida);
    }

    public String getEntrada() {
        return entrada.get();
    }

    public void setEntrada(String fName) {
        entrada.set(fName);
    }

    public String getSaida() {
        return saida.get();
    }

    public void setSaida(String fName) {
        saida.set(fName);
    }


}