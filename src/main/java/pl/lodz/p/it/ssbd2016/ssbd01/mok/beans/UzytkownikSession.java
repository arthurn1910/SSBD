package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.IOException;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.HistoriaLogowania;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

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
    
    /**
     * MOK.16 logujemy zalogowanego użytkownika w historii logowania
     */
    @PostConstruct
    public void init() {
        login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        
        if (login != null) {
            MOKEndpoint.ustawIP(login);
        }
    }
    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
        
    /**
     * Rejestruje konto, nadając mu poziom dostępu klienta
     * @param  k  konto, które ma zostać zarejestrowane
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public void rejestrujKontoKlienta(Konto k) throws /*WyjatekSystemu,*/ NoSuchAlgorithmException, UnsupportedEncodingException, NamingException, MessagingException{
        try {
            Konto kontoRejestracja = new Konto();
            kontoRejestracja.setLogin(k.getLogin());
            kontoRejestracja.setHaslo(k.getHaslo());
            kontoRejestracja.setImie(k.getImie());
            kontoRejestracja.setNazwisko(k.getNazwisko());
            kontoRejestracja.setEmail(k.getEmail());
            kontoRejestracja.setDataUtworzenia(Date.from(Instant.now()));
            kontoRejestracja.setTelefon(k.getTelefon());
            MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
    }     
    
    /**
     * Rejestruje konto, nadając mu wybrane poizomy dostępu
     * @param  k  konto, które ma zostać zarejestrowane
     * @param  poziomyDostepu  poziomy dostępu, który ma mieć nowo tworzone konto
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public void utworzKonto(Konto k, List<String> poziomyDostepu) throws WyjatekSystemu, NoSuchAlgorithmException, UnsupportedEncodingException, NamingException{
        try {
            Konto kontoRejestracja = new Konto();
            kontoRejestracja.setLogin(k.getLogin());
            kontoRejestracja.setHaslo(k.getHaslo());
            kontoRejestracja.setImie(k.getImie());
            kontoRejestracja.setNazwisko(k.getNazwisko());
            kontoRejestracja.setEmail(k.getEmail());
            kontoRejestracja.setDataUtworzenia(Date.from(Instant.now()));
            kontoRejestracja.setTelefon(k.getTelefon());
        
            MOKEndpoint.utworzKonto(kontoRejestracja, poziomyDostepu);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
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
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public void odblokujKonto(Konto konto) throws WyjatekSystemu, MessagingException{
        try{
            MOKEndpoint.odblokujKonto(konto);
        }catch(Exception ex){
            this.exception=ex;
            throw ex;
        }
    }

    /**
     * Metoda blokująca konto
     * @param konto konto do zablokowania
     */
    public void zablokujKonto(Konto konto) throws WyjatekSystemu, MessagingException {
        try{
            MOKEndpoint.zablokujKonto(konto);
        }catch(Exception ex){
            this.exception=ex;
            throw ex;
        }
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
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public void pobierzKontoDoEdycji(Konto konto) throws WyjatekSystemu, IOException, ClassNotFoundException{
        try {
            setKontoEdytuj(MOKEndpoint.pobierzKontoDoEdycji(konto));
        } catch (IOException | ClassNotFoundException ex){
            this.exception=ex;
            throw ex;
        }
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     * @throws Exception 
     */
    void zapiszSwojeKontoPoEdycji() throws WyjatekSystemu{
        try {
            MOKEndpoint.zapiszSwojeKontoPoEdycji(kontoEdytuj);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public void zapiszKontoPoEdycji() throws WyjatekSystemu{
        try {
            MOKEndpoint.zapiszKontoPoEdycji(kontoEdytuj);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
    }

    /**
     * Metoda zmienia hasło obecnie zalogowanego użytkownika. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws WyjatekSystemu, NoSuchAlgorithmException, UnsupportedEncodingException{           
        try {
            MOKEndpoint.zmienMojeHaslo(noweHaslo, stareHaslo);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
    }    
    
    /**
     * Metoda zmienia hasło obecnie edytowanego konta. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public void zmienHaslo(String noweHaslo) throws WyjatekSystemu, NoSuchAlgorithmException, UnsupportedEncodingException{
        try {
            MOKEndpoint.zmienHaslo(noweHaslo);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
    }
    
    /**
     * Metoda zwraca liste kont podobnych do danego konta
     * @param konto konto zawierające kryteria wyszukania
     * @return  lista kont podobnych
     */
    List<Konto> pobierzPodobneKonta(Konto konto) {
        return MOKEndpoint.pobierzPodobneKonta(konto);
    }
    
    List<Konto> pobierzWszystkieKonta() {
        return MOKEndpoint.pobierzWszystkieKonta();
    }

    /**
     * Metoda dodająca poziom dostępu do konta
     * @param konto konto do którego należy dodać poziom dostępu
     * @param poziom nazwa poziomu dostępu
     */
    void dodajPoziomDostepu(Konto konto, String poziom) throws WyjatekSystemu, NamingException{
        try {
            MOKEndpoint.dodajPoziomDostepu(konto, poziom);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
    }
    /**
     * Metoda odłączająca poziom dostępu do konta
     * @param konto konto od którego należy odłączyć poziom dostępu
     * @param poziom nazwa poziomu dostępu
     */
    void odlaczPoziomDostepu(Konto konto, String poziom) throws WyjatekSystemu, NamingException{
        try {
            MOKEndpoint.odlaczPoziomDostepu(konto, poziom);
        } catch (Exception ex){
            this.exception=ex;
            throw ex;
        }
    }

    // Gettery i Settery
    
    public List<HistoriaLogowania> pobierzHistorieLogowanUzytkownikow(){
        return MOKEndpoint.pobierzHistorieLogowanUzytkownikow();
    }    
    
    public void setKontoEdytuj(Konto kontoEdytuj) {
        this.kontoEdytuj = kontoEdytuj;
    }

    public Konto getKontoEdytuj() throws WyjatekSystemu{
        if (kontoEdytuj == null){
            WyjatekSystemu ex=new WyjatekSystemu("blad.brakKontaDoEdycji");
            this.setException(ex);
            throw ex;
        }
            
        return kontoEdytuj;
    }
    
    public Konto getWybraneKonto() throws WyjatekSystemu{
        Konto tmp= MOKEndpoint.znajdzPoLoginie(wybraneKonto.getLogin());
        if(tmp.getId()==null){
            WyjatekSystemu ex=new WyjatekSystemu("blad.NullPointerException1");
            this.setException(ex);
            throw ex;
        }
        return tmp;
    }

    public void setWybraneKonto(Konto wybraneKonto) {
        this.wybraneKonto = wybraneKonto;
    }

    public Konto getSwojeKonto() throws WyjatekSystemu{
        try{
            wybraneKonto = MOKEndpoint.getSwojeKonto();
        }catch (WyjatekSystemu ex){
            this.exception=ex;
            throw ex;
        }
        return wybraneKonto;
    }
    
    public DataModel<Konto> getKontaDataModel() {
        return kontaDataModel;
    }

    public void setKontaDataModel(DataModel<Konto> kontaDataModel) throws WyjatekSystemu{
        try{
            this.kontaDataModel = kontaDataModel;
        } catch(Exception ex){
            WyjatekSystemu exc=new WyjatekSystemu("blad.EJBAccessExcpetion");
            this.exception=exc;
            throw exc;
        }
    }     
}
