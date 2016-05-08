package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Obsługuje widok dla dodawania ogłoszenia
 */
@Named
@RequestScoped
public class DodajOgloszenieBean {
        
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private final Ogloszenie ogloszenie = new Ogloszenie();

    private final Nieruchomosc nieruchomosc = new Nieruchomosc();
    
    public Ogloszenie getOgloszenie() {
        return ogloszenie;
    }

    public Nieruchomosc getNieruchomosc() {
        return nieruchomosc;
    }

    /**
     * MOO. 1 Dodaje ogłoszenie dla nieruchomości, Kamil Rogowski
     *
     * @return success
     */
    public String dodajOgloszenie() {
        ogloszenieSession.dodajOgloszenie(ogloszenie, nieruchomosc);
        return "success";
    }
}
