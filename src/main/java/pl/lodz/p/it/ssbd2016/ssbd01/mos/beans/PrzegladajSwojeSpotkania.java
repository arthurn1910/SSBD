package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
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
    private Spotkanie wybraneSpotkanie;

    //na potrzeby przetestowania anulowania wybranego spotkania
    @EJB
    private SpotkanieFacadeLocal spotkanieFacade;
    /**
     * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
     * konto przez konto użytkownika obecnie zalogowanego
     */

    /**
     * Inicjalizuje listę spotkań dla swojego konta
     */
    @PostConstruct
    public void init() {
        wybraneSpotkanie = spotkanieFacade.find(1L);  //na potrzeby przetestowania anulowania wybranego spotkania
    }

    /**
     * Anuluje spotkania powiązane z kontem MOS.3, Kamil Rogowski
     */
    public void anulujSpotkanie() {
        spotkanieSession.anulujSpotkanie(wybraneSpotkanie);
    }

    public List<Spotkanie> getSpotkania() {
        return spotkania;
    }
}