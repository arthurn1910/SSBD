package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Obsługuje widok spotkań dla ogłoszenia
 */
@Named
@RequestScoped
public class WyswietlSpotkaniaDlaOgloszeniaBean {

    private Ogloszenie ogloszenie;
    private List<Spotkanie> spotkania;

    @Inject
    private SpotkanieSession spotkanieSession;

    /**
     * Pobiera listę spotkań dla ogłoszenia, MOS.5, Kamil Rogowski
     */
    public void pobierzListeSpotkanDlaOgloszenia() {

        spotkania = spotkanieSession.pobierzSpotkaniaDlaOgloszenia(ogloszenie);
    }

    /**
     * inicjalizacja modelu danych
     */
    @PostConstruct
    public void init() {

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