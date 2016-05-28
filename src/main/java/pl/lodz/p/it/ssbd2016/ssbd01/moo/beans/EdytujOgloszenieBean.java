package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.ElementWyposazeniaNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;
import java.util.*;
import javax.annotation.PostConstruct;

/**
 * Obsługuje widok edycji ogłoszenia
 */
@Named
@RequestScoped
public class EdytujOgloszenieBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private List<ElementWyposazeniaNieruchomosci> wyposazenieNieruchomosci;
    
    @PostConstruct
    private void initModel() {
        try {
            wyposazenieNieruchomosci = new ArrayList(getOgloszenieEdytuj().getNieruchomosc().getElementWyposazeniaNieruchomosciCollection());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Metoda ma za zadanie zapisanie ogłoszenia po zakończeniu edycji
     * @return zwraca łańcuch, który przekierowuje do widoku wyświetlającego ogłoszenia
     * @throws Exception 
     */
    public String edytujOgloszenieDanegoUzytkownika() throws Exception{
        ogloszenieSession.edytujOgloszenieDanegoUzytkownika(wyposazenieNieruchomosci);
        return "wyswietlOgloszenia";
    }
    
    public void usunElementWyposazenia() {
        
    }
    /**
     * Metoda ma za zadanie zapisanie ogłoszenia innego użytkownika po zakończeniu edycji
     * @return zwraca łańcuch, który przekierowuje do widoku wyświetlającego ogłoszenia
     * @throws Exception 
     */
    public String edytujOgloszenieInnegoUzytkownika() throws Exception{
        ogloszenieSession.zapiszOgloszenieInnegoUzytkownikaPoEdycji();
        return "wyswietlOgloszenia";
    }
    
    public Ogloszenie getOgloszenieEdytuj() throws WyjatekSystemu {
        return ogloszenieSession.getOgloszenieEdytuj();
    }
}
