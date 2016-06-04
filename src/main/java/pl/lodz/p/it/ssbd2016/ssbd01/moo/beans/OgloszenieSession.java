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
import javax.faces.bean.ManagedBean;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.ElementWyposazeniaNieruchomosci;

/**
 * Ziarno zarządzające sesją użytkownika. Udostępnia API dla widoku.
 */
@SessionScoped
@ManagedBean(name = "ogloszenieSession")
public class OgloszenieSession implements Serializable {
    
    @EJB
    private MOOEndpointLocal mooEndpoint;
    private Ogloszenie ogloszenieDoWyswietlenia;
    
    private Ogloszenie ogloszenieEdytuj;
    
    private boolean czyWyswietlicPotwierdzenie;
    private List<Ogloszenie> ogloszeniaDataModel;
    
    private List<ElementWyposazeniaNieruchomosci> wyposazenieNieruchomosci;
    private List<ElementWyposazeniaNieruchomosci> mozliweWyposazenie;
    
    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    public List<ElementWyposazeniaNieruchomosci> getWyposazenieNieruchomosci() {
        return wyposazenieNieruchomosci;
    }
    public void setWyposazenieNieruchomosci(List<ElementWyposazeniaNieruchomosci> w) {
        wyposazenieNieruchomosci = w;
    }
    
    public List<ElementWyposazeniaNieruchomosci> getMozliweWyposazenie() {
        return mozliweWyposazenie;
    }
    public void setMozliweWyposazenie(List<ElementWyposazeniaNieruchomosci> w) {
        mozliweWyposazenie = w;
    }

    public boolean isCzyWyswietlicPotwierdzenie() {
        if (czyWyswietlicPotwierdzenie) {
            czyWyswietlicPotwierdzenie = false;
            return true;
        }
        return false;
    }    
    
    public void setOgloszenieDoWyswietlenia(Ogloszenie ogloszenieDoWyswietlenia) {
        this.ogloszenieDoWyswietlenia = ogloszenieDoWyswietlenia;
    }
    
    public void setOgloszeniaDataModel(List<Ogloszenie> o) {
        ogloszeniaDataModel = o;
    }
    
    public List<Ogloszenie> getOgloszeniaDataModel() {
        if (null == ogloszeniaDataModel) pobierzWszystkieOgloszenia();
        return ogloszeniaDataModel;
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
    void pobierzWszystkieOgloszenia() {
        ogloszeniaDataModel = mooEndpoint.pobierzWszytkieOgloszenia();
    }
    
    /**
     * Edytuje dane ogłoszenie
     * @throws WyjatekSystemu
     */
    void edytujOgloszenieDanegoUzytkownika() throws WyjatekSystemu {
        mooEndpoint.edytujOgloszenieDotyczaceUzytkownika(ogloszenieEdytuj);
    }
    
    /**
     * Deaktywuje ogłoszenie
     * @param ogloszenie ogłoszenie do deaktywacji
     */
    void deaktywujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenie) throws Exception {
        try{
            mooEndpoint.deaktywujOgloszenieDotyczaceUzytkownika(ogloszenie);
        }catch(Exception e){
            this.exception=e;
        }
    }

