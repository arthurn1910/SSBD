package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Obsługa zmiany danych przez admina i użytkownika
 */
@Named
@RequestScoped
public class EdytujDaneBean {

    @Inject
    private UzytkownikSession uzytkownikSession;
    
    /**
     * Handler dla przyciku potwierdź. Metoda zmienia dane dla obecnie
     * zalogowanego użytkownika i przekierowuje do szczegółów danego konta
     * @return  przekierowanie do szczegółów konta 
     */
    public String zapiszSwojeKontoPoEdycji(){
        uzytkownikSession.zapiszSwojeKontoPoEdycji();
        return "wyswietlSzczegolySwojegoKonta";
    }
    
    /**
     * Handler dla przyciku potwierdź. Metoda zmienia hasło dla wybranego konta
     * i przekierowuje do szczegółów danego konta
     * @return  przekierowanie do szczegółów konta
     */
    public String zapiszKontoPoEdycji(){
        uzytkownikSession.zapiszKontoPoEdycji();
        return "wyswietlSzczegolyKonta";
    }
    
    // Gettery i Settery
    
    public Konto getKontoEdytuj() {
        return uzytkownikSession.getKontoEdytuj();
    }
}