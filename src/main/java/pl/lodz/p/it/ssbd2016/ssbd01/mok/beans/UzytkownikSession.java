package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
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
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.ServletException;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladDeSerializacjiObiektu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladWywolania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.KontoNiezgodneWczytanym;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NaruszenieUniq;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiewykonanaOperacja;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodneHasla;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.OgloszenieDeaktywowaneWczesniej;
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
    private BladWywolania bladWywolania;
    private NiewykonanaOperacja niewykonanaOperacja;

    public BladDeSerializacjiObiektu getBladDeSerializajiObiektu() {
        return bladDeSerializajiObiektu;
    }

    public BladWywolania getBladWywolania() {
        return bladWywolania;
    }

    public NiewykonanaOperacja getNiewykonanaOperacja() {
        return niewykonanaOperacja;
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
     */
    public void rejestrujKontoKlienta(Konto k){
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
            poziomDostepuNieIstniejeFunkcja(ex);

        } catch (NieobslugiwaneKodowanie ex) {
            nieobslugiwaneKodowanieFunkcja(ex);
        } catch (BrakAlgorytmuKodowania ex) {
            brakAlgorytmuKodowaniaFunkcja(ex);
        } catch(EJBException ex){
            naruszenieUniqFunkcja(naruszenieUniq);
        }
    }
    /***
     * Metoda obsługująca wyjątek brakDostępu
     * @param ex 
     */
    public void brakDostepuFunkcja(BrakDostepu ex){
        this.brakDostepu=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/brakDostepu.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    /***
     * Metoda obsługująca wyjątek NaruszenieUniq
     * @param ex 
     */
    public void naruszenieUniqFunkcja(NaruszenieUniq ex){
        this.naruszenieUniq=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/naruszenieUniq.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }   
    }
    /***
     * Metoda obsługująca wyjątek BladDeSerializacjiObiektu
     * @param ex 
     */
    public void bladDeSerializacjiObiektuFunkcja(BladDeSerializacjiObiektu ex){
        this.bladDeSerializajiObiektu=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/bladDeSerializacjiObiektu.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        } 
    }
    /***
     * Metoda obsługująca wyjątek BladPoziomDostepu
     * @param ex 
     */
    public void bladPoziomDostepuFunkcja(BladPoziomDostepu ex){
        this.bladPoziomDostepu=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/bladPoziomDostepu.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }  
    }
    /***
     * Metoda obsługująca wyjątek BladWywolania
     * @param ex 
     */
    public void bladWywolaniaFunkcja(BladWywolania ex){
        this.bladWywolania=ex;   
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/bladWywolania.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }   
    }
    /***
     * Metoda obsługująca wyjątek BrakAlgorytmuKodowania
     * @param ex 
     */
    public void brakAlgorytmuKodowaniaFunkcja(BrakAlgorytmuKodowania ex){
            this.brakAlgorytmuKodowania=ex;
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("../wyjatki/brakAlgorytmuKodowania.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
            }   
    }
    /***
     * Metoda obsługująca wyjątek BrakKontaDoEdycji
     * @param ex 
     */
    public void brakKontaDoEdycjiFunkcja(BrakKontaDoEdycji ex){
        this.brakKontaDoEdycji=ex;
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("../wyjatki/brakKontaDoEdycji.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
            } 
    }
    /***
     * Metoda obsługująca wyjątek KontoNiezgodneWczytanym
     * @param ex 
     */
    public void kontoNiezgodneWczytanymFunkcja(KontoNiezgodneWczytanym ex){
        this.kontoNiezgodneWczytanym=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatkikontoNiezgodneWczytanym.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        } 
    }
    /***
     * Metoda obsługująca wyjątek NieobslugiwaneKodowanie
     * @param ex 
     */
    public void nieobslugiwaneKodowanieFunkcja(NieobslugiwaneKodowanie ex){
        this.nieobslugiwaneKodowanie=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/nieobslugiwaneKodowanie.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }   
    }
    /***
     * Metoda obsługująca wyjątek NiewykonanaOperacja
     * @param ex 
     */
    public void niewykonanaOperacjaFunkcja(NiewykonanaOperacja ex){
        this.niewykonanaOperacja=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect(".//wyjatki/niewykonanaOperacja.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    /***
     * Metoda obsługująca wyjątek NiezgodneHasla
     * @param ex 
     */
    public void niezgodneHaslaFunkcja(NiezgodneHasla ex){
        this.niezgodneHasla=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/niezgodneHasla.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }   
    }
    /***
     * Metoda obsługująca wyjątek NiezgodnyLogin
     * @param ex 
     */
    public void niezgodnyLoginFunkcja(NiezgodnyLogin ex){
        this.niezgodnyLogin=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("ssbd201601//wyjatki/niezgodnyLogin.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }  
    }
    /***
     * Metoda obsługująca wyjątek PoziomDostepuNieIstnieje
     * @param ex 
     */
    public void poziomDostepuNieIstniejeFunkcja(PoziomDostepuNieIstnieje ex){
        this.poziomDostepuNieIstnieje=ex;
        this.poziomDostepuNieIstnieje=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/poziomDostepuNieIstnieje.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    /***
     * Metoda obsługująca wyjątek Exception
     * @param ex 
     */
    public void exceptionFunkcja(Exception ex){
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/nieobsluzonyWyjatek.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }   
    }
    /***
     * Metoda obsługująca wyjątek BladPliku
     * @param ex 
     */
    public void bladPlikuFunkcja(BladPliku ex){
        this.bladPliku=ex;
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("../wyjatki/bladPliku.xhtml");
            facesContext.renderResponse();
        } catch (IOException ex1) {
            Logger.getLogger(UzytkownikSession.class.getName()).log(Level.SEVERE, null, ex1);
        }   
    }
        
    
    /**
     * Rejestruje konto, nadając mu wybrane poizomy dostępu
     * @param  k  konto, które ma zostać zarejestrowane
     * @param  poziomyDostepu  poziomy dostępu, który ma mieć nowo tworzone konto
     */
    public void utworzKonto(Konto k, List<String> poziomyDostepu){
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
            nieobslugiwaneKodowanieFunkcja(ex);
        } catch (BrakAlgorytmuKodowania ex) {
            brakAlgorytmuKodowaniaFunkcja(ex);
        } catch (PoziomDostepuNieIstnieje ex) {
            poziomDostepuNieIstniejeFunkcja(ex);
        } catch(NaruszenieUniq ex){
            naruszenieUniqFunkcja(ex);
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
     */
    public void pobierzKontoDoEdycji(Konto konto){
        try {
            setKontoEdytuj(MOKEndpoint.pobierzKontoDoEdycji(konto));
        } catch (BladPliku ex) {
            bladPlikuFunkcja(ex);
        } catch (BladDeSerializacjiObiektu ex) {
            bladDeSerializacjiObiektuFunkcja(ex);
        }
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     * @throws Exception 
     */
    void zapiszSwojeKontoPoEdycji(){
        try {
            MOKEndpoint.zapiszSwojeKontoPoEdycji(kontoEdytuj);
        } catch (NiezgodnyLogin ex) {
            niezgodnyLoginFunkcja(ex);

        } catch (BrakKontaDoEdycji ex) {
            brakKontaDoEdycjiFunkcja(ex);

        } catch (KontoNiezgodneWczytanym ex) {
            kontoNiezgodneWczytanymFunkcja(ex);
        }
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     */
    public void zapiszKontoPoEdycji(){
        try {
            MOKEndpoint.zapiszKontoPoEdycji(kontoEdytuj);
        } catch (BrakKontaDoEdycji ex) {
            brakKontaDoEdycjiFunkcja(ex);
        } catch (KontoNiezgodneWczytanym ex) {
            kontoNiezgodneWczytanymFunkcja(ex);
        }
    }

    /**
     * Metoda zmienia hasło obecnie zalogowanego użytkownika. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     */
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo){           
        try {
            MOKEndpoint.zmienMojeHaslo(noweHaslo, stareHaslo);
        } catch (BrakAlgorytmuKodowania ex) {
            brakAlgorytmuKodowaniaFunkcja(ex);
        } catch (NiezgodneHasla ex) {
            niezgodneHaslaFunkcja(ex);
        } catch (NieobslugiwaneKodowanie ex) {
            nieobslugiwaneKodowanieFunkcja(ex);
        } catch (NiezgodnyLogin ex) {
            niezgodnyLoginFunkcja(ex);
        } catch (PoziomDostepuNieIstnieje ex) {
            poziomDostepuNieIstniejeFunkcja(ex);
        }
    }    
    
    /**
     * Metoda zmienia hasło obecnie edytowanego konta. Sprawdzana jest blokada
     * optymistyczna
     * @param noweHaslo  nowe hasło w postaci jawnej
     */
    public void zmienHaslo(String noweHaslo){
        try {
            MOKEndpoint.zmienHaslo(noweHaslo);
        } catch (PoziomDostepuNieIstnieje ex) {
            poziomDostepuNieIstniejeFunkcja(ex);
        } catch (NieobslugiwaneKodowanie ex) {
            nieobslugiwaneKodowanieFunkcja(ex);
        } catch (BrakAlgorytmuKodowania ex) {
            brakAlgorytmuKodowaniaFunkcja(ex);
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
    void dodajPoziomDostepu(Konto konto, String poziom){
        try {
            MOKEndpoint.dodajPoziomDostepu(konto, poziom);
        } catch (BladPoziomDostepu ex) {
            bladPoziomDostepuFunkcja(ex);
        } catch (PoziomDostepuNieIstnieje ex) {
            poziomDostepuNieIstniejeFunkcja(ex);
        }
    }
    /**
     * Metoda odłączająca poziom dostępu do konta
     * @param konto konto od którego należy odłączyć poziom dostępu
     * @param poziom nazwa poziomu dostępu
     */
    void odlaczPoziomDostepu(Konto konto, String poziom){
        try {
            MOKEndpoint.odlaczPoziomDostepu(konto, poziom);
        } catch (BladPoziomDostepu ex) {
            bladPoziomDostepuFunkcja(ex);
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

    public Konto getSwojeKonto() {
        try{
            wybraneKonto = MOKEndpoint.getSwojeKonto();
        }catch(BrakDostepu ex){
            brakDostepuFunkcja(ex);
        }
        return wybraneKonto;
    }
    
    public DataModel<Konto> getKontaDataModel() {
        return kontaDataModel;
    }

    public void setKontaDataModel(DataModel<Konto> kontaDataModel) {
        try{
            this.kontaDataModel = kontaDataModel;
        } catch(EJBAccessException ex){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String tmp=sw.toString();
            brakDostepuFunkcja(new BrakDostepu(tmp));
        }
    }  
    
}
