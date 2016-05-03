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
     */
    public String zapiszSwojeKontoPoEdycji() {
        try {
            uzytkownikSession.zapiszSwojeKontoPoEdycji();
            return "wyswietlSzczegolySwojegoKonta";
        } catch (NiezgodnyLogin ex) {
            Logger.getLogger(EdytujDaneBean.class.getName()).log(Level.SEVERE, null, ex);
            return "niezgodnyLogin";
        } catch (BrakKontaDoEdycji ex) {
            Logger.getLogger(EdytujDaneBean.class.getName()).log(Level.SEVERE, null, ex);
            return "brakKontaDoEdycji";
        }
    }
    
    /**
     * Handler dla przyciku potwierdź. Metoda zmienia hasło dla wybranego konta
     * i przekierowuje do szczegółów danego konta
     * @return  przekierowanie do szczegółów konta
     */
    public String zapiszKontoPoEdycji() {
        try {
            uzytkownikSession.zapiszKontoPoEdycji();
            return "wyswietlSzczegolySwojegoKonta";
        } catch (BrakKontaDoEdycji ex) {
            Logger.getLogger(EdytujDaneBean.class.getName()).log(Level.SEVERE, null, ex);
            return "brakKontaDoEdycji";
        }
    }
    
    // Gettery i Settery
    
    public Konto getKontoEdytuj() {
        return uzytkownikSession.getKontoEdytuj();
    }
}