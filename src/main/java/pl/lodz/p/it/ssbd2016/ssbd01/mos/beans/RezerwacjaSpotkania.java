package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

/**
 * Obsługuje widok rezerwacji spotkania
 */
@Named
@RequestScoped
public class RezerwacjaSpotkania implements Serializable {
    @Inject
    private SpotkanieSession spotkanieSession;
    
    Spotkanie spotkanie = new Spotkanie();
    
    private String rok;
    private String miesiac;
    private String dzien;
    private String dlugosc;
    
    /**
     * Pobiera listę spotkań dla swojego konta
     */
    public void rezerwujSpotkanie() {
        spotkanieSession.rezerwujSpotkanie(spotkanie);
    }

    public String getRok() {
        return rok;
    }
    public void setRok(String rok) {
        this.rok = rok;
    }
    
    public String getMiesiac() {
        return miesiac;
    }
    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }
    
    public String getDzien() {
        return dzien;
    }
    public void setDzien(String dzien) {
        this.dzien = dzien;
    }
    
    public String getDlugosc() {
        return dlugosc;
    }
    public void setDlugosc(String dlugosc) {
        this.dlugosc = dlugosc;
    }
}