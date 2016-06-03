package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.Utils.ZalogowanyUzytkownik;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.*;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints.MOOEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Ziarno zarządzające sesją użytkownika. Udostępnia API dla widoku.
 */
@SessionScoped
@ManagedBean(name = "ogloszenieSession")
public class OgloszenieSession implements Serializable {
    private static final Logger logger = Logger.getLogger(OgloszenieSession.class.getName());

    @EJB
    private MOOEndpointLocal mooEndpoint;
    private Ogloszenie ogloszenieDoWyswietlenia;

    private Ogloszenie ogloszenieEdytuj;
    private Exception exception;
    private boolean czyWyswietlicPotwierdzenie;

    public void setOgloszenieDoWyswietlenia(Ogloszenie ogloszenieDoWyswietlenia) {
        this.ogloszenieDoWyswietlenia = ogloszenieDoWyswietlenia;
    }

    /**
     * MOO. 1 Dodaje ogłoszenie dla nieruchomości, Kamil Rogowski
     *
     * @param ogloszenie   ogłoszenie do dodania
     * @param nieruchomosc nieruchomość do dodania
     */
    void dodajOgloszenie(Ogloszenie ogloszenie, Nieruchomosc nieruchomosc, List<ElementWyposazeniaNieruchomosci> elem) {

        final Nieruchomosc nowaNieruchomosc = ustawNieruchomosc(nieruchomosc);
        final Ogloszenie noweOgloszenie = ustawOgloszenie(ogloszenie);
        final List<ElementWyposazeniaNieruchomosci> elementWyposazeniaNieruchomosci = new ArrayList<>(elem);
        noweOgloszenie.setNieruchomosc(nowaNieruchomosc);
        nowaNieruchomosc.setOgloszenie(noweOgloszenie);
        nowaNieruchomosc.setElementWyposazeniaNieruchomosciCollection(elementWyposazeniaNieruchomosci);
        elementWyposazeniaNieruchomosci.forEach(e -> e.getNieruchomoscWyposazona().add(nowaNieruchomosc));

        mooEndpoint.dodajOgloszenie(noweOgloszenie, nowaNieruchomosc, elementWyposazeniaNieruchomosci);
        czyWyswietlicPotwierdzenie = true;
    }


    private Ogloszenie ustawOgloszenie(Ogloszenie ogloszenie) {
        Ogloszenie noweOgloszenie = new Ogloszenie();
        noweOgloszenie.setTytul(ogloszenie.getTytul());
        noweOgloszenie.setCena(ogloszenie.getCena());
        noweOgloszenie.setRynekPierwotny(ogloszenie.getRynekPierwotny());
        noweOgloszenie.setAktywne(false);
        noweOgloszenie.setDataDodania(new Date());
        final String loginZalogowanegoUzytkownika = ZalogowanyUzytkownik.getLoginZalogowanegoUzytkownika();
        noweOgloszenie.setIdWlasciciela(mooEndpoint.getKonto(loginZalogowanegoUzytkownika));
        noweOgloszenie.setTypOgloszenia(ogloszenie.getTypOgloszenia());
        return noweOgloszenie;
    }

    private Nieruchomosc ustawNieruchomosc(Nieruchomosc nieruchomosc) {
        Nieruchomosc nowaNieruchomosc = new Nieruchomosc();
        nowaNieruchomosc.setMiejscowosc(nieruchomosc.getMiejscowosc());
        nowaNieruchomosc.setUlica(nieruchomosc.getUlica());
        nowaNieruchomosc.setRokBudowy(nieruchomosc.getRokBudowy());
        nowaNieruchomosc.setPowierzchniaNieruchomosci(nieruchomosc.getPowierzchniaNieruchomosci());
        nowaNieruchomosc.setTypNieruchomosci(nieruchomosc.getTypNieruchomosci());
        nowaNieruchomosc.setLiczbaPieter(nieruchomosc.getLiczbaPieter());
        nowaNieruchomosc.setLiczbaPokoi(nieruchomosc.getLiczbaPokoi());
        nowaNieruchomosc.setPowierzchniaDzialki(nieruchomosc.getPowierzchniaDzialki());
        nowaNieruchomosc.getElementWyposazeniaNieruchomosciCollection().addAll(nieruchomosc.getElementWyposazeniaNieruchomosciCollection());

        return nowaNieruchomosc;
    }

