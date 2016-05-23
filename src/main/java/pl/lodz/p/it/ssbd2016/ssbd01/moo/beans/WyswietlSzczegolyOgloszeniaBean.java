package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
        ogloszenie = ogloszenieSession.getOgloszenieDoWyswietlenia();
    }

    public Ogloszenie getOgloszenie() {
        return ogloszenie;
    }

    /**
     * MOO.11
     * Handler na przycisku "Dodaj do ulubionych". Dodaje ogłoszenie do ulubionych
     * dla obecnie zalogowanego użytkownika(klienta).
     */
    public void dodajDoUlubionych() {
        ogloszenieSession.dodajDoUlubionych(ogloszenie);
    }
    
    /**
     * MOO.12
     * Handler na przycisku "Usun z ulubionych". Usuwa ogłoszenie z ulubionych
     * dla obecnie zalogowanego użytkownika(klienta).
     */
    public void usunZUlubionych() {
        ogloszenieSession.usunZUlubionych(ogloszenie);
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
     * Metoda wywołuje metodę zmienAgentaWOgloszeniu w OgloszenieSession przekazując jej parametry Ogloszenie, Konto
     * Stowrzył Radosław Pawlaczyk
     * MOO 7
     */
    public void zmienAgentaWOgloszeniu(){
        pobierzListAgentow();
        Konto k=new Konto();
        ogloszenieSession.zmienAgentaWOgloszeniu(ogloszenie, k);
    }
    
    /***
     * Metoda wywołuje metodę przydzielAgentaDoOgloszenia w OgloszenieSession przekazując jej parametry Ogloszenie, Konto
     * Stowrzył Radosław Pawlaczyk
     * MOO 6
     */
    void przydzielAgentaDoOgloszenia(){
        pobierzListAgentow();
        Konto agent=new Konto();
        ogloszenieSession.przydzielAgentaDoOgloszenia(ogloszenie, agent);
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
        ogloszenieSession.deaktywujOgloszenieDanegoUzytkownika(ogloszenie);
        return "";
    }
}
