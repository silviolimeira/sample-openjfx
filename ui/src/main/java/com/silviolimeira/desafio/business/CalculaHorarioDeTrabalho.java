package com.silviolimeira.desafio.business;

import com.silviolimeira.desafio.model.Periodo;
import javafx.collections.transformation.SortedList;

public class CalculaHorarioDeTrabalho {

    public CalculaHorarioDeTrabalho() {}

    public boolean testaIntersecaoPeriodos(Periodo periodo, SortedList<Periodo> periodos) {
        boolean cnd = true;
        int max = periodos.size();
        for (int i = 0; i < max; i++) {
            Periodo item = periodos.get(i);
            System.out.print("periodo - " + i + ": " + periodo.toString());
            System.out.print(" item: " + item.toString());
            System.out.println(" max: " + max);
            if (periodo != null) {
                if (item.getMinutosEntrada() != periodo.getMinutosEntrada() &&
                        item.getMinutosSaida() != periodo.getMinutosSaida()) {
                    if (periodo.getMinutosEntrada() >= item.getMinutosEntrada()
                            && periodo.getMinutosEntrada() <= item.getMinutosSaida()) {
                        cnd = false;
                        System.out.println("FALSE");
                    }
                    if (periodo.getMinutosSaida() >= item.getMinutosEntrada()
                            && periodo.getMinutosSaida() <= item.getMinutosSaida()) {
                        cnd = false;
                        System.out.println("FALSE");
                    }
                    System.out.println("minutosEntrada: " + periodo.getMinutosEntrada());
                    System.out.println("minutosSaida: " + periodo.getMinutosSaida());
                    System.out.println("itemEntrada: " + item.getMinutosEntrada());
                    System.out.println("itemSaida: " + item.getMinutosSaida());
                }
            }
        }
        return cnd;
    }

    public void subtracaoEntreHorarios(SortedList<Periodo> horarioTrabalho,
                                       SortedList<Periodo> marcacoesFeitas,
                                       SortedList<Periodo> horaExtra,
                                       SortedList<Periodo> atraso) {

        int max = 0;
//        max = horarioTrabalho.size();
//        System.out.println("Horario Trabalho: ### " + max);
//        for (int i = 0; i < max; i++ ) {
//            Periodo periodoHorarioTrabalho = horarioTrabalho.get(i);
//            System.out.println("i: " + i + ", " + periodoHorarioTrabalho.toString());
//        }
        max = marcacoesFeitas.size();
        System.out.println("Marcacoes Feitas: ### " + max);
        for (int i = 0; i < max; i++ ) {
            Periodo marcacaoFeita = marcacoesFeitas.get(i);
            System.out.println("i: " + i + ", " + marcacaoFeita.toString());
        }


    }


}
