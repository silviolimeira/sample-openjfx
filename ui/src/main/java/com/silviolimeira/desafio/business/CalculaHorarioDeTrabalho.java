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

    public boolean testaIntersecaoPeriodos(Periodo periodo, ObservableList<Periodo> periodos) {
        Periodo pt;
        try {
            int max = periodos.size();
            for (int i = 0; i < max; i++) {
                Periodo periodoTrabalho = periodos.get(i);
//            System.out.print("periodo - " + i + ": " + periodo.toString());
//            System.out.print(" item: " + item.toString());
//            System.out.println(" max: " + max);
                int pte = periodoTrabalho.getMinutosEntrada();
                int pts = periodoTrabalho.getMinutosSaida();
                if (pte > pts) {
                    if (pts > 0) {
                        pt = new Periodo(
                                "00:00",
                                String.format("%02d:%02d", pts / 60, pts % 60)
                        );
                        if (intersecao(periodo,pt) > 0)
                            return false;

                    }
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

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("teste ex");
            return false;
        }
        System.out.println("teste9");
        return true;
    }

    public void calculaHoraExtraAtraso(WorkScheduleReport horaExtra, WorkScheduleReport atraso, WorkSchedule horarioDeTrabalho, WorkSchedule marcacoes) {

        //WorkScheduleReport periodosHoraExtra = new WorkScheduleReport();
        try {
            int maxMarcacoes = marcacoes.getTable().getItems().size();

            for (int j = 0; j < maxMarcacoes; j++) {

                Periodo marcacao = marcacoes.getTable().getItems().get(j);

                // calcula atraso
                atraso.getTable().getItems().clear();

                calcMarcacoes(horarioDeTrabalho.getTable().getItems(),
                        marcacoes.getTable().getItems(),
                        atraso.getTable().getItems(),
                        horaExtra.getTable().getItems());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

/////////////////////////////////////////////////////////

    public static void calculaHoraExtra(Periodo horarioTrabalho, List<Periodo> marcacoes, List<Periodo> horasExtras) {
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
            Periodo periodoExtra;

            int inters = intersecao(marcacao, horarioTrabalho);

            int e = 0;
            int s = 0;

            if ((inters & 0x01) == 0x01) {
                //System.out.println("### Marcacao: " + marcacao.toString() + " ..............." + " horarioTrabalho: " + horarioTrabalho.toString());
                if (me > he && me < hs) {
                    e = me;
                    String mas_s = String.format("%02d:%02d", mas / 60, mas % 60);
                    String he_s = String.format("%02d:%02d", he / 60, he % 60);
                    if (mas > 0 && mas > he && mas > me) e = mas;
                    if (ms < hs) {
                        periodoExtra = new Periodo(e, ms);
                    } else {
                        periodoExtra = new Periodo(e, hs);
                    }
                    if (horasExtras.indexOf(periodoExtra) == -1) {
                        horasExtras.add(periodoExtra);
                        continue ;
                    }
                }
            }
            if ((inters & 0x02) == 0x02) {
                //System.out.println("### Marcacao: " + marcacao.toString() + " ..............." + " horarioTrabalho: " + horarioTrabalho.toString());
                if (ms < hs && ms > he) {
                    s = hs;
                    String ms_s = String.format("%02d:%02d", ms / 60, ms % 60);
                    String mpe_s = String.format("%02d:%02d", mpe / 60, mpe % 60);
                    String hs_s = String.format("%02d:%02d", hs / 60, hs % 60);
                    if (mpe > 0 && mpe < hs) s = mpe;
                    //String s_s = String.format("%02d:%02d", s / 60, s % 60);
                    periodoExtra = new Periodo(he, ms);
                    //System.out.println("       -1 Atraso: " + a.toString());
                    if (horasExtras.indexOf(periodoExtra) == -1) {
                        horasExtras.add(periodoExtra);
                    }
                }
            }

        }
    }



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
                //System.out.println("### Marcacao: " + marcacao.toString() + " ..............." + " horarioTrabalho: " + horarioTrabalho.toString());

                int e = 0;
                int s = 0;
                if (me > he) {
                    e = he;
                    String mas_s = String.format("%02d:%02d", mas / 60, mas % 60);
                    String he_s = String.format("%02d:%02d", he / 60, he % 60);
                    if (mas > 0 && mas > he) e = mas;
                    a = new Periodo(e, me);
                    //System.out.println("       -1 Atraso: " + a.toString());
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

    public static void verificaPeriodosHE(List<Periodo> periodosHoraExtra, List<Periodo> marcacoes, List<Periodo> horasExtras) {
        Periodo m = null;
        Periodo phe = null;
        int max = periodosHoraExtra.size();
        for (int i = 0; i < max; i++) {
            phe = periodosHoraExtra.get(i);
            int maxm = marcacoes.size();
            boolean cnd = false;
            for (int j = 0; j < maxm; j++) {
                m = marcacoes.get(j);
                int inter = intersecao(phe, m);
                if (intersecao(phe,m) == 3) {
                    //horasExtras.add(phe);
                    cnd = true;
                }
            }
            if (cnd == true) {
                horasExtras.add(phe);
            }
        }

        Periodo a1 = null;
        Periodo a2 = null;
        max = horasExtras.size();
        int i = 0;
        for (i = 0; i < max; i++) {
            a1 = horasExtras.get(i);
            if (a1.getMinutosEntrada() == 0) {
                break;
            }
            a1 = null;
        }
        if (a1 != null) {

            Periodo p = null;
            max = horasExtras.size();
            for (int j = 0; j < max; j++) {
                a2 = horasExtras.get(j);
                if (a2.getMinutosSaida() == (23 * 60 + 59)) {
                    p = new Periodo(a2.getMinutosEntrada(), a1.getMinutosSaida());
                    horasExtras.set(j, p);
                    if (a1.getMinutosEntrada() == 0) {
                        horasExtras.remove(i);
                    }
                    break;
                }
            }

        }

    }



    public static void verificaPeriodos(List<Periodo> horarioTrabalho, List<Periodo> marcacoes, List<Periodo> atrasos) {
        Periodo ht = null;
        Periodo m = null;
        int max = horarioTrabalho.size();
        for (int i = 0; i < max; i++) {
            ht = horarioTrabalho.get(i);
            int maxm = marcacoes.size();
            Boolean cnd = false;
            for (int j = 0; j < maxm; j++) {
                m = marcacoes.get(j);
                int inter = intersecao(m,ht);
                if (intersecao(m,ht) > 0) {
                    cnd = true;
                }
                inter = intersecao(ht, m);
                if (intersecao(ht, m) > 0) {
                    cnd = true;
                }
            }
            if (cnd == false) {
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
            a1 = null;
        }
        if (a1 != null) {

            Periodo p = null;
            max = atrasos.size();
            for (int j = 0; j < max; j++) {
                a2 = atrasos.get(j);
                if (a2.getMinutosSaida() == (23 * 60 + 59)) {
                    p = new Periodo(a2.getMinutosEntrada(), a1.getMinutosSaida());
                    atrasos.set(j, p);
                    if (a1.getMinutosEntrada() == 0) {
                        atrasos.remove(i);
                    }
                    break;
                }
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
                if (pe < 23 * 60 + 59) {
                    p = new Periodo(pe, 23 * 60 + 59);
                    periodosNorm.add(p);
                }
                if (ps > 0) {
                    p = new Periodo(0, ps);
                    periodosNorm.add(p);
                }
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

    public static void calcMarcacoes(List<Periodo> horarioDeTrabalho, List<Periodo> marcacoes, List<Periodo> atrasos, List<Periodo> horasExtras) {

        Periodo marcacao = null;
        Periodo atraso = null;

        System.out.println("");
        System.out.println("");
        System.out.println("=============== ### Calcula Marcacoes: ### ================================");
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

        int maxht = 0;
        atrasos.clear();
        // calcula atrasos
        maxht = horarioTrabalhoNorm.size();
        for (int j = 0; j < maxht; j++) {
            ht = horarioTrabalhoNorm.get(j);
            calculaAtraso(ht, marcacoesNorm, atrasos);
        }
        verificaPeriodos(horarioTrabalhoNorm, marcacoes, atrasos);
        System.out.println("** Atrasos: " + atrasos);
        System.out.println("");

//        List<Periodo> marcacoesNorm = normalizaPeriodos(marcacoes);
//        ordenaPeriodos(marcacoesNorm);
        horasExtras.clear();
        List<Periodo> periodosHoraExtra =  new ArrayList<>();
        maxht = horarioTrabalhoNorm.size();
        Periodo hta = null;
        int j = 0;
        for (j = 0; j < maxht; j++) {
            ht = horarioTrabalhoNorm.get(j);
            if (j == 0) {
                if (ht.getMinutosEntrada() > 0) {
                    p = new Periodo(0, ht.getMinutosEntrada());
                    periodosHoraExtra.add(p);
                }
            } else {
                hta = horarioTrabalhoNorm.get(j - 1);
                p = new Periodo(hta.getMinutosSaida(), ht.getMinutosEntrada());
                periodosHoraExtra.add(p);

            }
        }
        ht = horarioTrabalhoNorm.get(j - 1);
        if (ht.getHoraSaida() != 23 && ht.getMinutoSaida() != 59) {
            p = new Periodo(ht.getMinutosSaida(), 23 * 60 + 59); //
            periodosHoraExtra.add(p);
        }

        //System.out.println("Periodos Horas Extras: " + periodosHoraExtra);

        // calcula Horas Extras
        int maxphe = periodosHoraExtra.size();
        Periodo phe = null;
        for (j = 0; j < maxphe; j++) {
            phe = periodosHoraExtra.get(j);
            calculaHoraExtra(phe, marcacoesNorm, horasExtras);
        }
        verificaPeriodosHE(periodosHoraExtra, marcacoes, horasExtras);
        ordenaPeriodos(horasExtras);

        System.out.println("** Horas Extras: " + horasExtras);


    }


    public static void main(String[] args) {
        //System.out.println("Atrasos ***");

        List<Periodo> horarioDeTrabalho = new ArrayList<>();
        List<Periodo> marcacoes = new ArrayList<>();
        List<Periodo> atrasos = new ArrayList<>();
        List<Periodo> horasExtras = new ArrayList<>();


//        {
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "06:00"));
//            marcacoes.add(new Periodo("21:00", "05:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "06:00"));
//            marcacoes.add(new Periodo("22:00", "01:00"));
//            marcacoes.add(new Periodo("02:00", "06:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "06:00"));
//            marcacoes.add(new Periodo("20:00", "22:00"));
//            marcacoes.add(new Periodo("06:00", "07:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//
//        }

//        {
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("07:00", "11:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("08:00", "11:00"));
//            marcacoes.add(new Periodo("14:00", "17:00"));
//            marcacoes.add(new Periodo("20:00", "21:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("06:00", "10:00"));
//            marcacoes.add(new Periodo("12:00", "16:00"));
//            marcacoes.add(new Periodo("18:00", "20:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//
//        }

//        {
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("07:00", "11:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("08:00", "13:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("08:00", "09:30"));
//            marcacoes.add(new Periodo("10:30", "12:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("06:00", "08:00"));
//            marcacoes.add(new Periodo("12:00", "13:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("06:00", "07:30"));
//            marcacoes.add(new Periodo("08:15", "10:00"));
//            marcacoes.add(new Periodo("10:10", "11:35"));
//            marcacoes.add(new Periodo("11:50", "12:45"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//        }



//        {
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "01:00"));
//            horarioDeTrabalho.add(new Periodo("02:00", "06:00"));
//            marcacoes.add(new Periodo("22:00", "01:00"));
//            marcacoes.add(new Periodo("02:00", "06:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "01:00"));
//            horarioDeTrabalho.add(new Periodo("02:00", "06:00"));
//            marcacoes.add(new Periodo("21:00", "00:00"));
//            marcacoes.add(new Periodo("01:00", "05:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//        }

//        {
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("14:00", "18:00"));
//            marcacoes.add(new Periodo("07:00", "11:00"));
//            marcacoes.add(new Periodo("13:00", "17:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("14:00", "18:00"));
//            marcacoes.add(new Periodo("09:00", "13:00"));
//            marcacoes.add(new Periodo("15:00", "19:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("14:00", "18:00"));
//            marcacoes.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("19:00", "21:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//        }

//        {
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("07:00", "11:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("08:00", "11:00"));
//            marcacoes.add(new Periodo("14:00", "17:00"));
//            marcacoes.add(new Periodo("20:00", "21:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("06:00", "10:00"));
//            marcacoes.add(new Periodo("12:00", "16:00"));
//            marcacoes.add(new Periodo("18:00", "20:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//        }


        // no email
//        {
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("13:30", "17:30"));
//            marcacoes.add(new Periodo("06:00", "20:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("13:30", "17:30"));
//            marcacoes.add(new Periodo("07:00", "12:30"));
//            marcacoes.add(new Periodo("14:00", "17:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);
//
//
//        }
        {
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "05:00"));
//            marcacoes.add(new Periodo("21:00", "04:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);

//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "05:00"));
//            marcacoes.add(new Periodo("03:00", "07:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);

//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "05:00"));
//            marcacoes.add(new Periodo("21:00", "04:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);

//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "05:00"));
//            marcacoes.add(new Periodo("03:00", "07:00"));
//            calcAtrasoMarcacoes(horarioDeTrabalho,marcacoes,atrasos);


            // do email
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("13:30", "17:30"));
//            marcacoes.add(new Periodo("06:00", "20:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("13:30", "17:30"));
//            marcacoes.add(new Periodo("07:00", "12:30"));
//            marcacoes.add(new Periodo("14:00", "17:00"));
//            calcMarcacoes(horarioDeTrabalho, marcacoes, atrasos, horasExtras);

//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "05:00"));
//            marcacoes.add(new Periodo("21:00", "04:00"));
//            calcMarcacoes(horarioDeTrabalho, marcacoes, atrasos, horasExtras);

//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "05:00"));
//            marcacoes.add(new Periodo("03:00", "07:00"));
//            calcMarcacoes(horarioDeTrabalho, marcacoes, atrasos, horasExtras);

            //da planilha

//            // simulação 1
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("07:00", "11:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("08:00", "13:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("08:00", "09:30"));
//            marcacoes.add(new Periodo("10:30", "12:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("06:00", "08:00"));
//            marcacoes.add(new Periodo("12:00", "13:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("06:00", "07:30"));
//            marcacoes.add(new Periodo("08:15", "10:00"));
//            marcacoes.add(new Periodo("10:10", "11:35"));
//            marcacoes.add(new Periodo("11:50", "12:45"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

//            // simulação 2
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("14:00", "18:00"));
//            marcacoes.add(new Periodo("07:00", "11:00"));
//            marcacoes.add(new Periodo("13:00", "17:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("14:00", "18:00"));
//            marcacoes.add(new Periodo("09:00", "13:00"));
//            marcacoes.add(new Periodo("15:00", "19:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("08:00", "12:00"));
//            horarioDeTrabalho.add(new Periodo("14:00", "18:00"));
//            marcacoes.add(new Periodo("08:00", "12:00"));
//            marcacoes.add(new Periodo("19:00", "21:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

//            // simulação 3
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("07:00", "11:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("08:00", "11:00"));
//            marcacoes.add(new Periodo("14:00", "17:00"));
//            marcacoes.add(new Periodo("20:00", "21:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("07:00", "11:00"));
//            horarioDeTrabalho.add(new Periodo("13:00", "17:00"));
//            horarioDeTrabalho.add(new Periodo("19:00", "21:00"));
//            marcacoes.add(new Periodo("06:00", "10:00"));
//            marcacoes.add(new Periodo("12:00", "16:00"));
//            marcacoes.add(new Periodo("18:00", "20:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

            // simulação 4
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "06:00"));
//            marcacoes.add(new Periodo("21:00", "05:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "06:00"));
//            marcacoes.add(new Periodo("22:00", "01:00"));
//            marcacoes.add(new Periodo("02:00", "06:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

            horarioDeTrabalho.clear();
            marcacoes.clear();
            horarioDeTrabalho.add(new Periodo("22:00", "06:00"));
            marcacoes.add(new Periodo("20:00", "22:00"));
            marcacoes.add(new Periodo("06:00", "07:00"));
            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);
//
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "06:00"));
//            marcacoes.add(new Periodo("20:00", "01:00"));
//            marcacoes.add(new Periodo("02:00", "07:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

            // simulação 5
//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "01:00"));
//            horarioDeTrabalho.add(new Periodo("02:00", "06:00"));
//            marcacoes.add(new Periodo("22:00", "01:00"));
//            marcacoes.add(new Periodo("02:00", "06:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

//            horarioDeTrabalho.clear();
//            marcacoes.clear();
//            horarioDeTrabalho.add(new Periodo("22:00", "01:00"));
//            horarioDeTrabalho.add(new Periodo("02:00", "06:00"));
//            marcacoes.add(new Periodo("21:00", "00:00"));
//            marcacoes.add(new Periodo("01:00", "05:00"));
//            calcMarcacoes(horarioDeTrabalho,marcacoes,atrasos, horasExtras);

        }



    }

}
