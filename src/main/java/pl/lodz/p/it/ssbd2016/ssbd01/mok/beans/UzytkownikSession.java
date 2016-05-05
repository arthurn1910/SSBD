package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.HistoriaLogowania;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;

import javax.annotation.PostConstruct;
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
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
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

    public void setBladDeSerializajiObiektu(BladDeSerializacjiObiektu bladDeSerializajiObiektu) {
        this.bladDeSerializajiObiektu = bladDeSerializajiObiektu;
    }

    public void setBladPliku(BladPliku bladPliku) {
        this.bladPliku = bladPliku;
    }

    public void setBladPoziomDostepu(BladPoziomDostepu bladPoziomDostepu) {
        this.bladPoziomDostepu = bladPoziomDostepu;
    }

    public void setBrakAlgorytmuKodowania(BrakAlgorytmuKodowania brakAlgorytmuKodowania) {
        this.brakAlgorytmuKodowania = brakAlgorytmuKodowania;
    }

    public void setBrakKontaDoEdycji(BrakKontaDoEdycji brakKontaDoEdycji) {
        this.brakKontaDoEdycji = brakKontaDoEdycji;
    }

    private String login;

    @PostConstruct
    public void init() {
        login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        //po naprawniniu problemow z logowaniem zacznie zwracac login uzytkownika
        if(login == null) login = "ANONYMOUS";
        MOKEndpoint.ustawIP(login);
    }

    public void setNieobslugiwaneKodowanie(NieobslugiwaneKodowanie nieobslugiwaneKodowanie) {
        this.nieobslugiwaneKodowanie = nieobslugiwaneKodowanie;
    }

    public void setNiezgodneHasla(NiezgodneHasla niezgodneHasla) {
        this.niezgodneHasla = niezgodneHasla;
    }

    public void setNiezgodnyLogin(NiezgodnyLogin niezgodnyLogin) {
        this.niezgodnyLogin = niezgodnyLogin;
    }

    public void setPoziomDostepuNieIstnieje(PoziomDostepuNieIstnieje poziomDostepuNieIstnieje) {
        this.poziomDostepuNieIstnieje = poziomDostepuNieIstnieje;
    }

    public void setKontoNiezgodneWczytanym(KontoNiezgodneWczytanym kontoNiezgodneWczytanym) {
        this.kontoNiezgodneWczytanym = kontoNiezgodneWczytanym;
    }

    public void setNaruszenieUniq(NaruszenieUniq naruszenieUniq) {
        this.naruszenieUniq = naruszenieUniq;
    }

    public void setBrakDostepu(BrakDostepu brakDostepu) {
        this.brakDostepu = brakDostepu;
    }

    public void setBladWywolania(BladWywolania bladWywolania) {
        this.bladWywolania = bladWywolania;
    }

    public void setNiewykonanaOperacja(NiewykonanaOperacja niewykonanaOperacja) {
        this.niewykonanaOperacja = niewykonanaOperacja;
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
            this.poziomDostepuNieIstnieje=ex;
            obslugaWyjatkow(ex,"../wyjatki/poziomDostepuNieIstnieje.xhtml");

        } catch (NieobslugiwaneKodowanie ex) {
            this.nieobslugiwaneKodowanie=ex;
            obslugaWyjatkow(ex,"../wyjatki/nieobslugiwaneKodowanie.xhtml");
        } catch (BrakAlgorytmuKodowania ex) {
            this.brakAlgorytmuKodowania=ex;
            obslugaWyjatkow(ex,"../wyjatki/brakAlgorytmuKodowania.xhtml");
        } catch(EJBException ex){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String tmp=sw.toString();
            NaruszenieUniq a=new NaruszenieUniq(tmp);
            this.naruszenieUniq=a;
            obslugaWyjatkow(a,"../wyjatki/naruszenieUniq.xhtml");
        } catch (NiewykonanaOperacja ex) {
            this.niewykonanaOperacja=ex;
            obslugaWyjatkow(ex, "../wyjatki/niewykonanaOperacja.xhtml");
        }
    }
    /***
     * Metoda obsługująca wyjąteki
     * @param ex 
     */
    public void obslugaWyjatkow(Exception ex, String path){
        Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
        lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
        FacesContext facesContext=FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect(path);
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
            this.nieobslugiwaneKodowanie=ex;
            obslugaWyjatkow(ex,"../wyjatki/nieobslugiwaneKodowanie.xhtml");
        } catch (BrakAlgorytmuKodowania ex) {
            this.brakAlgorytmuKodowania=ex;
            obslugaWyjatkow(ex,"../wyjatki/brakAlgorytmuKodowania.xhtml");
        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            obslugaWyjatkow(ex,"../wyjatki/poziomDostepuNieIstnieje.xhtml");
        } catch(NaruszenieUniq ex){
            this.naruszenieUniq=ex;
            obslugaWyjatkow(ex,"../wyjatki/naruszenieUniq.xhtml");
        } catch (NiewykonanaOperacja ex) {
            this.niewykonanaOperacja=ex;
            obslugaWyjatkow(ex, "../wyjatki/niewykonanaOperacja.xhtml");
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
            this.bladPliku=ex;
            obslugaWyjatkow(ex, "../wyjatki/bladPliku.xhtml");
        } catch (BladDeSerializacjiObiektu ex) {
            this.bladDeSerializajiObiektu=ex;
            obslugaWyjatkow(ex,"../wyjatki/bladDeSerializacjiObiektu.xhtml");
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
            this.niezgodnyLogin=ex;
            obslugaWyjatkow(ex,"ssbd201601//wyjatki/niezgodnyLogin.xhtml");

        } catch (BrakKontaDoEdycji ex) {
            this.brakKontaDoEdycji=ex;
            obslugaWyjatkow(ex,"../wyjatki/brakKontaDoEdycji.xhtml");

        } catch (KontoNiezgodneWczytanym ex) {
            this.kontoNiezgodneWczytanym=ex;
            obslugaWyjatkow(ex,"../wyjatkikontoNiezgodneWczytanym.xhtml");
        }
    }
    
    /**
     * Metoda zapisuje zmienione konto. Sprawdzana jest blokada optymistyczna
     */
    public void zapiszKontoPoEdycji(){
        try {
            MOKEndpoint.zapiszKontoPoEdycji(kontoEdytuj);
        } catch (BrakKontaDoEdycji ex) {
            this.brakKontaDoEdycji=ex;
            obslugaWyjatkow(ex,"../wyjatki/brakKontaDoEdycji.xhtml");
        } catch (KontoNiezgodneWczytanym ex) {
            this.kontoNiezgodneWczytanym=ex;
            obslugaWyjatkow(ex,"../wyjatkikontoNiezgodneWczytanym.xhtml");
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
            this.brakAlgorytmuKodowania=ex;
            obslugaWyjatkow(ex,"../wyjatki/brakAlgorytmuKodowania.xhtml");
        } catch (NiezgodneHasla ex) {
            this.niezgodneHasla=ex;
            obslugaWyjatkow(ex, "../wyjatki/niezgodneHasla.xhtml");
        } catch (NieobslugiwaneKodowanie ex) {
            this.nieobslugiwaneKodowanie=ex;
            obslugaWyjatkow(ex,"../wyjatki/nieobslugiwaneKodowanie.xhtml");
        } catch (NiezgodnyLogin ex) {
            this.niezgodnyLogin=ex;
            obslugaWyjatkow(ex, "ssbd201601//wyjatki/niezgodnyLogin.xhtml");
        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            obslugaWyjatkow(ex,"../wyjatki/poziomDostepuNieIstnieje.xhtml");
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
            this.poziomDostepuNieIstnieje=ex;
            obslugaWyjatkow(ex,"../wyjatki/poziomDostepuNieIstnieje.xhtml");
        } catch (NieobslugiwaneKodowanie ex) {
            this.nieobslugiwaneKodowanie=ex;
            obslugaWyjatkow(ex,"../wyjatki/nieobslugiwaneKodowanie.xhtml");
        } catch (BrakAlgorytmuKodowania ex) {
            this.brakAlgorytmuKodowania=ex;
            obslugaWyjatkow(ex,"../wyjatki/brakAlgorytmuKodowania.xhtml");
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
    void dodajPoziomDostepu(Konto konto, String poziom){
        try {
            MOKEndpoint.dodajPoziomDostepu(konto, poziom);
        } catch (BladPoziomDostepu ex) {
            this.bladPoziomDostepu=ex;
            obslugaWyjatkow(ex,"../wyjatki/bladPoziomDostepu.xhtml");
        } catch (PoziomDostepuNieIstnieje ex) {
            this.poziomDostepuNieIstnieje=ex;
            obslugaWyjatkow(ex, "../wyjatki/poziomDostepuNieIstnieje.xhtml");
        }catch (NiewykonanaOperacja ex) {
            this.niewykonanaOperacja=ex;
            obslugaWyjatkow(ex, "../wyjatki/niewykonanaOperacja.xhtml");
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
            this.bladPoziomDostepu=ex;
            obslugaWyjatkow(ex,"../wyjatki/bladPoziomDostepu.xhtml");
        }catch (NiewykonanaOperacja ex) {
            this.niewykonanaOperacja=ex;
            obslugaWyjatkow(ex, "../wyjatki/niewykonanaOperacja.xhtml");
        }
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

    public Konto getSwojeKonto() {
        try{
            wybraneKonto = MOKEndpoint.getSwojeKonto();
        }catch(BrakDostepu ex){
            this.brakDostepu=ex;
            obslugaWyjatkow(ex,"../wyjatki/brakDostepu.xhtml");
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
            BrakDostepu exc=new BrakDostepu(tmp);
            this.brakDostepu=exc;
            obslugaWyjatkow(exc, "../wyjatki/brakDostepu.xhtml");
        }
    }  
    
}
