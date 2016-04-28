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
 * Klasa definiująca dostępne poziomy dostępu. Udostępnia metody tworzenia,
 * pobierania i sprawdzania poziomów dostępu.
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

    public static List<String> getPoziomyDostepu() {
        return new ArrayList<String>(poziomyDostepu);
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
     * Metoda definiująca warunki dodania poziomów dostępu
     * @param konto         konto do którego chcemy dodać poziom
     * @param nowyPoziom    nazwa poziomu, który chcemy dodać
     * @return              boolean określający decyzję czy można dodać poziom
     */
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
        if (!czyPoprawnaKombinacjaPoziomowDostepu(poziomyAktywne)) {
            return false;
        }
        
        return true;
    }
    
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
    /*
     * Metoda sprawdzająca czy dane konto posiada poziom dostępu
     * @param konto         konto dla którego sprawdzamy poziomy dostępu
     * @param poziom        nazwa poziomu dostępu
     * @return              boolean określający czy posiadamy dany poziom dostępu
     */
    public static boolean czyPosiadaPoziomDostepu(Konto konto, String poziom) {
        for (PoziomDostepu obecnyPoziom:konto.getPoziomDostepuCollection()) {
            if (obecnyPoziom.getPoziom().equals(poziom)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metoda sprawdzająca czy dane konto posiada aktywny poziom dostępu, przed 
     * wykonaniem tej metody powiniśmy sprawdzić czy dany poziom dostępu posiadamy.
     * @param konto         konto dla którego sprawdzamy poziomy dostępu
     * @param poziom        nazwa poziomu dostępu
     * @return              boolean określający czy posiadamy aktywny poziom dostępu
     */
    public static boolean czyPosiadaAktywnyPoziomDostepu(Konto konto, String poziom) {
        for (PoziomDostepu obecnyPoziom:konto.getPoziomDostepuCollection()) {
            if (obecnyPoziom.getPoziom().equals(poziom)) {
                return obecnyPoziom.getAktywny();
            }
        }
        return false;
    }
    
    /**
     * Metoda zwracająca poziom dostępu na podstawie nazwy, przed wykonaniem
     * tej metody powiniśmy sprawdzić czy dany poziom dostępu posiadamy.
     * @param konto         konto z którego pobieramy poziom dostępu
     * @param poziom        nazwa poziomu dostępu
     * @return              obiekt poziomu dostępu
     */
    public static PoziomDostepu pobierzPoziomDostepu(Konto konto, String poziom) {
        for (PoziomDostepu obecnyPoziom:konto.getPoziomDostepuCollection()) {
            if (obecnyPoziom.getPoziom().equals(poziom)) {
                return obecnyPoziom;
            }
        }
        return null;
    }
}
