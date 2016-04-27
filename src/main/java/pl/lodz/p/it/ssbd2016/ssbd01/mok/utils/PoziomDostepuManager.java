/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

/**
 *
 * @author java
 */
public class PoziomDostepuManager {
    private static List<String> poziomyDostepu = dodajPoziomyDostepu();
    private static List<List<String>> poprawneKombinacjePoziomowDostepu = dodajPoprawneKombinacjePoziomowDostepu();

    private PoziomDostepuManager() {        
    }
    
    /**
     * Metoda definiująca poziomy dostępu
     * @return      lista nazw poziomu dostepu
     */
    private static List<String> dodajPoziomyDostepu() {
        List<String> nowePoziomy;
        String poziomyDostepuDoParsowania = null;
        
        try {
            Context ctx = new InitialContext();
            poziomyDostepuDoParsowania = (String) ctx.lookup("java:comp/env/PoziomyDostepu");
        } catch (NamingException ex) {
            Logger.getLogger(PoziomDostepuManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        nowePoziomy = new ArrayList<String>(Arrays.asList(poziomyDostepuDoParsowania.split(";")));
        
        return nowePoziomy;
    }

    private static List<List<String>> dodajPoprawneKombinacjePoziomowDostepu() {
        List<List<String>> nowePoprawneKombinacjePoziomowDostepu = new ArrayList<List<String>>();
        String kombinacjePoziomowDostepuDoParsowania = null;
        List<String> pojedynczaKombinacjaDoParsowania = null;
        
        try {
            Context ctx = new InitialContext();
            kombinacjePoziomowDostepuDoParsowania = (String) ctx.lookup("java:comp/env/PoprawneKombinacjePoziomowDostepu");
        } catch (NamingException ex) {
            Logger.getLogger(PoziomDostepuManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (String kombinacja: kombinacjePoziomowDostepuDoParsowania.split(";")) {
            nowePoprawneKombinacjePoziomowDostepu.add(new ArrayList<String>(Arrays.asList(kombinacja.split(","))));
        }
        
        return nowePoprawneKombinacjePoziomowDostepu;
    }
    
    /**
     * Metoda tworząca poziomy dostępu z określonego zbioru wartości
     * @param poziom    nazwa poziomu dostepu
     * @return          nowy obiekt o określonej nazwie
     */   
    public static PoziomDostepu stworzPoziomDostepu(String poziom) {
        if (!poziomyDostepu.contains(poziom)) {
            return null;
        }
        PoziomDostepu nowyPoziomDostepu = new PoziomDostepu();
        nowyPoziomDostepu.setPoziom(poziom);
        
        return nowyPoziomDostepu;
    }
        
    /**
     * metoda sprawdza, czy poziomy dostepu jakie chcemy dodac, nie wykluczaja sie
     * @param poziomyDostepu lista stringow, zawierajaca poziomy dostepu
     * @return 
     */
     public static boolean czyPoprawnaKombinacjaPoziomowDostepu(List<String> poziomyDostepu) {
        if (poziomyDostepu.size() == 1) {
            return true;
        } else if (poziomyDostepu.size() > 1) {
            for (List<String> kombinacja:poprawneKombinacjePoziomowDostepu) {
                System.out.println("Poziom: " + poziomyDostepu.size());
                System.out.println("Poziom: " + Arrays.toString(poziomyDostepu.toArray()));
                
                System.out.println("Kombinacja: " + kombinacja.size());
                System.out.println("Kombinacja: " + Arrays.toString(kombinacja.toArray()));
                if (poziomyDostepu.size() == kombinacja.size() && kombinacja.containsAll(poziomyDostepu)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Tworzy poziom dostepu Klient
     * @return zwraca poziom dostepu
     */
    public static PoziomDostepu stworzPoziomDostepuKlient() {
        
        PoziomDostepu nowyPoziomDostepu = new PoziomDostepu();
        nowyPoziomDostepu.setPoziom(poziomyDostepu.get(3));
        
        return nowyPoziomDostepu;
    }
}
