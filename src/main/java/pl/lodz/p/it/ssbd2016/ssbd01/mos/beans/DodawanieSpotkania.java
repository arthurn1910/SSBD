/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

/**
 *
 * zachowuje MOS 4. P. Stepien
 */
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

@Named
@RequestScoped
public class DodawanieSpotkania implements Serializable {
    @Inject
    private SpotkanieSession spotkanieSession;
    
    private final Ogloszenie ogloszenie = new Ogloszenie();
    private final Konto konto = new Konto();
    
    private String rok;
    private String miesiac;
    private String dzien;
    private String dlugosc;

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

    public String dodajSpotkanie() {
        spotkanieSession.dodajSpotkanie(konto, ogloszenie, rok, miesiac, dzien, dlugosc);
        return "success";
    }
}