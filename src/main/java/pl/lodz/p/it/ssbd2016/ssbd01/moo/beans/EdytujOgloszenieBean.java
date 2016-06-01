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
    
    public List<ElementWyposazeniaNieruchomosci> getWyposazenieNieruchomosci() {
        return wyposazenieNieruchomosci;
    }
    public void setWyposazenieNieruchomosci(List<ElementWyposazeniaNieruchomosci> w) {
        wyposazenieNieruchomosci = w;
    }
    
    @PostConstruct
    private void initModel() {
        wyposazenieNieruchomosci = ogloszenieSession.getWyposazenieEdytowanejNieruchomosci();
    }
    
    /**
     * Metoda ma za zadanie zapisanie ogłoszenia po zakończeniu edycji
     * @return zwraca łańcuch, który przekierowuje do widoku wyświetlającego ogłoszenia
     * @throws Exception 
     */
    public String edytujOgloszenieDanegoUzytkownika() throws Exception{
        ogloszenieSession.getOgloszenieEdytuj().getNieruchomosc().setElementWyposazeniaNieruchomosciCollection(wyposazenieNieruchomosci);
        ogloszenieSession.edytujOgloszenieDanegoUzytkownika();
        return "wyswietlOgloszenia";
    }
    
    /**
     * Metoda usuwa element wyposażenia z kolekcji
     * @param element element do usunięcia
     */
    public void usunElementWyposazenia(ElementWyposazeniaNieruchomosci element) {
        for(int i = 0; i < wyposazenieNieruchomosci.size(); i++) {
            if(wyposazenieNieruchomosci.get(i).getId().equals(element.getId()))
                wyposazenieNieruchomosci.remove(i);
        }
    }
    
    /**
     * Metoda dodaje element wyposażenia nieruchomości do kolekcji
     * @param element 
     */
    public void dodajElementWyposazenia(ElementWyposazeniaNieruchomosci element) {
        for(int i = 0; i < wyposazenieNieruchomosci.size(); i++)
            if(wyposazenieNieruchomosci.get(i).getId().equals(element.getId()))
                return;
        wyposazenieNieruchomosci.add(element);
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
    
    /**
     * Metoda odpowiedzialna za zwracanie wszystkich możliwych elementów wyposażenia nieruchomości
     * @return list możliwych elementów wyposażenia
     */
    public List<ElementWyposazeniaNieruchomosci> getWszystkieMozliweElementyWyposazeniaNieruchomosci() {
        return ogloszenieSession.getWszystkieMozliweElementyWyposazeniaNieruchomosci();
    }
}
