package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

/**
 *
 * @author Maksymilian Zgierski
 */

/*
    Stworzył: Maksymilian Zgierski
    Przypadek użycia: MOO.2 - Edytuj ogłoszenie dotyczące danego użytkownika   
*/
@Named
@RequestScoped
public class EdytujOgloszenieBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    /*
    @param ogloszenieNowe obiekt Ogloszenie o id starego ogłoszenia, ale zawierające nowe dane
    */
    public void edytujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenieNowe) {
        ogloszenieSession.edytujOgloszenieDanegoUzytkownika(ogloszenieNowe);
    }
}
