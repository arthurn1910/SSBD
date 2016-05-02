package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.HistoriaLogowania;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Ziarno zarządzające sesją użytkownika. Udostępnia API dla widoku.
 */
@SessionScoped
@ManagedBean(name = "uzytkownikSession")
public class UzytkownikSession implements Serializable {

    @EJB
    private MOKEndpointLocal MOKEndpoint;
    
    private Konto wybraneKonto;
    
    private Konto kontoEdytuj;
    
    private DataModel<Konto> kontaDataModel;

    private String login;

    @PostConstruct
    public void init() {
        login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        //po naprawniniu problemow z logowaniem zacznie zwracac login uzytkownika
        if(login == null) login = "ANONYMOUS";
        MOKEndpoint.ustawIP(login);
    }

    /**
     * Rejestruje konto, nadając mu poziom dostępu klienta
     * @param  k  konto, które ma zostać zarejestrowane
     */
    public void rejestrujKontoKlienta(Konto k) {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo(k.getHaslo());
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(Date.from(Instant.now()));
        kontoRejestracja.setTelefon(k.getTelefon());
       
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja);
    }
        
    
    /**
     * Rejestruje konto, nadając mu wybrane poizomy dostępu
     * @param  k  konto, które ma zostać zarejestrowane
     * @param  poziomyDostepu  poziomy dostępu, który ma mieć nowo tworzone konto
     */
    public void utworzKonto(Konto k, List<String> poziomyDostepu) throws Exception {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo(k.getHaslo());
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(Date.from(Instant.now()));
        kontoRejestracja.setTelefon(k.getTelefon());
        
        MOKEndpoint.utworzKonto(kontoRejestracja, poziomyDostepu);
    }
    
    /**
     * Metoda zmienia stan konta na potwierdzone
     * @param konto konto do potwierdzenia
     */
    public void potwierdzKonto(Konto konto) {
        MOKEndpoint.potwierdzKonto(konto);
    }
    
    /**
     * Metoda oblokowuje konto
     * @param konto konto do odblokowania
     */
    public void odblokujKonto(Konto konto) {
        MOKEndpoint.odblokujKonto(konto);
    }

    /**
     * Metoda blokująca konto
     * @param konto konto do zablokowania
     */
    public void zablokujKonto(Konto konto) {
        MOKEndpoint.zablokujKonto(konto);
    }

    /**
     * Metoda zwraca konto o zadanym loginie
     * @param login login konta
     * @return szukane konto
     */
    public Konto znajdzPoLoginie(String login) {
        return MOKEndpoint.znajdzPoLoginie(login);
    }

    /**
     * Metoda pobierająca konto do edycji. Zapewnia blokadę optymistyczną.
     * @param konto konto do edycji
     */
    public void pobierzKontoDoEdycji(Konto konto) {
        setKontoEdytuj(MOKEndpoint.pobierzKontoDoEdycji(konto));
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     * @throws Exception 
     */
    void zapiszSwojeKontoPoEdycji() throws Exception {
        MOKEndpoint.zapiszSwojeKontoPoEdycji(kontoEdytuj);
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     */
    public void zapiszKontoPoEdycji() {
        MOKEndpoint.zapiszKontoPoEdycji(kontoEdytuj);
    }

    /**
     * Metoda zmienia hasło obecnie zalogowanego użytkownika. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     * @throws java.lang.Exception
     */
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws Exception {           
        MOKEndpoint.zmienMojeHaslo(noweHaslo, stareHaslo);
    }    
    
    /**
     * Metoda zmienia hasło obecnie edytowanego konta. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @throws java.lang.Exception
     */
    public void zmienHaslo(String noweHaslo) throws Exception {
        MOKEndpoint.zmienHaslo(noweHaslo);
    }
    
    /**
     * Metoda zwraca liste kont podobnych do danego konta
     * @param konto konto zawierające kryteria wyszukania
     * @return  lista kont podobnych
     */
    List<Konto> pobierzPodobneKonta(Konto konto) {
        return MOKEndpoint.pobierzPodobneKonta(konto);
    }

    /**
     * Metoda dodająca poziom dostępu do konta
     * @param konto konto do którego należy dodać poziom dostępu
     * @param poziom nazwa poziomu dostępu
     * @throws Exception 
     */
    void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        MOKEndpoint.dodajPoziomDostepu(konto, poziom);
    }
    /**
     * Metoda odłączająca poziom dostępu do konta
     * @param konto konto od którego należy odłączyć poziom dostępu
     * @param poziom nazwa poziomu dostępu
     * @throws Exception 
     */
    void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        MOKEndpoint.odlaczPoziomDostepu(konto, poziom);
    }

    public List<HistoriaLogowania> pobierzHistorieLogowanUzytkownikow(){
        return MOKEndpoint.pobierzHistorieLogowanUzytkownikow();
    }
    // Gettery i Settery
        
    public void setKontoEdytuj(Konto kontoEdytuj) {
        this.kontoEdytuj = kontoEdytuj;
    }

    public Konto getKontoEdytuj() {
        return kontoEdytuj;

    }
    
    public Konto getWybraneKonto() {
        return MOKEndpoint.znajdzPoLoginie(wybraneKonto.getLogin());
    }

    public void setWybraneKonto(Konto wybraneKonto) {
        this.wybraneKonto = wybraneKonto;
    }

    Konto getSwojeKonto() {
        wybraneKonto = MOKEndpoint.getSwojeKonto();
        return wybraneKonto;
    }
    
    public DataModel<Konto> getKontaDataModel() {
        return kontaDataModel;
    }

    public void setKontaDataModel(DataModel<Konto> kontaDataModel) {
        this.kontaDataModel = kontaDataModel;
    }  
    
}
