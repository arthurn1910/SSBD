package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Obsługuje widok edycji ogłoszenia
 */
@Named
@RequestScoped
public class EdytujOgloszenieBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    /**
     * metoda odpowiadajaca za edycje ogloszenia danego uzytkownika
     */       
    public void edytujOgloszenieDanegoUzytkownika() {
        ogloszenieSession.edytujOgloszenieInnegoUzytkownika();
    }
}
