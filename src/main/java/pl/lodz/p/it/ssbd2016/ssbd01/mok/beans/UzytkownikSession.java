package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladDeSerializacjiObiektu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.KontoNiezgodneWczytanym;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NaruszenieUniq;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodneHasla;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje;

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
    
    private BladDeSerializacjiObiektu bladDeSerializajiObiektu;
    private BladPliku bladPliku;
    private BladPoziomDostepu bladPoziomDostepu;
    private BrakAlgorytmuKodowania brakAlgorytmuKodowania;
    private BrakKontaDoEdycji brakKontaDoEdycji;
    private NieobslugiwaneKodowanie nieobslugiwaneKodowanie;
    private NiezgodneHasla niezgodneHasla;
    private NiezgodnyLogin niezgodnyLogin;
    private PoziomDostepuNieIstnieje poziomDostepuNieIstnieje;
    private KontoNiezgodneWczytanym kontoNiezgodneWczytanym;
    private NaruszenieUniq naruszenieUniq;
    private BrakDostepu brakDostepu;

    public BladDeSerializacjiObiektu getBladDeSerializajiObiektu() {
        return bladDeSerializajiObiektu;
    }

    public BladPliku getBladPliku() {
        return bladPliku;
    }

    public BladPoziomDostepu getBladPoziomDostepu() {
        return bladPoziomDostepu;
    }

    public BrakAlgorytmuKodowania getBrakAlgorytmuKodowania() {
        return brakAlgorytmuKodowania;
    }

    public BrakKontaDoEdycji getBrakKontaDoEdycji() {
        return brakKontaDoEdycji;
    }

    public NieobslugiwaneKodowanie getNieobslugiwaneKodowanie() {
        return nieobslugiwaneKodowanie;
    }

    public NiezgodneHasla getNiezgodneHasla() {
        return niezgodneHasla;
    }

    public NiezgodnyLogin getNiezgodnyLogin() {
        return niezgodnyLogin;
    }

    public PoziomDostepuNieIstnieje getPoziomDostepuNieIstnieje() {
        return poziomDostepuNieIstnieje;
    }

    public KontoNiezgodneWczytanym getKontoNiezgodneWczytanym() {
        return kontoNiezgodneWczytanym;
    }

    public NaruszenieUniq getNaruszenieUniq() {
        return naruszenieUniq;
    }

    public BrakDostepu getBrakDostepu() {
        return brakDostepu;
    }
    
    
    

    /**
     * Rejestruje konto, nadając mu poziom dostępu klienta
     * @param  k  konto, które ma zostać zarejestrowane
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     */
    public void rejestrujKontoKlienta(Konto k) throws PoziomDostepuNieIstnieje, NieobslugiwaneKodowanie, BrakAlgorytmuKodowania, NaruszenieUniq{
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
        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            throw ex;
        } catch (NieobslugiwaneKodowanie ex) {
            this.nieobslugiwaneKodowanie=ex;
            throw ex;        
        } catch (BrakAlgorytmuKodowania ex) {
            this.brakAlgorytmuKodowania=ex;
            throw ex;  
        } catch(EJBException ex){
            NaruszenieUniq exc=new NaruszenieUniq("pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.UzytkownikSession.rejestrujKontoKlienta()");
            this.naruszenieUniq=exc;
            throw exc;
        }
    }
        
    
    /**
     * Rejestruje konto, nadając mu wybrane poizomy dostępu
     * @param  k  konto, które ma zostać zarejestrowane
     * @param  poziomyDostepu  poziomy dostępu, który ma mieć nowo tworzone konto
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     */
    public void utworzKonto(Konto k, List<String> poziomyDostepu) throws NieobslugiwaneKodowanie, BrakAlgorytmuKodowania, PoziomDostepuNieIstnieje, NaruszenieUniq{
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
        } catch (NieobslugiwaneKodowanie ex) {
            this.nieobslugiwaneKodowanie=ex;
            throw ex;  
        } catch (BrakAlgorytmuKodowania ex) {
            this.brakAlgorytmuKodowania=ex;
            throw ex; 
        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            throw ex; 
        } catch(NaruszenieUniq ex){
            this.naruszenieUniq=ex;
            throw ex;
        } catch(EJBException ex){
            NaruszenieUniq exc=new NaruszenieUniq("pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.UzytkownikSession.utworzKonto()");
            this.naruszenieUniq=exc;
            throw exc;
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
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladDeSerializacjiObiektu
     */
    public void pobierzKontoDoEdycji(Konto konto) throws BladPliku, BladDeSerializacjiObiektu {
        try {
            setKontoEdytuj(MOKEndpoint.pobierzKontoDoEdycji(konto));
        } catch (BladPliku ex) {
            this.bladPliku=ex;
            throw ex; 
        } catch (BladDeSerializacjiObiektu ex) {
            this.bladDeSerializajiObiektu=ex;
            throw ex; 
        }
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     * @throws Exception 
     */
    void zapiszSwojeKontoPoEdycji() throws NiezgodnyLogin, BrakKontaDoEdycji{
        try {
            MOKEndpoint.zapiszSwojeKontoPoEdycji(kontoEdytuj);
        } catch (NiezgodnyLogin ex) {
            this.niezgodnyLogin=ex;
            throw ex; 

        } catch (BrakKontaDoEdycji ex) {
            this.brakKontaDoEdycji=ex;
            throw ex; 

        } catch (KontoNiezgodneWczytanym ex) {
            this.kontoNiezgodneWczytanym=ex;
            throw ex; 

        }
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji
     */
    public void zapiszKontoPoEdycji() throws BrakKontaDoEdycji {
        try {
            MOKEndpoint.zapiszKontoPoEdycji(kontoEdytuj);
        } catch (BrakKontaDoEdycji ex) {
            this.brakKontaDoEdycji=ex;
            throw ex; 

        } catch (KontoNiezgodneWczytanym ex) {
            this.kontoNiezgodneWczytanym=ex;
            throw ex; 

        }
    }

    /**
     * Metoda zmienia hasło obecnie zalogowanego użytkownika. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodneHasla
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     */
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws BrakAlgorytmuKodowania, NieobslugiwaneKodowanie, NiezgodneHasla, NiezgodnyLogin, PoziomDostepuNieIstnieje{           
        try {
            MOKEndpoint.zmienMojeHaslo(noweHaslo, stareHaslo);
        } catch (BrakAlgorytmuKodowania ex) {
            this.brakAlgorytmuKodowania=ex;
            throw ex; 

        } catch (NiezgodneHasla ex) {
            this.niezgodneHasla=ex;
            throw ex; 

        } catch (NieobslugiwaneKodowanie ex) {
            this.nieobslugiwaneKodowanie=ex;
            throw ex; 

        } catch (NiezgodnyLogin ex) {
            this.niezgodnyLogin=ex;
            throw ex; 

        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            throw ex; 

        }
    }    
    
    /**
     * Metoda zmienia hasło obecnie edytowanego konta. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     */

    public void zmienHaslo(String noweHaslo) throws PoziomDostepuNieIstnieje, NieobslugiwaneKodowanie, BrakAlgorytmuKodowania{
        try {
            MOKEndpoint.zmienHaslo(noweHaslo);
        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            throw ex; 
        } catch (NieobslugiwaneKodowanie ex) {
            this.nieobslugiwaneKodowanie=ex;
            throw ex; 
        } catch (BrakAlgorytmuKodowania ex) {
            this.brakAlgorytmuKodowania=ex;
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

    /**
     * Metoda dodająca poziom dostępu do konta
     * @param konto konto do którego należy dodać poziom dostępu
     * @param poziom nazwa poziomu dostępu
     */
    void dodajPoziomDostepu(Konto konto, String poziom) throws BladPoziomDostepu, PoziomDostepuNieIstnieje{
        try {
            MOKEndpoint.dodajPoziomDostepu(konto, poziom);
        } catch (BladPoziomDostepu ex) {
            this.bladPoziomDostepu=ex;
            throw ex; 
        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            throw ex; 
        }
    }
    /**
     * Metoda odłączająca poziom dostępu do konta
     * @param konto konto od którego należy odłączyć poziom dostępu
     * @param poziom nazwa poziomu dostępu
     */
    void odlaczPoziomDostepu(Konto konto, String poziom) throws BladPoziomDostepu{
        try {
            MOKEndpoint.odlaczPoziomDostepu(konto, poziom);
        } catch (BladPoziomDostepu ex) {
            this.bladPoziomDostepu=ex;
            throw ex; 
        }
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
        try{
            wybraneKonto = MOKEndpoint.getSwojeKonto();
            return wybraneKonto;
        }catch(BrakDostepu ex){
            this.brakDostepu=ex;
            throw ex;
        }
    }
    
    public DataModel<Konto> getKontaDataModel() {
        return kontaDataModel;
    }

    public void setKontaDataModel(DataModel<Konto> kontaDataModel) {
        try{
            this.kontaDataModel = kontaDataModel;
        } catch(EJBAccessException ex){
            
        }
    }  
    
}
