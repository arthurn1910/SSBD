/**
 * Ziarno odpowiedzialne za prezentacje informacji o ogłoszeniu. Umożliwia
 * manipulowanie ogloszeniem osobom upoważnionym.
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

@Named
@RequestScoped
public class WyswietlSzczegolyOgloszenia {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private Ogloszenie ogloszenie = new Ogloszenie();
    
    
    /**
     * MOO.11
     * Handler na przycisku "Dodaj do ulubionych". Dodaje ogłoszenie do ulubionych
     * dla obecnie zalogowanego użytkownika(klienta).
     */
    public void dodajDoUlubionych() {
        ogloszenieSession.dodajDoUlubionych(ogloszenie);
    }
    
    /**
     * MOO.12
     * Handler na przycisku "Usun z ulubionych". Usuwa ogłoszenie z ulubionych
     * dla obecnie zalogowanego użytkownika(klienta).
     */
    public void usunZUlubionych() {
        ogloszenieSession.usunZUlubionych(ogloszenie);
    }
    /***
     * Stowrzył Radosław Pawlaczyk
     * MOO 10 wyświetl szczegóły ogłoszenia
     * @author java
     */
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