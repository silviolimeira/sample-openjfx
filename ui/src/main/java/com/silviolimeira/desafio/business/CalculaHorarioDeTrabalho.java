package com.silviolimeira.desafio.business;

import com.silviolimeira.desafio.model.Periodo;
import com.silviolimeira.desafio.ui.WorkSchedule;
import com.silviolimeira.desafio.ui.WorkScheduleReport;
import javafx.collections.ObservableList;

public class CalculaHorarioDeTrabalho {

    WorkScheduleReport periodosHoraExtra = new WorkScheduleReport();

    public CalculaHorarioDeTrabalho() {}

    public boolean testaIntersecaoPeriodos(Periodo periodo, ObservableList<Periodo> periodos) {
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

    public void diferencaHoraExtra(Periodo p, Periodo periodoHoraExtra, ObservableList<Periodo> horaExtra) {
        int pe = p.getMinutosEntrada();
        int ps = p.getMinutosSaida();
        int hei = periodoHoraExtra.getMinutosEntrada();
        int hef = periodoHoraExtra.getMinutosSaida();
        Periodo he = null;
        if (pe >= hei && ps <= hef) {
            he = new Periodo(
                    String.format("%02d:%02d", pe / 60, pe % 60),
                    String.format("%02d:%02d", ps / 60, ps % 60)
            );
            if (he.getMinutosEntrada() != he.getMinutosSaida()) {
                horaExtra.add(he);
            }
        } else if (pe < hei && (ps >= hei && ps <= hef)) {
            he = new Periodo(
                    String.format("%02d:%02d",hei / 60, hei % 60),
                    String.format("%02d:%02d", ps / 60, ps % 60)
            );
            if (he.getMinutosEntrada() != he.getMinutosSaida()) {
                horaExtra.add(he);
            }

        } else if ((pe >= hei && pe <= hef) && ps > hef) {
            he = new Periodo(
                    String.format("%02d:%02d", pe / 60, pe % 60),
                    String.format("%02d:%02d", hef / 60, hef % 60)
            );
            if (he.getMinutosEntrada() != he.getMinutosSaida()) {
                horaExtra.add(he);
            }

        } else if (pe < hei && ps > hef) {
            he = new Periodo(
                    String.format("%02d:%02d", hei / 60, hei % 60),
                    String.format("%02d:%02d", hef / 60, hef % 60)
            );
            if (he.getMinutosEntrada() != he.getMinutosSaida()) {
                horaExtra.add(he);
            }


        }
    }

    public void calculaHoraExtra(Periodo p, ObservableList<Periodo> horaExtra) {
        System.out.println("     Calcula Hora Extra - periodo: " + p.toString() + " ");

        int max = periodosHoraExtra.getTableAsList().size();
        for (int i = 0; i < max; i++) {
            Periodo phe = periodosHoraExtra.getTableAsList().get(i);

            int pe = p.getMinutosEntrada();
            int ps = p.getMinutosSaida();
            int hei = phe.getMinutosEntrada();
            int hef = phe.getMinutosSaida();
            Periodo he = null;

            System.out.println("       " + i+1 + ": P.h.e: " + phe.toString() + " ===========");
            if (pe > ps) {
                p = new Periodo("00:00", String.format("%02d:%02d", ps / 60, ps % 60));
                int pe1 = p.getMinutosEntrada();
                int ps1 = p.getMinutosSaida();
                System.out.println("          p1: " + p.toString());
                diferencaHoraExtra(p, phe, horaExtra);

                p = new Periodo(String.format("%02d:%02d", pe / 60, pe % 60), "23:59");
                int pe2 = p.getMinutosEntrada();
                int ps2 = p.getMinutosSaida();
                System.out.println("          p2: " + p.toString());
                diferencaHoraExtra(p, phe, horaExtra);

            } else {
                diferencaHoraExtra(p, phe, horaExtra);
            }


        }
    }

    public void calculaHoraExtraAtraso(WorkScheduleReport horaExtra, WorkScheduleReport atraso, WorkSchedule horarioDeTrabalho, WorkSchedule marcacoes) {

        //WorkScheduleReport periodosHoraExtra = new WorkScheduleReport();

        int maxMarcacoes = marcacoes.getTable().getItems().size();

        for (int j = 0; j < maxMarcacoes; j++) {

            Periodo marcacao = marcacoes.getTable().getItems().get(j);

            // calcula hora extra
            calculaHoraExtra(marcacao, horaExtra.getTable().getItems());

            // calcula atraso
            calculaAtraso(marcacao, horarioDeTrabalho, atraso);

        }

    }




    public void calcPeriodoHoraExtra(Periodo ht, WorkScheduleReport periodosHoraExtra) {
        int hexe = ht.getMinutosEntrada();
        int hexs = ht.getMinutosSaida();
        Periodo p1;
        Periodo p2;
        if (hexe < hexs) {
            p1 = new Periodo(
                    "00:00", String.format("%02d:%02d", hexe / 60, hexe % 60));

            p2 = new Periodo(
                    String.format("%02d:%02d", hexs / 60, hexs % 60), "23:59");
            periodosHoraExtra.getTable().getItems().add(p1);
            periodosHoraExtra.getTable().getItems().add(p2);
        } else {
            p1 = new Periodo(
                    String.format("%02d:%02d", hexs / 60, hexs % 60),
                    String.format("%02d:%02d", hexe / 60, hexe % 60));

            periodosHoraExtra.getTable().getItems().add(p1);
        }
    }

    public void calcPeriodoHoraExtra(Periodo ht1, Periodo ht2, WorkScheduleReport periodosHoraExtra) {
        int hexe0 = 0;
        int hexs0 = 0;
        int hexe1 = 0;
        int hexs1 = 0;
        int hexe2 = 0;
        int hexs2 = 0;
        Periodo p = null;
        if (ht2.getMinutosEntrada() < ht2.getMinutosSaida()) {
            hexe0 = 0;
            hexs0 = ht1.getMinutosEntrada();
            hexe1 = ht1.getMinutosSaida();
            hexs1 = ht2.getMinutosEntrada();
            hexe2 = ht2.getMinutosSaida();
            hexs2 = 23 * 60 + 59;
            p = new Periodo(
                    String.format("%02d:%02d", hexe0 / 60, hexe0 % 60),
                    String.format("%02d:%02d", hexs0 / 60, hexs0 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe1 / 60, hexe1 % 60),
                    String.format("%02d:%02d", hexs1 / 60, hexs1 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe2 / 60, hexe2 % 60),
                    String.format("%02d:%02d", hexs2 / 60, hexs2 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
        } else {
            hexe0 = ht2.getMinutosSaida();
            hexs0 = ht1.getMinutosEntrada();
            hexe1 = ht1.getMinutosSaida();
            hexs1 = ht2.getMinutosEntrada();
            p = new Periodo(
                    String.format("%02d:%02d", hexe0 / 60, hexe0 % 60),
                    String.format("%02d:%02d", hexs0 / 60, hexs0 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe1 / 60, hexe1 % 60),
                    String.format("%02d:%02d", hexs1 / 60, hexs2 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
        }
        System.out.println("Periodos Hora Extra ** : " + periodosHoraExtra.getTableAsList().toString());

    }

    public void calcPeriodoHoraExtra(Periodo ht1, Periodo ht2, Periodo ht3, WorkScheduleReport periodosHoraExtra)
    {
        Periodo p = null;
        int hexe0 = 0;
        int hexs0 = 0;
        int hexe1 = 0;
        int hexs1 = 0;
        int hexe2 = 0;
        int hexs2 = 0;
        int hexe3 = 0;
        int hexs3 = 0;

        if (ht3.getMinutosEntrada() < ht3.getMinutosSaida()) {
            hexe0 = 0;
            hexs0 = ht1.getMinutosEntrada();
            hexe1 = ht1.getMinutosSaida();
            hexs1 = ht2.getMinutosEntrada();
            hexe2 = ht2.getMinutosSaida();
            hexs2 = ht3.getMinutosEntrada();
            hexe3 = ht3.getMinutosSaida();
            hexs3 = 23 * 60 + 59;
            p = new Periodo(
                    String.format("%02d:%02d", hexe0 / 60, hexe0 % 60),
                    String.format("%02d:%02d", hexs0 / 60, hexs0 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe1 / 60, hexe1 % 60),
                    String.format("%02d:%02d", hexs1 / 60, hexs1 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe2 / 60, hexe2 % 60),
                    String.format("%02d:%02d", hexs2 / 60, hexs2 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe3 / 60, hexe3 % 60),
                    String.format("%02d:%02d", hexs3 / 60, hexs3 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
        }
        else {
            hexe0 = ht3.getMinutosSaida();
            hexs0 = ht1.getMinutosEntrada();
            hexe1 = ht1.getMinutosSaida();
            hexs1 = ht2.getMinutosEntrada();
            hexe2 = ht2.getMinutosSaida();
            hexs2 = ht3.getMinutosEntrada();
            p = new Periodo(
                    String.format("%02d:%02d", hexe0 / 60, hexe0 % 60),
                    String.format("%02d:%02d", hexs0 / 60, hexs0 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe1 / 60, hexe1 % 60),
                    String.format("%02d:%02d", hexs1 / 60, hexs1 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
            p = new Periodo(
                    String.format("%02d:%02d", hexe2 / 60, hexe2 % 60),
                    String.format("%02d:%02d", hexs2 / 60, hexs2 % 60));
            periodosHoraExtra.getTable().getItems().add(p);
        }
        System.out.println("Periodos Hora Extra: " + periodosHoraExtra.getTableAsList().toString());
    }

    public void calcPeriodosHoraExtra(ObservableList<Periodo> horarioDeTrabalho) {
        int max = 0;
        max = horarioDeTrabalho.size();

        periodosHoraExtra.getTable().getItems().clear();

        if (max == 1) {
            Periodo ht1 = horarioDeTrabalho.get(0);
            calcPeriodoHoraExtra(ht1, periodosHoraExtra);
            System.out.println("Calc periodos hora extra: 1");
        }
        if (max == 2) {
            Periodo ht1 = horarioDeTrabalho.get(0);
            Periodo ht2 = horarioDeTrabalho.get(1);
            calcPeriodoHoraExtra(ht1, ht2, periodosHoraExtra);
            System.out.println("Calc periodos hora extra: 2");
        }
        if (max == 3) {
            Periodo ht1 = horarioDeTrabalho.get(0);
            Periodo ht2 = horarioDeTrabalho.get(1);
            Periodo ht3 = horarioDeTrabalho.get(2);
            calcPeriodoHoraExtra(ht1, ht2, ht3, periodosHoraExtra);
            System.out.println("Calc periodos hora extra: 3");
        }

    }

    public void diferencaAtraso1(Periodo p, Periodo periodoHorarioDeTrabalho, WorkScheduleReport atrasos) {
        int pe = p.getMinutosEntrada();
        int ps = p.getMinutosSaida();
        Periodo ht = periodoHorarioDeTrabalho;
        int hti = periodoHorarioDeTrabalho.getMinutosEntrada();
        int htf = periodoHorarioDeTrabalho.getMinutosSaida();
        Periodo at = null;

        Periodo pht1 = new Periodo(
                "00:00", String.format("%02d:%02d", htf / 60, htf % 60));
        Periodo pht2 = new Periodo(
                String.format("%02d:%02d", hti / 60, htf % 60), "23:59");

        int phti = pht1.getMinutosEntrada();
        int phtf = pht1.getMinutosSaida();
        at = new Periodo(
                String.format("%02d:%02d", phti / 60, phti % 60),
                String.format("%02d:%02d", ps / 60, ps % 60)
        );
        atrasos.getTable().getItems().add(at);
        phti = pht2.getMinutosEntrada();
        phtf = pht2.getMinutosSaida();
        at = new Periodo(
                String.format("%02d:%02d", pe / 60, pe % 60),
                String.format("%02d:%02d", phtf / 60, phtf % 60)
        );
        atrasos.getTable().getItems().add(at);

    }

    public void diferencaAtraso2(Periodo p, Periodo periodoHorarioDeTrabalho, WorkScheduleReport atrasos) {
        int pe = p.getMinutosEntrada();
        int ps = p.getMinutosSaida();
        Periodo ht = periodoHorarioDeTrabalho;
        int hti = periodoHorarioDeTrabalho.getMinutosEntrada();
        int htf = periodoHorarioDeTrabalho.getMinutosSaida();
        Periodo at = null;

        //if (hti > htf) {
        if (pe < ps)
        {
            //**now
            Periodo pht1 = new Periodo(
                    "00:00", String.format("%02d:%02d", htf / 60, htf % 60));
            Periodo pht2 = new Periodo(
                    String.format("%02d:%02d", hti / 60, htf % 60), "23:59");

            int phti = pht1.getMinutosEntrada();
            int phtf = pht1.getMinutosSaida();
            at = new Periodo(
                    String.format("%02d:%02d", phti / 60, phti % 60),
                    String.format("%02d:%02d", pe / 60, pe % 60)
            );
            atrasos.getTable().getItems().add(at);
            phti = pht2.getMinutosEntrada();
            phtf = pht2.getMinutosSaida();
            at = new Periodo(
                    String.format("%02d:%02d", phti / 60, phti % 60),
                    String.format("%02d:%02d", phtf / 60, phtf % 60)
            );
            atrasos.getTable().getItems().add(at);


        } else {
            Periodo pht1 = new Periodo(
                    "00:00", String.format("%02d:%02d", htf / 60, htf % 60));
            Periodo pht2 = new Periodo(
                    String.format("%02d:%02d", hti / 60, htf % 60), "23:59");

            int phti = pht1.getMinutosEntrada();
            int phtf = pht1.getMinutosSaida();
            if (pe >= phti && ps <= phtf) {

                if (pe >= phti && ps < phtf) {
                    at = new Periodo(
                            String.format("%02d:%02d", pe / 60, pe % 60),
                            String.format("%02d:%02d", ps / 60, ps % 60)
                    );
                    atrasos.getTable().getItems().add(at);

                }
            } else if (pe >= phti && ps > phtf) {
                phti = pht2.getMinutosEntrada();
                phtf = pht2.getMinutosSaida();
                at = new Periodo(
                        String.format("%02d:%02d", pe / 60, pe % 60),
                        String.format("%02d:%02d", phtf / 60, phtf % 60)
                );
                atrasos.getTable().getItems().add(at);

            }

        }

        //}
//        else {
//            if (pe > hti && ps < htf) {
//                if (pe > hti) {
//                    at = new Periodo(
//                            String.format("%02d:%02d", hti / 60, hti % 60),
//                            String.format("%02d:%02d", pe / 60, pe % 60)
//                    );
//                    atrasos.add(at);
//                }
//                if (ps < htf) {
//                    at = new Periodo(
//                            String.format("%02d:%02d", ps / 60, ps % 60),
//                            String.format("%02d:%02d", htf / 60, htf % 60)
//                    );
//                    atrasos.add(at);
//                }
//            }
//        }

    }


    public void calculaAtraso(Periodo p, WorkSchedule horarioDeTrabalho, WorkScheduleReport atrasos) {
        atrasos.getTable().getItems().clear();
        System.out.println("     Calcula Atraso     - periodo: " + p.toString() + " ");

        int max = horarioDeTrabalho.getTable().getItems().size();
        for (int i = 0; i < max; i++) {
            Periodo pht =  horarioDeTrabalho.getTable().getItems().get(i);

            int pe = p.getMinutosEntrada();
            int ps = p.getMinutosSaida();
            Periodo he = null;


            System.out.println("       " + i+1 + ": P.h.t: " + pht.toString() + " ===========");
            if (pe > ps) {
                diferencaAtraso1(p, pht, atrasos);
            } else {
                diferencaAtraso2(p, pht, atrasos);
            }
        }
    }


}
