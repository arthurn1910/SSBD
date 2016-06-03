package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
   
/**
 * Ziarno odpowiedzialne za prezentacje informacji o ogłoszeniu. Umożliwia
 * manipulowanie ogloszeniem osobom upoważnionym.
 */
@Named
@RequestScoped
public class WyswietlSzczegolyOgloszeniaBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private Ogloszenie ogloszenie; 
    List<Konto> listaAgentów;   
    
    /**
     * Pobiera wybrane ogłoszenie
     */
    @PostConstruct
    private void initModel() {
        ogloszenieSession.setOgloszenieDoWyswietlenia(ogloszenieSession.getOgloszenieDoWyswietlenia());
        ogloszenie = ogloszenieSession.getOgloszenieDoWyswietlenia();
    }

    public Ogloszenie getOgloszenie() {
        return ogloszenie;
    }
    /***
     * Funkcja sprawdzająca czy ogłoszenie posiada agenta
     * @return 
     */
    public Boolean posiadaAgenta(){
        return ogloszenieSession.czyPosiadaJakiegosAgenta(ogloszenie);
    }

    /**
     * MOO.11
     * Handler na przycisku "Dodaj do ulubionych". Dodaje ogłoszenie do ulubionych
     * dla obecnie zalogowanego użytkownika(klienta).
     */
    public String dodajDoUlubionych() {
        ogloszenieSession.dodajDoUlubionych(ogloszenie);
        return "ulubione";
    }
    
    /**
     * MOO.12
     * Handler na przycisku "Usun z ulubionych". Usuwa ogłoszenie z ulubionych
     * dla obecnie zalogowanego użytkownika(klienta).
     */
    public String usunZUlubionych() {
        ogloszenieSession.usunZUlubionych(ogloszenie);
        return "ulubione";
    }    
    
    /**
     * Metoda sprawdza czy dane ogłoszenie jest w ulubionych, na potrzeby renderowania 
     * odpowiednich przycisków.
     * @return 
     */
    public boolean czyUlubione() {
        String login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return ogloszenie.isUlubione(login);
    }
          
    /**
     * Deaktywuje ogłoszenie innego użytkownika
     * @param ogloszenie innego użytkownika, które ma zostać deaktywowane
     * Przypadek użycia - MOO5
     */
    public void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) {
        ogloszenieSession.deaktywujOgloszenieInnegoUzytkownika(ogloszenie);
    }
    
    /**
     * Pobiera liste agentow
     */
    public void pobierzListAgentow() {
        listaAgentów = ogloszenieSession.pobierzListeAgentow();
    }
    
    /***
     * Metoda odpowiada za pobranie ogłoszenia do edycji
     * Stowrzył Maksymilian Zgierski
     * MOO.2
     * @return Łańcuch przekierowuje do widoku z edycją danych ogłoszenia
     */
    public String edytujOgloszenieDanegoUzytkownika() throws Exception {
        ogloszenieSession.pobierzOgloszenieDoEdycji(ogloszenie);
        return "edytujOgloszenieDanegoUzytkownika";
    }
    
      /***
     * Metoda odpowiada za pobranie ogłoszenia innego użytkownika do edycji
     * Stowrzył Tomasz Jędrzejewski
     * MOO.3
     * @return Łańcuch przekierowuje do widoku z edycją danych ogłoszenia
     */
    public String edytujOgloszenieInnegoUzytkownika() throws Exception {
        ogloszenieSession.pobierzOgloszenieDoEdycji(ogloszenie);
        return "edytujOgloszenieDanegoUzytkownika";
    }
    
    /**
     * Stworzył: Maksymilian Zgierski
     * Przypadek użycia: MOO.4 - Deaktywuj ogłoszenie dotyczące danego użytkownika 
     */
    public String deaktywujOgloszenieDanegoUzytkownika() throws Exception {
        ogloszenieSession.pobierzOgloszenieDoEdycji(ogloszenie);
        ogloszenieSession.deaktywujOgloszenieDanegoUzytkownika(ogloszenie);
        return "wyswietlOgloszenia";
    }
    
    /**
     * Sprawdza czy użytkownik jest właścicielem lub agentem aktualnie otwartego ogłoszenia
     * @return
     */
    public boolean czyMojeOgloszenie()
    {
        Ogloszenie otwarte = ogloszenieSession.getOgloszenieDoWyswietlenia();
        String loginKonta = ogloszenieSession.pobierzZalogowanegoUzytkownika();
        if(otwarte.getIdWlasciciela().getLogin().equals(loginKonta) == false && otwarte.getIdAgenta().getLogin().equals(loginKonta) == false)
            return false;
        return true;
    }
}
