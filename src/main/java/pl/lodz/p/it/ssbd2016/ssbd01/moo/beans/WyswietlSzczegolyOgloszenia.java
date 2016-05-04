/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;

/***
 * Stowrzył Radosław Pawlaczyk
 * MOO 10 wyświetl szczegóły ogłoszenia
 * @author java
 */
@Named
@RequestScoped
public class WyswietlSzczegolyOgloszenia {
    @Inject
    private OgloszenieSession ogloszenieSession;
    private Ogloszenie ogloszenie;
    
    private Boolean aktywne;
    private int cena;
    private long id;
    private Konto kontoAgenta;
    private Konto kontoWlasciciela;
    private Nieruchomosc nieruchomosc;
    private Boolean rynekPierwotny;
    private TypOgloszenia typOgloszenia;
    private String tytul;
    private Collection<Spotkanie> spotkanieCollection;
    private Collection<Konto> kontoCollection;
    
    @PostConstruct
    private void initModel() {
        ogloszenie = ogloszenieSession.getOgloszenieDoWyswietlenia();
    }

    public OgloszenieSession getOgloszenieSession() {
        return ogloszenieSession;
    }

    public Ogloszenie getOgloszenie() {
        return ogloszenie;
    }

    public Boolean getAktywne() {
        return aktywne;
    }

    public int getCena() {
        return cena;
    }

    public long getId() {
        return id;
    }

    public Konto getKontoAgenta() {
        return kontoAgenta;
    }

    public Konto getKontoWlasciciela() {
        return kontoWlasciciela;
    }

    public Nieruchomosc getNieruchomosc() {
        return nieruchomosc;
    }

    public Boolean getRynekPierwotny() {
        return rynekPierwotny;
    }

    public TypOgloszenia getTypOgloszenia() {
        return typOgloszenia;
    }

    public String getTytul() {
        return tytul;
    }

    public Collection<Spotkanie> getSpotkanieCollection() {
        return spotkanieCollection;
    }

    public Collection<Konto> getKontoCollection() {
        return kontoCollection;
    }
    
    
    
    
    
}
