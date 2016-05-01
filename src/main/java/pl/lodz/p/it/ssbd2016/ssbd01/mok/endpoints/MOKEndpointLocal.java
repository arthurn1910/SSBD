package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs API servera dla modułu funkcjonalnego MOK
 */
@Local
public interface MOKEndpointLocal {

    /**
     * Metoda wprowadza do systemu konto klienta (niepotwierdzone)
     * @param konto informacje kontcie do utworzenia
     */
    void rejestrujKontoKlienta(Konto konto);
    
    /**
     * Metoda wprowadza do systemu konto o dowolnym, niewykluczajacym sie poziomie dostepu
     * @param konto informacje kontcie do utworzenia
     * @param poziomyDostepu lista poziomów dostępu jakie beda przypisane
     */
    void utworzKonto(Konto konto, List<String> poziomyDostepu);
    
    /**
     * Metoda zmienia stan konta na potwierdzone
     * @param konto konto, które ma zostać potwierdzone
     */
    void potwierdzKonto(Konto konto);
    
    /**
     * Metoda zmienia stan konta na aktywne
     * @param konto konto które ma zostać odblokowane 
     */
    public void odblokujKonto(Konto konto);

    /**
     * Metoda zmienia stan konta na nieaktywne
     * @param konto konto które ma zostać zablokowane
     */
    public void zablokujKonto(Konto konto);
    
    /**
     * Zmienia hasło dla obecnie zalogowanego użytkownika
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     * @throws java.lang.Exception rzucany gdy stare hasła się nie zgadzaja
     */
    void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws Exception;

    /**
     * Zmienia hasło dla obecnie edytowanego użytkownika
     * @param noweHaslo nowe hasło w postaci jawnej
     */
    void zmienHaslo(String noweHaslo);

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
     * Metoda dodająca dany poziom dostępu do konta
     * @param konto     konto do którego należy dodać poziom dostępu
     * @param poziom    nazwa poziomu dostępu
     * @throws java.lang.Exception rzucany gdy nie można dodać poziomu dostępu
     */
    public void dodajPoziomDostepu(Konto konto, String poziom) throws Exception;

    /**
     * Metoda odłączająca dany poziom dostępu do konta
     * @param konto     konto od którego należy odłączyć poziom dostępu
     * @param poziom    nazwa poziomu dostępu
     * @throws java.lang.Exception rzucany gdy nie można odłączyć poziomu dostępu
     */
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception;

    /**
     * Metoda pobierająca konto do edycji. Zapewnia blokadę optymistyczną.
     * @param konto     konto które chcemy edytować
     * @return          głęboka kopia encji Konto
     */
    Konto pobierzKontoDoEdycji(Konto konto);
    
    /**
     * Metoda zapisuje konto użytkownika obecnie zalogowanego ze zmienionymi 
     * parameterami. Sprawdzana jest blokada optymistyczna.
     * @param konto     konto ze zmianami
     * @throws Exception rzucany gdy endytowane konto nie jest kontem obecnie zalogowanego użytkownika
     */
    public void zapiszSwojeKontoPoEdycji(Konto konto) throws Exception;
    
    /**
     * Metoda zapisuje konto ze zmienionymi parameterami. Sprawdzana jest
     * blokada optymistyczna.
     * @param konto konto ze zmianami
     */
    void zapiszKontoPoEdycji(Konto konto);

    /**
     * Metoda pobierająca konto obecnie zalogowanego użytkownika
     * @return      konto obecnie zalogowanego użytkownika
     */
    public Konto getSwojeKonto();
}

