package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints.MOSEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.SessionContext;
import javax.inject.Inject;

/**
 * Ziarno zarządzające sesją użytkownika. Udostępnia API dla widoku.
 */
@SessionScoped
@ManagedBean(name = "spotkanieSession")
public class SpotkanieSession implements Serializable {

    @EJB
    private MOSEndpointLocal mosEndpoint;
    
    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    private Ogloszenie wybraneOgloszenie;
    private Spotkanie wybraneSpotkanie;
    private Konto aktualneKonto;

    
    private boolean czyWyswietlicPotwierdzenie;
    
    /**
     * Inicjalizacja modelu danych
     */
    @PostConstruct
    public void initModel(){
    }

    /**
     * Pobiera listę spotkań dla konta, MOS. 3, Kamil Rogowski
     * MOS.4, MOS.2 P. Stepien
     * @param spotkaniaDlaKonta konto,
     * @return lista spotkań
     */
    public List<Spotkanie> pobierzSpotkania(Konto spotkaniaDlaKonta) {
        return mosEndpoint.pobierzSpotkania(spotkaniaDlaKonta);
    }
      
    /**
     * Pobiera listę spotkań dla konta, 
     * @return lista spotkań
     */
    List<Spotkanie> pobierzSwojeSpotkania() {
        Konto konto = new Konto();
        return mosEndpoint.pobierzSpotkania(konto);
    }


    /**
     * Anuluje spotkania dla konta, MOS.3, Kamil Rogowski
     *
     * @param spotkanieDoAnulowania spotkanie do anulowania
     */
    public void anulujSpotkanie(Spotkanie spotkanieDoAnulowania) {
        mosEndpoint.anulujSpotkanie(spotkanieDoAnulowania);

    }

    /**
     * Pobiera spotkania dla ogłoszenia, MOS. 5, Kamil Rogowski
     *
     * @param ogloszenie ogłoszenie
     * @return lista spotkań
     */
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {

        return mosEndpoint.pobierzSpotkaniaDlaOgloszenia(ogloszenie);
    }

    /**
     * rezerwuje spotkanie MOS 1, Radosław Pawlaczyk
     * @param spotkanie 
     */
    public void rezerwujSpotkanie(Spotkanie spotkanie) throws WyjatekSystemu {
        try{
            mosEndpoint.rezerwujSpotkanie(spotkanie);
        }catch(WyjatekSystemu ex){
            this.exception=ex;
            throw ex;
        }
    }
    
    /**
     * pobiera spotkanie do edycji MOS.2 P. Stepien
     * @return Spotkanie
     */
    public Spotkanie pobierzSpotkanieDoEdycji(Spotkanie spotkanie) throws WyjatekSystemu, IOException, ClassNotFoundException {
        try{
            return mosEndpoint.pobierzSpotkanieDoEdycji(spotkanie);
        }catch(Exception e){
            this.exception=e;
            throw e;
        }
    }
    
    /**
     * zapisuje spotkanie po edycji MOS.2 P. Stepien
     * @param spotkanie 
     */
    public void zapiszSpotkaniePoEdycji(Spotkanie spotkanie) {

        mosEndpoint.zapiszSpotkaniePoEdycji(spotkanie);
    }

    /**
     * Pobiera ogloszenie dla ktorego maja byc wyswietlone spotkania
     * @return ogloszenie
     */
    public Ogloszenie pobierzWybraneOgloszenie(){

        return wybraneOgloszenie = mosEndpoint.znajdzOgloszeniePoId(1L);
    }
    /**
     * Pobiera aktualne konto użytkownika
     * @return ogloszenie
     */

    public Konto getAktualneKonto() {        
        aktualneKonto = mosEndpoint.pobierzMojeKonto();
        return aktualneKonto;
    }
    
    /**
     * Metoda zwraca wartość określającą czy pokazać potwierdzenie operacji
     *
     * @return
     */
    public boolean isCzyWyswietlicPotwierdzenie() {
        if (czyWyswietlicPotwierdzenie) {
            czyWyswietlicPotwierdzenie = false;
            return true;
        }
        return false;
    }
}
