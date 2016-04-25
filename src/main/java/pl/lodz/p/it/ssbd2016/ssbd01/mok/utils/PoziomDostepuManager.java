package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

/**
 * Klasa definiująca dostępne poziomy dostępu. Udostępnia metody tworzenia,
 * pobierania i sprawdzania poziomów dostępu.
 */
public class PoziomDostepuManager {
    private static List<String> poziomyDostepu = dodajPoziomyDostepu();

    private PoziomDostepuManager() {        
    }
    
    /**
     * Metoda definiująca poziomy dostępu
     * @return      lista nazw poziomu dostepu
     */
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
       
    /**
     * Metoda tworząca poziomy dostępu z określonego zbioru wartości
     * @param poziom    nazwa poziomu dostepu
     * @return          nowy obiekt o określonej nazwie
     */
    public static PoziomDostepu stwórzPoziomDostepu(String poziom) {
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
        if (((poziomyAktywne.contains(poziomyDostepu.get(0)) || poziomyAktywne.contains(poziomyDostepu.get(3))) 
                && poziomyAktywne.size() > 1)) {
            return false;
        }
        
        return true;
    }
    
    /**
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
