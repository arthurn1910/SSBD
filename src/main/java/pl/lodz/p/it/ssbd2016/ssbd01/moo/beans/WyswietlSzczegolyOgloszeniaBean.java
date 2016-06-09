package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.HistoriaLogowaniaRaportBean;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;
   
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
        try {
            ogloszenieSession.setOgloszenieDoWyswietlenia(ogloszenieSession.getOgloszenieDoWyswietlenia());
            ogloszenie = ogloszenieSession.getOgloszenieDoWyswietlenia();
        } catch (WyjatekSystemu ex) {
            Logger lg=Logger.getLogger("javax.enterprice.system.conteiner.web.faces");
            lg.log(Level.INFO, "1!!!:"+ogloszenieSession.getException().getMessage());
            ex.setMessage("blad.NullPointerException");
            lg.log(Level.INFO, "2!!!:"+ogloszenieSession.getException().getMessage());
            ogloszenieSession.setException(ex);
            lg.log(Level.INFO, "3!!!:"+ogloszenieSession.getException().getMessage());
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ",ex);
            lg.log(Level.INFO, "4!!!:"+ogloszenieSession.getException().getMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            lg.log(Level.INFO, "5!!!:"+ogloszenieSession.getException().getMessage());
            HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            lg.log(Level.INFO, "6!!!:"+ogloszenieSession.getException().getMessage());
            try {
                lg.log(Level.INFO, "7!!!:"+ogloszenieSession.getOgloszenieDoWyswietlenia());
                externalContext.redirect(origRequest.getContextPath() + "/wyjatki/wyjatekMOO.xhtml");
                lg.log(Level.INFO, "8!!!:"+ogloszenieSession.getException().getMessage());
            } catch (IOException ex1) {
                lg.log(Level.INFO, "9!!!:"+ogloszenieSession.getException().getMessage());
                Logger.getLogger(HistoriaLogowaniaRaportBean.class.getName()).log(Level.SEVERE, null, ex1);
                lg.log(Level.INFO, "10!!!:"+ogloszenieSession.getException().getMessage());
            } catch (WyjatekSystemu ex1) {
                Logger.getLogger(WyswietlSzczegolyOgloszeniaBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
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
        return "wyswietlSzczegolyOgloszenia";
    }
    
    /**
     * MOO.12
     * Handler na przycisku "Usun z ulubionych". Usuwa ogłoszenie z ulubionych
     * dla obecnie zalogowanego użytkownika(klienta).
     */
    public String usunZUlubionych() {
        ogloszenieSession.usunZUlubionych(ogloszenie);
        return "wyswietlSzczegolyOgloszenia";
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
 //       ogloszenieSession.pobierzOgloszenieDoEdycji(ogloszenie);
        ogloszenieSession.deaktywujOgloszenieDanegoUzytkownika(ogloszenie);
        return "wyswietlOgloszenia";
    }
    
    /**
     * Sprawdza czy użytkownik jest właścicielem lub agentem aktualnie otwartego ogłoszenia
     * @return
     */
    public boolean czyMojeOgloszenie() throws WyjatekSystemu
    {
        Logger lg=Logger.getLogger("javax.enterprice.system.conteiner.web.faces");
        lg.log(Level.INFO, "1!!!:");
        Ogloszenie otwarte = ogloszenieSession.getOgloszenieDoWyswietlenia();
        
        String loginKonta = ogloszenieSession.pobierzZalogowanegoUzytkownika();
        if(otwarte.getIdAgenta()!=null)
            if(otwarte.getIdWlasciciela().getLogin().equals(loginKonta) == false && otwarte.getIdAgenta().getLogin().equals(loginKonta) == false){
                return false;
        }else{
            if(otwarte.getIdWlasciciela().getLogin().equals(loginKonta) == false && otwarte.getIdAgenta().getLogin().equals(loginKonta) == false){
                return false;
            }
        }    
        return true;
    }
}
