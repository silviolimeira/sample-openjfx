package com.silviolimeira.desafio.model;

public class Periodo implements Comparable<Periodo> {

    private String entrada;
    private String saida;

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public int getMinutoEntrada() {
        return minutoEntrada;
    }

    public int getHoraSaida() {
        return horaSaida;
    }

    public int getMinutoSaida() {
        return minutoSaida;
    }

    public int getMinutosEntrada() {
        return minutosEntrada;
    }

    public int getMinutosSaida() {
        return minutosSaida;
    }

    int horaEntrada;
    int minutoEntrada;
    int horaSaida;
    int minutoSaida;
    int minutosEntrada;
    int minutosSaida;

    public Periodo(String entrada, String saida) {
//        this.entrada = entrada;
//        this.saida = saida;
        this.setEntrada(entrada);
        this.setSaida(saida);
    }


    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
        if (entrada.length() > 0) {
            this.horaEntrada = Integer.parseInt(entrada.substring(0,2));
            this.minutoEntrada = Integer.parseInt(entrada.substring(3,5));
            this.minutosEntrada = this.horaEntrada * 60 + this.minutoEntrada;
            //System.out.println("** minutos entrada: " + this.minutosEntrada);
        }
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
        if (saida.length() > 0) {
            this.horaSaida = Integer.parseInt(saida.substring(0,2));
            this.minutoSaida = Integer.parseInt(saida.substring(3,5));
            this.minutosSaida = this.horaSaida * 60 + this.minutoSaida;
        }
    }

    public String toString() {
        return this.entrada + " " + this.saida;
    }

    @Override
    public int compareTo(Periodo o) {
        return o.toString().compareToIgnoreCase(this.toString());
    }
}