    /**
     * Pobiera wszystkie ogłoszenia
     *
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
     *
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
    void przydzielAgentaDoOgloszenia(Ogloszenie rowData, Konto agent) {
        mooEndpoint.przydzielAgentaDoOgloszenia(rowData, agent);
    }

    /**
     * Metoda dodaje ogloszenie do ulubionych dla obecnie zalogowanego użytkownika
     *
     * @param ogloszenie ogłoszenie, które ma być dodane
     */
    void dodajDoUlubionych(Ogloszenie ogloszenie) {
        mooEndpoint.dodajDoUlubionych(ogloszenie);
    }

    /**
     * Metoda usuwa ogloszenie z ulubionych dla obecnie zalogowanego użytkownika
     *
     * @param ogloszenie ogłoszenie, które ma być usunięte
     */
    void usunZUlubionych(Ogloszenie ogloszenie) {
        mooEndpoint.usunZUlubionych(ogloszenie);
    }

    /**
     * metoda deaktywująca ogłoszenie innego użytkownika
     *
     * @param ogloszenie, które ma zostać deaktywowane
     */
    void deaktywujOgloszenieInnegoUzytkownika(Ogloszenie ogloszenie) {
        try {
            mooEndpoint.deaktywujOgloszenieInnegoUzytkownika(ogloszenie);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    /**
     * Metoda pobierająca ogłoszenie do edycji. Zapewnia blokade optymistyczną.
     *
     * @param ogloszenie ogloszenie do edycji
     */
    void pobierzOgloszenieDoEdycji(Ogloszenie ogloszenie) throws WyjatekSystemu, IOException, ClassNotFoundException {
        setOgloszenieEdytuj(mooEndpoint.pobierzOgloszenieDoEdycji(ogloszenie));
    }

    /**
     * Metoda zapisuje zmienione ogloszenie innego uzytkownika.
     */
    public void zapiszOgloszenieInnegoUzytkownikaPoEdycji() throws Exception {
        mooEndpoint.edytujOgloszenieInnegoUzytkownika(ogloszenieEdytuj);
    }

    /**
     * metoda umożliwiająca edycje ogłoszenia innego użytkownika
     */
    void edytujOgloszenieInnegoUzytkownika() {
        try {
            mooEndpoint.edytujOgloszenieInnegoUzytkownika(ogloszenieEdytuj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Metoda wywołuje metodę zmienAgentaWOgloszeniu w MOOEndpoint przekazując jej parametr Ogloszenie
     * Stowrzył Radosław Pawlaczyk
     * MOO 7
     */
    void zmienAgentaWOgloszeniu(Ogloszenie rowData, Konto agent) {
        mooEndpoint.zmienAgentaWOgloszeniu(rowData, agent);
    }

    /**
     * Pobiera liste agentów
     *
     * @return lista agentow
     */
    List<Konto> pobierzListeAgentow() {
        return mooEndpoint.pobierzListeAgentow();
    }

    Ogloszenie getOgloszenieDoWyswietlenia() {
        Ogloszenie tmp = mooEndpoint.znajdzPoID(ogloszenieDoWyswietlenia.getId());
        if (tmp.getId() == null) {
            //WyjatekSystemu ex=new WyjatekSystemu("blad.NullPointerException");
            return null;
        }
        return tmp;
    }

    /**
     * Pobiera wszystkie typy ogłoszeń z tabel słownikowych
     *
     * @return lista kluczy
     */
    public List<TypOgloszenia> pobierzTypyOgloszen() {
        return mooEndpoint.pobierzTypyOgloszen();
    }

    /**
     * Pobiera wszystkie typy nieruchomości z tabel słownikowych
     *
     * @return lista kluczy
     */
    public List<TypNieruchomosci> pobierzTypyNieruchomosci() {

        return mooEndpoint.pobierzTypyNieruchomosci();
    }

    public List<ElementWyposazeniaNieruchomosci> pobierzElementyKategorii() {
        return mooEndpoint.pobierzElementyKategorii();
    }

    public List<KategoriaWyposazeniaNieruchomosci> pobierzKategorie() {
        return mooEndpoint.pobierzKategorie();
    }

    public Ogloszenie getOgloszenieEdytuj() {
        return ogloszenieEdytuj;
    }

    public void setOgloszenieEdytuj(Ogloszenie ogloszenieEdytuj) {
        this.ogloszenieEdytuj = ogloszenieEdytuj;
    }


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}