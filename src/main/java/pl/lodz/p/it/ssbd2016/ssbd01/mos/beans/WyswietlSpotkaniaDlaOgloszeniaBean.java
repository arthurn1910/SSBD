package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Obsługuje widok spotkań dla ogłoszenia
 */
@Named
@RequestScoped
public class WyswietlSpotkaniaDlaOgloszeniaBean {


    private Ogloszenie ogloszenie;
    /**
     * Lista spotkan dla ogloszenia
     */
    private List<Spotkanie> spotkania;

    @Inject
    private SpotkanieSession spotkanieSession;

    /**
     * inicjalizacja modelu danych, MOS.5, Kamil Rogowski
     */
    @PostConstruct
    public void init() {
        ogloszenie = spotkanieSession.pobierzWybraneOgloszenie();
        spotkania = spotkanieSession.pobierzSpotkaniaDlaOgloszenia(ogloszenie);
    }

    public Ogloszenie getOgloszenie() {
        return ogloszenie;
    }

    public void setOgloszenie(Ogloszenie ogloszenie) {
        this.ogloszenie = ogloszenie;
    }

    public List<Spotkanie> getSpotkania() {
        return spotkania;
    }

    public void setSpotkania(List<Spotkanie> spotkania) {
        this.spotkania = spotkania;
    }
}