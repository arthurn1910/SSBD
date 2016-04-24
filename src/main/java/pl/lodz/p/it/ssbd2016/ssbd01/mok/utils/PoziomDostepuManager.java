/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

/**
 *
 * @author java
 */
public class PoziomDostepuManager {
    private static List<String> poziomyDostepu = dodajPoziomyDostepu();

    private PoziomDostepuManager() {        
    }
    
    private static List<String> dodajPoziomyDostepu() {
        List<String> nowePoziomy = new ArrayList<String>();
        nowePoziomy.add("ADMINISTRATOR");
        nowePoziomy.add("MENADZER");
        nowePoziomy.add("AGENT");
        nowePoziomy.add("KLIENT");
        return nowePoziomy;
    }

    public static List<String> getPoziomyDostepu() {
        return new ArrayList<String>(poziomyDostepu);
    }
       
    public static PoziomDostepu stwórzPoziomDostepu(String poziom) {
        if (!poziomyDostepu.contains(poziom)) {
            return null;
        }
        
        PoziomDostepu nowyPoziomDostepu = new PoziomDostepu();
        nowyPoziomDostepu.setPoziom(poziom);
        
        return nowyPoziomDostepu;
    }
    
    public static boolean czyMoznaDodacPoziom(Konto konto, String nowyPoziom) {
        List<String> poziomyAktywne = new ArrayList<String>();
        for (PoziomDostepu poziom:konto.getPoziomDostepuCollection()) {
            if (poziom.getAktywny()) {
                poziomyAktywne.add(poziom.getPoziom());
            }
        }
        // Jesli dany poziom juz posiadamy
        if (poziomyAktywne.contains(nowyPoziom)) {
            return false;
        }
        
        poziomyAktywne.add(nowyPoziom);
        // Jesli jestesmy adminem lub klientem - nie mozemy miec innych poziomow
        if (((poziomyAktywne.contains(poziomyDostepu.get(0)) || poziomyAktywne.contains(poziomyDostepu.get(3))) 
                && poziomyAktywne.size() > 1)) {
            return false;
        }
        
        return true;
    }
    
    public static boolean czyPosiadaPoziomDostepu(Konto konto, String poziom) {
        for (PoziomDostepu obecnyPoziom:konto.getPoziomDostepuCollection()) {
            System.out.println(obecnyPoziom.getPoziom() + " ? "+ poziom);
            if (obecnyPoziom.getPoziom().equals(poziom)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean czyPosiadaAktywnyPoziomDostepu(Konto konto, String poziom) {
        for (PoziomDostepu obecnyPoziom:konto.getPoziomDostepuCollection()) {
            System.out.println(obecnyPoziom.getPoziom() + " ? "+ poziom);
            if (obecnyPoziom.getPoziom().equals(poziom)) {
                return obecnyPoziom.getAktywny();
            }
        }
        return false;
    }
    
    public static PoziomDostepu pobierzPoziomDostepu(Konto konto, String poziom) {
        for (PoziomDostepu obecnyPoziom:konto.getPoziomDostepuCollection()) {
            if (obecnyPoziom.getPoziom().equals(poziom)) {
                return obecnyPoziom;
            }
        }
        return null;
    }
}
