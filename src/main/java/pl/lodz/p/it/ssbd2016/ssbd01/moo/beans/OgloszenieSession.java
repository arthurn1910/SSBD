package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import java.io.IOException;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints.MOOEndpointLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Ziarno zarządzające sesją użytkownika. Udostępnia API dla widoku.
 */
@SessionScoped
public class OgloszenieSession implements Serializable {
    
    @EJB
    private MOOEndpointLocal mooEndpoint;
    private Ogloszenie ogloszenieDoWyswietlenia;
    
    private Ogloszenie ogloszenieEdytuj;

    public void setOgloszenieDoWyswietlenia(Ogloszenie ogloszenieDoWyswietlenia) {
        this.ogloszenieDoWyswietlenia = ogloszenieDoWyswietlenia;
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

    /**
     * Pobiera wszystkie ogłoszenia
     * @return lista ogłoszeń
     */
    List<Ogloszenie> pobierzWszystkieOgloszenia() {
        return mooEndpoint.pobierzWszytkieOgloszenia();
    }
    
    /**
     * Edytuje dane ogłoszenie
     */
    void edytujOgloszenieDanegoUzytkownika() throws Exception {
        mooEndpoint.edytujOgloszenieDotyczaceUzytkownika(ogloszenieEdytuj);
    }
    
    /**
     * Deaktywuje ogłoszenie
     * @param ogloszenie ogłoszenie do deaktywacji
     */
    void deaktywujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenie) throws Exception {
        mooEndpoint.deaktywujOgloszenieDotyczaceUzytkownika(ogloszenie);
    }

    /***
     * Metoda wywołuje metodę przydzielAgentaDoOgloszenia w MOOEndpoint przekazując jej parametr Ogloszenie
     * Stowrzył Radosław Pawlaczyk
     * MOO 6
     */
    void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent){
        mooEndpoint.przydzielAgentaDoOgloszenia(rowData, agent);
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
    
    /**
     *   metoda deaktywująca ogłoszenie innego użytkownika
     *   @param ogloszenie, które ma zostać deaktywowane
     */
    void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) {
        try {
            mooEndpoint.deaktywujOgloszenieInnegoUzytkownika(ogloszenie);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Metoda pobierająca ogłoszenie do edycji. Zapewnia blokade optymistyczną.
     * @param ogloszenie ogloszenie do edycji
     */
    void pobierzOgloszenieDoEdycji(Ogloszenie ogloszenie) throws WyjatekSystemu, IOException, ClassNotFoundException {
        setOgloszenieEdytuj(mooEndpoint.pobierzOgloszenieDoEdycji(ogloszenie));
    }
    
     /**
     * Metoda zapisuje zmienione ogloszenie innego uzytkownika.
     */
    public void zapiszOgloszenieInnegoUzytkownikaPoEdycji() throws Exception{
        mooEndpoint.edytujOgloszenieInnegoUzytkownika(ogloszenieEdytuj);
    }
    /**
     * metoda umożliwiająca edycje ogłoszenia innego użytkownika
     */    
    void edytujOgloszenieInnegoUzytkownika() {
        try {
            mooEndpoint.edytujOgloszenieInnegoUzytkownika(ogloszenieEdytuj);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /***
     * Metoda wywołuje metodę zmienAgentaWOgloszeniu w MOOEndpoint przekazując jej parametr Ogloszenie
     * Stowrzył Radosław Pawlaczyk
     * MOO 7
     */
    void zmienAgentaWOgloszeniu(Ogloszenie rowData, Konto agent){
        mooEndpoint.zmienAgentaWOgloszeniu(rowData, agent);
    }  
    
    /**
     * Pobiera liste agentów
     * @return lista agentow
     */
    List<Konto> pobierzListeAgentow() {
        return mooEndpoint.pobierzListeAgentow();
    }
    
    public Ogloszenie getOgloszenieDoWyswietlenia() {
        Ogloszenie tmp=mooEndpoint.znajdzPoID(ogloszenieDoWyswietlenia.getId());
        if(tmp.getId()==null){
            //WyjatekSystemu ex=new WyjatekSystemu("blad.NullPointerException");
            return null;
        }
        return tmp;
    }

    public Ogloszenie getOgloszenieEdytuj() {
        return ogloszenieEdytuj;
    }

    public void setOgloszenieEdytuj(Ogloszenie ogloszenieEdytuj) {
        this.ogloszenieEdytuj = ogloszenieEdytuj;
    }
}