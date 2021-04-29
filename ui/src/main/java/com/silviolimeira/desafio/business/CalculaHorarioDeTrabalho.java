package com.silviolimeira.desafio.business;

import com.silviolimeira.desafio.model.Periodo;
import com.silviolimeira.desafio.ui.WorkSchedule;
import com.silviolimeira.desafio.ui.WorkScheduleReport;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculaHorarioDeTrabalho {

    WorkScheduleReport periodosHoraExtra = new WorkScheduleReport();

    public CalculaHorarioDeTrabalho() {}

    public static void calculaAtraso(Periodo horarioTrabalho, List<Periodo> marcacoes, List<Periodo> atrasos) {
        int max = marcacoes.size();
        if (max == 0) return ;
        Periodo marcacao;
        Periodo pm;
        Periodo ma = null;
        int mpe = 0;
        int mas = 0;
        for (int i = 0; i < max; i++) {
            marcacao = marcacoes.get(i);
            mas = -1;
            if (i - 1 >= 0) {
                ma = marcacoes.get(i - 1);
                mas = marcacoes.get(i - 1).getMinutosSaida();
            }
            mpe = -1;
            if (i + 1 < max) {
                pm = marcacoes.get(i + 1);
                mpe = marcacoes.get(i + 1).getMinutosEntrada();
            }

            int me = marcacao.getMinutosEntrada();
            int ms = marcacao.getMinutosSaida();
            int he = horarioTrabalho.getMinutosEntrada();
            int hs = horarioTrabalho.getMinutosSaida();
            Periodo a;
            int inters = intersecao(marcacao, horarioTrabalho);
            if (intersecao(marcacao, horarioTrabalho) > 0) {
                //System.out.println("### Calcula atraso ###");
                System.out.println("### Marcacao: " + marcacao.toString() + " ..............." + " horarioTrabalho: " + horarioTrabalho.toString());


                int e = 0;
                int s = 0;
                if (me > he) {
                    e = he;
                    String mas_s = String.format("%02d:%02d", mas / 60, mas % 60);
                    String he_s = String.format("%02d:%02d", he / 60, he % 60);
                    if (mas > 0 && mas > he) e = mas;
                    a = new Periodo(e, me);
                    System.out.println("       -1 Atraso: " + a.toString());
                    if (atrasos.indexOf(a) == -1) {
                        atrasos.add(a);
                    }
                }
                if (ms < hs) {
                    s = hs;
                    //String ms_s = String.format("%02d:%02d", ms / 60, ms % 60);
                    //String mpe_s = String.format("%02d:%02d", mpe / 60, mpe % 60);
                    //String hs_s = String.format("%02d:%02d", hs / 60, hs % 60);
                    if (mpe > 0 && mpe < hs) s = mpe;
                    //String s_s = String.format("%02d:%02d", s / 60, s % 60);
                    a = new Periodo(ms, s);
                    //System.out.println("       -1 Atraso: " + a.toString());
                    if (atrasos.indexOf(a) == -1) {
                        atrasos.add(a);
                    }
                }
            }
        }
    }

    public static boolean intersecao(int hr, int mn, Periodo contido) {
        int ce = contido.getMinutosEntrada();
        int cs = contido.getMinutosSaida();
        int hora = hr * 60 + mn;
        if (ce < cs) {
            if (hora >= ce && hora <= cs) {
                return true;
            }
        } else {
            if (hora >= ce && hora <= 23 * 60 + 59) {
                return true;
            }
            if (hora >= 0 && hora <= cs) {
                return true;
            }
        }
        return false;
    }


    public static int intersecao(Periodo periodo, Periodo contido) {
        int cnd = 0;
        int h = periodo.getMinutosEntrada() / 60;
        int m = periodo.getMinutosEntrada() % 60;
        if (intersecao(h, m, contido)) cnd |= 1;
        h = periodo.getMinutosSaida() / 60;
        m = periodo.getMinutosSaida() % 60;
        if (intersecao(h, m, contido)) cnd |= 2;
        return cnd;
    }

    public static void verificaPeriodos(List<Periodo> horarioTrabalho, List<Periodo> marcacoes, List<Periodo> atrasos) {
        Periodo ht = null;
        Periodo m = null;
        int max = horarioTrabalho.size();
        for (int i = 0; i < max; i++) {
            ht = horarioTrabalho.get(i);
            int maxm = marcacoes.size();
            Boolean cnd = true;
            for (int j = 0; j < maxm; j++) {
                m = marcacoes.get(j);
                int inter = intersecao(m,ht);
                if (intersecao(m,ht) > 0) {
                    cnd = false;
                }
                inter = intersecao(ht,m);
                if (intersecao(ht,m) > 0) {
                    cnd = false;
                }
            }
            if (cnd == true) {
                atrasos.add(ht);
            }
        }

        Periodo a1 = null;
        Periodo a2 = null;
        max = atrasos.size();
        int i = 0;
        for (i = 0; i < max; i++) {
            a1 = atrasos.get(i);
            if (a1.getMinutosEntrada() == 0) {
                break;
            }
        }
        if (a1 != null) {
            if (a1.getMinutosEntrada() == 0) {
                atrasos.remove(i);
            }
        }
        Periodo p = null;
        max = atrasos.size();
        i = 0;
        for (i = 0; i < max; i++) {
            a2 = atrasos.get(i);
            if (a2.getMinutosSaida() == (23 * 60 + 59)) {
                a2.setMinutosSaida(a1.getMinutosSaida());
                p = new Periodo(a2.getMinutosEntrada(), a1.getMinutosSaida());
                atrasos.set(i, p);
                break;
            }
        }

    }

    public static List<Periodo> normalizaPeriodos(List<Periodo> periodos) {
        int max = periodos.size();
        if (max == 0) return null;
        List<Periodo> periodosNorm = new ArrayList<>();
        Periodo periodo = null;
        Periodo p = null;
        for (int i = 0; i < max; i++) {
            periodo = periodos.get(i);
            int pe = periodo.getMinutosEntrada();
            int ps = periodo.getMinutosSaida();
            if (pe > ps) {
                p = new Periodo(pe, 23 * 60 + 59);
                periodosNorm.add(p);
                p = new Periodo(0, ps);
                periodosNorm.add(p);
            } else {
                periodosNorm.add(periodo);
            }
        }
        return periodosNorm;
    }


    public static void ordenaPeriodos(List<Periodo> periodos) {
        int max = periodos.size();
        if (max == 0) return;
        List<String> horarioTrabalhoAsc = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            horarioTrabalhoAsc.add(periodos.get(i).toString());
        }
        Collections.sort(horarioTrabalhoAsc);
        periodos.clear();
        for (int i = 0; i < max; i++) {
            periodos.add(new Periodo(horarioTrabalhoAsc.get(i)));
        }
    }

    public static void calcAtrasoMarcacoes(List<Periodo> horarioDeTrabalho, List<Periodo> marcacoes, List<Periodo> atrasos) {

        Periodo marcacao = null;
        Periodo atraso = null;

        System.out.println("=============== ### Calcula Atraso Marcacoes: ### =========================");
        ordenaPeriodos(horarioDeTrabalho);
        System.out.println("Horario de trabalho: " + horarioDeTrabalho);
        ordenaPeriodos(marcacoes);
        System.out.println("Marcacoes          : " + marcacoes);
        System.out.println("===========================================================================");

        List<Periodo> horarioTrabalhoNorm = normalizaPeriodos(horarioDeTrabalho);
        ordenaPeriodos(horarioTrabalhoNorm);
        System.out.println("** Horario de trabalho norm ASC: " + horarioTrabalhoNorm);
        List<Periodo> marcacoesNorm = normalizaPeriodos(marcacoes);
        ordenaPeriodos(marcacoesNorm);
        System.out.println("** Marcacoes norm ASC: " + marcacoesNorm);
        System.out.println("");



        Periodo ma = null;
        Periodo p = null;
        Periodo ht = null;

        atrasos.clear();

        int maxht = horarioTrabalhoNorm.size();
        for (int j = 0; j < maxht; j++) {
            ht = horarioTrabalhoNorm.get(j);
            //(hte > hts)
            calculaAtraso(ht, marcacoesNorm, atrasos);
            //System.out.println("atrasos: " + atrasos);
            //System.out.println("");

        }
        verificaPeriodos(horarioTrabalhoNorm, marcacoes, atrasos);


        System.out.println("** Atrasos: " + atrasos);


    }

    public boolean testaIntersecaoPeriodos(Periodo periodo, ObservableList<Periodo> periodos) {
        Periodo pt;
        int max = periodos.size();
        for (int i = 0; i < max; i++) {
            Periodo periodoTrabalho = periodos.get(i);
//            System.out.print("periodo - " + i + ": " + periodo.toString());
//            System.out.print(" item: " + item.toString());
//            System.out.println(" max: " + max);
            int pte = periodoTrabalho.getMinutosEntrada();
            int pts = periodoTrabalho.getMinutosSaida();
            if (pte > pts) {
                pt = new Periodo(
                        "00:00",
                        String.format("%02d:%02d", pts / 60, pts % 60)
                );
                if (intersecao(periodo,pt) > 0)
                    return false;
                pt = new Periodo(
                        String.format("%02d:%02d", pte / 60, pte % 60),
                        "23:59"
                );
                if (intersecao(periodo,pt) > 0)
                    return false;
            } else {
                if (intersecao(periodo,periodoTrabalho) > 0)
                    return false;
            }

        }
        return true;
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
            atraso.getTable().getItems().clear();
//            calcAtrasoMarcacoes(horarioDeTrabalho.getTable().getItems(),
//                    marcacoes.getTable().getItems(),
//                    atraso.getTable().getItems());

            calcAtrasoMarcacoes(horarioDeTrabalho.getTable().getItems(),
                    marcacoes.getTable().getItems(),
                    atraso.getTable().getItems());

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




}
