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

    public void setMinutosEntrada(int minutosEntrada) {
        this.minutosEntrada = minutosEntrada;
    }

    public int getMinutosSaida() {
        return minutosSaida;
    }

    public void setMinutosSaida(int minutosSaida) {
        this.minutosSaida = minutosSaida;
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

    public Periodo(int entrada, int saida) {
//        this.entrada = entrada;
//        this.saida = saida;
        this.setEntrada(String.format("%02d:%02d", entrada / 60, entrada % 60));
        this.setSaida(String.format("%02d:%02d", saida / 60, saida % 60));
    }

    public Periodo(String periodo) {
        if (periodo.length() == 11) {
            this.setEntrada(periodo.substring(0, 5));
            this.setSaida(periodo.substring(5, 11));
        }
    }


    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        if (entrada.length() > 0) {
            this.entrada = entrada.trim();
            this.horaEntrada = Integer.parseInt(entrada.trim().substring(0,2));
            this.minutoEntrada = Integer.parseInt(entrada.trim().substring(3,5));
            this.minutosEntrada = this.horaEntrada * 60 + this.minutoEntrada;
        }
    }

    public void setEntrada(int entrada) {
        this.setEntrada(String.format("%02d:%02d", entrada / 60, entrada % 60));
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        if (saida.length() > 0) {
            this.saida = saida.trim();
            this.horaSaida = Integer.parseInt(saida.trim().substring(0,2));
            this.minutoSaida = Integer.parseInt(saida.trim().substring(3,5));
            this.minutosSaida = this.horaSaida * 60 + this.minutoSaida;
        }
    }

    public void setSaida(int saida) {
        this.setSaida(String.format("%02d:%02d", saida / 60, saida % 60));
    }

    public String toString() {
        return this.entrada + " " + this.saida;
    }

    @Override
    public int compareTo(Periodo o) {
        return o.toString().compareToIgnoreCase(this.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Periodo periodo = (Periodo) o;

        if (horaEntrada != periodo.horaEntrada) return false;
        if (minutoEntrada != periodo.minutoEntrada) return false;
        if (horaSaida != periodo.horaSaida) return false;
        if (minutoSaida != periodo.minutoSaida) return false;
        if (minutosEntrada != periodo.minutosEntrada) return false;
        if (minutosSaida != periodo.minutosSaida) return false;
        if (entrada != null ? !entrada.equals(periodo.entrada) : periodo.entrada != null) return false;
        return saida != null ? saida.equals(periodo.saida) : periodo.saida == null;
    }

    @Override
    public int hashCode() {
        int result = entrada != null ? entrada.hashCode() : 0;
        result = 31 * result + (saida != null ? saida.hashCode() : 0);
        result = 31 * result + horaEntrada;
        result = 31 * result + minutoEntrada;
        result = 31 * result + horaSaida;
        result = 31 * result + minutoSaida;
        result = 31 * result + minutosEntrada;
        result = 31 * result + minutosSaida;
        return result;
    }
}
