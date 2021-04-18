package com.silviolimeira.desafio.model;

import javafx.collections.transformation.SortedList;

public class Periodo implements Comparable<Periodo> {

    private String entrada;
    private String saida;
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

    public boolean testaIntersecaoPeriodos(Periodo periodo, SortedList<Periodo> periodos) {
        boolean cnd = true;
        int max = periodos.size();
        for (int i = 0; i < max; i++) {
            Periodo item = periodos.get(i);
            System.out.print("periodo - " + i + ": " + periodo.toString());
            System.out.print(" item: " + item.toString());
            System.out.println(" max: " + max);
            if (periodo != null) {
                if (item.minutosEntrada != periodo.minutosEntrada &&
                    item.minutosSaida != periodo.minutosSaida) {
                    if (periodo.minutosEntrada >= item.minutosEntrada
                            && periodo.minutosEntrada <= item.minutosSaida) {
                        cnd = false;
                        System.out.println("FALSE");
                    }
                    if (periodo.minutosSaida >= item.minutosEntrada
                            && periodo.minutosSaida <= item.minutosSaida) {
                        cnd = false;
                        System.out.println("FALSE");
                    }
                    System.out.println("minutosEntrada: " + periodo.minutosEntrada);
                    System.out.println("minutosSaida: " + periodo.minutosSaida);
                    System.out.println("itemEntrada: " + item.minutosEntrada);
                    System.out.println("itemSaida: " + item.minutosSaida);
                }
            }
        }
        return cnd;
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
            System.out.println("** minutos entrada: " + this.minutosEntrada);
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