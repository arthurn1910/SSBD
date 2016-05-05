package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.HistoriaLogowania;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.ejb.Local;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladDeSerializacjiObiektu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.KontoNiezgodneWczytanym;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiewykonanaOperacja;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodneHasla;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOK
 */
@Local
public interface MOKEndpointLocal {

    /**
     * Metoda wprowadza do systemu konto klienta (niepotwierdzone)
     * @param konto informacje kontcie do utworzenia
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     */
    void rejestrujKontoKlienta(Konto konto) throws NiewykonanaOperacja, PoziomDostepuNieIstnieje, NieobslugiwaneKodowanie, BrakAlgorytmuKodowania;
    
    /**
     * Metoda wprowadza do systemu konto o dowolnym, niewykluczajacym sie poziomie dostepu
     * @param konto informacje kontcie do utworzenia
     * @param poziomyDostepu lista poziomów dostępu jakie beda przypisane
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     */
    void utworzKonto(Konto konto, List<String> poziomyDostepu) throws NiewykonanaOperacja,NieobslugiwaneKodowanie, BrakAlgorytmuKodowania, PoziomDostepuNieIstnieje;
    
    /**
     * Metoda zmienia stan konta na potwierdzone
     * @param konto konto, które ma zostać potwierdzone
     */
    void potwierdzKonto(Konto konto);

    /**
     * Metoda zmienia stan konta na aktywne
     * @param konto konto które ma zostać odblokowane
     */
     void odblokujKonto(Konto konto);

    /**
     * Metoda zmienia stan konta na nieaktywne
     * @param konto konto które ma zostać zablokowane
     */
     void zablokujKonto(Konto konto);

    /**
     * Zmienia hasło dla obecnie zalogowanego użytkownika
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodneHasla
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     */
    void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws BrakAlgorytmuKodowania, NiezgodneHasla, 
            NieobslugiwaneKodowanie, NiezgodnyLogin, PoziomDostepuNieIstnieje  ;

    /**
     * Zmienia hasło dla obecnie edytowanego użytkownika
     * @param noweHaslo nowe hasło w postaci jawnej
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania
     */
    void zmienHaslo(String noweHaslo) throws PoziomDostepuNieIstnieje, NieobslugiwaneKodowanie, BrakAlgorytmuKodowania;

    /**
     * Wyszukuje konto o podanym loginie
     * @param login login konta
     * @return znalezione konto
     */
    Konto znajdzPoLoginie(String login);

    /**
     * Metoda zwracająca liste kont podobnych do zadanego konta
     * @param konto     obiekt zawierający kryteria wyszukania
     * @return          lista podobnych kont
     */

    public List<Konto> pobierzPodobneKonta(Konto konto);
    
        /**
     * Metoda zwracająca liste wszystkich kont
     * @return lista wszystkich kont
     */
    public List<Konto> pobierzWszystkieKonta();
    
    /**
     * Metoda dodająca dany poziom dostępu do konta
     * @param konto     konto do którego należy dodać poziom dostępu
     * @param poziom    nazwa poziomu dostępu
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     */
    public void dodajPoziomDostepu(Konto konto, String poziom) throws NiewykonanaOperacja, BladPoziomDostepu, PoziomDostepuNieIstnieje;

    /**
     * Metoda odłączająca dany poziom dostępu do konta
     * @param konto     konto od którego należy odłączyć poziom dostępu
     * @param poziom    nazwa poziomu dostępu
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu
     */
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws NiewykonanaOperacja, BladPoziomDostepu;

    /**
     * Metoda pobierająca konto do edycji. Zapewnia blokadę optymistyczną.
     * @param konto     konto które chcemy edytować
     * @return          głęboka kopia encji Konto
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladDeSerializacjiObiektu
     */
    Konto pobierzKontoDoEdycji(Konto konto) throws BladPliku, BladDeSerializacjiObiektu ;
    
    /**
     * Metoda zapisuje konto użytkownika obecnie zalogowanego ze zmienionymi
     * parameterami. Sprawdzana jest blokada optymistyczna.
     * @param konto     konto ze zmianami
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiezgodnyLogin
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji
     */
    public void zapiszSwojeKontoPoEdycji(Konto konto) throws NiezgodnyLogin, BrakKontaDoEdycji, KontoNiezgodneWczytanym ;
    
    /**
     * Metoda zapisuje konto ze zmienionymi parameterami. Sprawdzana jest
     * blokada optymistyczna.
     * @param konto konto ze zmianami
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakKontaDoEdycji
     */
    void zapiszKontoPoEdycji(Konto konto) throws BrakKontaDoEdycji, KontoNiezgodneWczytanym ;

    /**
     * Metoda pobierająca konto obecnie zalogowanego użytkownika
     * @return      konto obecnie zalogowanego użytkownika
     */
     Konto getSwojeKonto() throws BrakDostepu;;
 
    /**
     * Metoda pobiera całą historię aktywności użytkowników związanych z logowaniem
     *
     */
    List<HistoriaLogowania> pobierzHistorieLogowanUzytkownikow();

    void ustawIP(String login);
}

