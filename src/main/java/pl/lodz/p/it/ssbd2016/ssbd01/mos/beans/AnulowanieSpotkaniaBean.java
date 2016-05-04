package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Kamil Rogowski
 *         Bean związany z MOS.3, anulowanie spotkań
 */
@Named
@RequestScoped
public class AnulowanieSpotkaniaBean {

    @Inject
    private SpotkanieSession spotkanieSession;

    private Ogloszenie ogloszenie;
    private Konto konto;

    /**
     * Anuluje spotkania powiązane z kontem MOS.3, Kamil Rogowski
     *
     * @param spotkanie spotkanie do anulowania
     */
    public void anulujSpotkanie(Spotkanie spotkanie) {
        spotkanieSession.anulujSpotkanie(konto, spotkanie);
    }


}