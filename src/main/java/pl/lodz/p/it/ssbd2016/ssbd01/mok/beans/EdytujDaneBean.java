package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin;

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
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin 
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji 
     */
    public String zapiszSwojeKontoPoEdycji() throws NiezgodnyLogin, BrakKontaDoEdycji {
            uzytkownikSession.zapiszSwojeKontoPoEdycji();
            return "wyswietlSzczegolySwojegoKonta";
    }
    
    /**
     * Handler dla przyciku potwierdź. Metoda zmienia hasło dla wybranego konta
     * i przekierowuje do szczegółów danego konta
     * @return  przekierowanie do szczegółów konta
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji
     */
    public String zapiszKontoPoEdycji() throws BrakKontaDoEdycji {
            uzytkownikSession.zapiszKontoPoEdycji();
            return "wyswietlSzczegolySwojegoKonta";
    }
    
    // Gettery i Settery
    
    public Konto getKontoEdytuj() {
        return uzytkownikSession.getKontoEdytuj();
    }
}