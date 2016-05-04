package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints.MOOEndpointLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Ziarno zarządzające sesją użytkownika. Udostępnia API dla widoku.
 */
@SessionScoped
public class OgloszenieSession implements Serializable {
    
    @EJB
    private MOOEndpointLocal mooEndpoint;

    public OgloszenieSession() {
    }
    /**
     * MOO. 1 Dodaje ogłoszenie dla nieruchomości, Kamil Rogowski
     *
     * @param ogloszenie   ogłoszenie do dodania
     * @param nieruchomosc nieruchomość do dodania
     */
    void dodajOgloszenie(Ogloszenie ogloszenie, Nieruchomosc nieruchomosc) {
        Ogloszenie noweOgloszenie = new Ogloszenie();
        Nieruchomosc nowaNieruchomosc = new Nieruchomosc();
        
        // Wypelnij Nieruchomosc:
        nowaNieruchomosc.setMiejscowosc("Mragowo");
        nowaNieruchomosc.setUlica("Mazurska");
        nowaNieruchomosc.setRokBudowy(new Date(2000, 6, 15));
        nowaNieruchomosc.setPowierzchniaNieruchomosci(nieruchomosc.getPowierzchniaNieruchomosci());
        nowaNieruchomosc.setOgloszenie(noweOgloszenie);
        nowaNieruchomosc.setTypNieruchomosci(mooEndpoint.getTypNieruchomosci("mieszkanie"));
        nowaNieruchomosc.setLiczbaPieter(1);
        nowaNieruchomosc.setLiczbaPokoi(1);
        nowaNieruchomosc.setPowierzchniaDzialki(10);
        
        
        // Wypelnij Ogloszenie:
        noweOgloszenie.setTytul(ogloszenie.getTytul());
        noweOgloszenie.setCena(ogloszenie.getCena());
        noweOgloszenie.setRynekPierwotny(true);
        noweOgloszenie.setAktywne(false);
        noweOgloszenie.setDataDodania(new Date(2016, 1, 1));
        noweOgloszenie.setNieruchomosc(nowaNieruchomosc);
        noweOgloszenie.setIdWlasciciela(mooEndpoint.getKonto("janusz"));
        noweOgloszenie.setIdAgenta(mooEndpoint.getKonto("agent"));
        noweOgloszenie.setTypOgloszenia(mooEndpoint.getTypOgloszenia("wynajem"));
        
        mooEndpoint.dodajOgloszenie(noweOgloszenie, nowaNieruchomosc);
    }

    List<Ogloszenie> pobierzWszystkieOgloszenia() {
        return mooEndpoint.pobierzWszytkieOgloszenia();
    }

    void aktywujOgloszenie(Ogloszenie rowData) {
        mooEndpoint.aktywujOgloszenie(rowData);
    }

    void deaktywujOgloszenie(Ogloszenie rowData) {
        mooEndpoint.deaktywujOgloszenie(rowData);
    }


    List<Ogloszenie> pobierzUlubioneOgloszenia() {
        return mooEndpoint.pobierzUlubioneOgloszenia();
    }
    
    /**
     * Metoda dodaje ogloszenie do ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być dodane
     */
    void dodajDoUlubionych(Ogloszenie ogloszenie) {
        mooEndpoint.dodajDoUlubionych(ogloszenie);
    }
    
    /**
     * Metoda usuwa ogloszenie z ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być usunięte
     */
    void usunZUlubionych(Ogloszenie ogloszenie) {
        mooEndpoint.usunZUlubionych(ogloszenie);
    }
    
    
}
