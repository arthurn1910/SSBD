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
    private boolean czyKlient = false;
    private List<Spotkanie> spotkania;

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
        spotkania = spotkanieSession.pobierzSwojeSpotkania();
    }

    /**
     * Anuluje spotkania powiązane z kontem MOS.3, Kamil Rogowski
     */
    public void anulujSpotkanie(Spotkanie wybraneSpotkanie) {
        spotkanieSession.anulujSpotkanie(wybraneSpotkanie);
    }

    public List<Spotkanie> getSpotkania() {
        return spotkania;
    }
}