    /***
     * Metoda wywołuje metodę przydzielAgentaDoOgloszenia w MOOEndpoint przekazując jej parametr Ogloszenie
     * Stowrzył Radosław Pawlaczyk
     * MOO 6
     * MOO 7
     */
    void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent)  {
        mooEndpoint.przydzielAgentaDoOgloszenia(rowData, agent);
    }
    
    /**
     * Metoda dodaje ogloszenie do ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być dodane
     */
    void dodajDoUlubionych(Ogloszenie ogloszenie) {
        mooEndpoint.dodajDoUlubionych(ogloszenie);
        czyWyswietlicPotwierdzenie = true;
    }
    
    /**
     * Metoda usuwa ogloszenie z ulubionych dla obecnie zalogowanego użytkownika
     * @param ogloszenie ogłoszenie, które ma być usunięte
     */
    void usunZUlubionych(Ogloszenie ogloszenie) {
        mooEndpoint.usunZUlubionych(ogloszenie);
        czyWyswietlicPotwierdzenie = true;
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
        try{
            setOgloszenieEdytuj(mooEndpoint.pobierzOgloszenieDoEdycji(ogloszenie));
            Nieruchomosc n = ogloszenieEdytuj.getNieruchomosc();
            wyposazenieNieruchomosci = mooEndpoint.pobierzWyposazenieNieruchomosci(n.getId());
            mozliweWyposazenie = mooEndpoint.getWszystkieMozliweElementyWyposazeniaNieruchomosci();
            for(int i = 0; i < mozliweWyposazenie.size(); i++) {
                for(int j = 0; j < wyposazenieNieruchomosci.size(); j++) {
                    if(mozliweWyposazenie.get(i).getId().equals(wyposazenieNieruchomosci.get(j).getId()))
                        mozliweWyposazenie.remove(i);
                }
            }        
        }catch(Exception e){
            this.exception=e;
            throw e;
        }
    }
    
     /**
     * Metoda zapisuje zmienione ogloszenie innego uzytkownika.
     * @throws java.lang.Exception
     */
    public void zapiszOgloszenieInnegoUzytkownikaPoEdycji() throws Exception{
        try{
            mooEndpoint.edytujOgloszenieInnegoUzytkownika(ogloszenieEdytuj);       
        }catch(Exception e){
            this.exception=e;
            throw e;
        }
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
    
    /**
     * Pobiera liste agentów
     * @return lista agentow
     */
    List<Konto> pobierzListeAgentow() {
        return mooEndpoint.pobierzListeAgentow();
    }
    
    public Ogloszenie getOgloszenieDoWyswietlenia() throws WyjatekSystemu {
        if(ogloszenieDoWyswietlenia.getId()==null){
            WyjatekSystemu ex=new WyjatekSystemu("blad.NullPointerException", "MOO");
            WyjatekSystemu exc=new WyjatekSystemu("blad.NullPointerException",ex, "MOO");
            this.exception=exc;
            throw exc;
        }
        Ogloszenie tmp=mooEndpoint.znajdzOgloszeniePoID(ogloszenieDoWyswietlenia.getId());
        if(tmp.getId()==null){
            WyjatekSystemu ex=new WyjatekSystemu("blad.NullPointerException", "MOO");
            WyjatekSystemu exc=new WyjatekSystemu("blad.NullPointerException",ex, "MOO");
            this.exception=exc;
            throw exc;
        }
        return tmp;
    }

    public Ogloszenie getOgloszenieEdytuj() {
        return ogloszenieEdytuj;
    }

    public void setOgloszenieEdytuj(Ogloszenie ogloszenieEdytuj) {
        this.ogloszenieEdytuj = ogloszenieEdytuj;
    }
    
    /**
     * Zwraca login aktualnie zalogowanego użytkownika
     * @return login użytkownika
     */
    public String pobierzZalogowanegoUzytkownika() {
        return mooEndpoint.pobierzZalogowanegoUzytkownika();
    }

    /***
     * Funkcja zwracająca liste agentów.
     * @return 
     */
    List<Konto> getAgenci() {
        return mooEndpoint.getAgenci();
    }
    /***
     * Funkcja sprawdza czy ogłoszenie posiada danego agenta.
     * @return 
     */
    Boolean czyPosiadaAgenta(Ogloszenie ogloszenie, Konto rowData) {
        return mooEndpoint.czyPosiadaAgenta(ogloszenie, rowData);
    }
    /***
     * Funkcja sprawdza czy ogłoszenie posiada jakiegoś agenta.
     * @return 
     */
    Boolean czyPosiadaJakiegosAgenta(Ogloszenie ogloszenie) {
        return mooEndpoint.czyPosiadaJakiegosAgenta(ogloszenie);
    }
}