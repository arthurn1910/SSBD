/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mosPU;

/**
 *
 * @author java
 */
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.Date;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints.MOSEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

@ManagedBean(name = "dodawanieSpotkania")
@SessionScoped
public class DodawanieSpotkania implements Serializable {
    @Inject
    private MOSEndpointLocal mosEndpoint;
    
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

    public void dodajSpotkanie() {
        System.out.println("Dodawanie spotkania:");
        Konto konto = mosEndpoint.pobierzPierwszeKonto();
        
        Ogloszenie ogloszenie = mosEndpoint.pobierzPierwszeOgloszenie();
        Spotkanie spotkanie = new Spotkanie();
        spotkanie.setDataSpotkania(new Date(Integer.parseInt(rok) - 1900, Integer.parseInt(miesiac), Integer.parseInt(dzien)));
        spotkanie.setDlugoscSpotkania(Integer.parseInt(dlugosc));
        spotkanie.setIdUzytkownika(konto);
        spotkanie.setIdOgloszenia(ogloszenie);
        mosEndpoint.dodajSpotkanie(spotkanie);
        
        konto.getSpotkanieCollection().add(spotkanie);
        ogloszenie.getSpotkanieCollection().add(spotkanie);
        
    }
}