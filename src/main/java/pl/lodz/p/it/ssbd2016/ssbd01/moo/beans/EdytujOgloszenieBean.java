package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

/*
    Stworzył: Maksymilian Zgierski
    Przypadek użycia: MOO.2 - Edytuj ogłoszenie dotyczące danego użytkownika   
    Przypadek użycia: MOO.3 - Edytuj ogłoszenie dotyczące danego użytkownika   
*/
@Named
@RequestScoped
public class EdytujOgloszenieBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;

    /*
    *   metoda odpowiadajaca za edycje ogloszenia danego uzytkownika
    *   @param nowe ogloszenie
    */   
    
    public void edytujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenieNowe) throws Exception{
        ogloszenieSession.edytujOgloszenieInnegoUzytkownika(ogloszenieNowe);
    }
}
