package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import java.util.List;

/**
 * Obsługuje widok przeglądania swoich spotkań
 */
@Named
@RequestScoped
public class PrzegladajSwojeSpotkania implements Serializable {
    @Inject
    private SpotkanieSession spotkanieSession;
    
    private List<Spotkanie> spotkania;
    
    /**
     * Inicjalizuje listę spotkań dla swojego konta
     */
    @PostConstruct
    public void init() {
        spotkania = spotkanieSession.pobierzSwojeSpotkania();
    }
    
    /**
     * Anuluje spotkania powiązane z kontem MOS.3, Kamil Rogowski
     * @param spotkanie spotkanie do anulowania
     */
    public void anulujSpotkanie(Spotkanie spotkanie) {
        spotkanieSession.anulujSpotkanie(spotkanie);
    }
    
    public List<Spotkanie> getSpotkania() {
        return spotkania;
    }
}