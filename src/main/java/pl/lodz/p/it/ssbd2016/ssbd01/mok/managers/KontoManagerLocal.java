package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 * Interfejs przetwarzania danych MOK
 */
@Local
public interface KontoManagerLocal {
    /**
     * Metoda zwracająca liste kont podobnych do zadanego konta
     * @param konto     obiekt zawierający kryteria wyszukania
     * @return          lista podobnych kont
     */
    public List<Konto> znajdzPodobne(Konto konto);
    
    /**
     * Metoda dodająca dany poziom dostępu do konta
     * @param konto     konto do którego należy dodać poziom dostępu
     * @param poziom    nazwa poziomu dostępu
     * @return          potwierdzenie wykonania operacji
     * @throws java.lang.Exception
     */
    public void dodajPoziomDostepu(Konto konto, String poziom) throws Exception;
    
    /**
     * Metoda odłączająca dany poziom dostępu do konta
     * @param konto     konto od którego należy odłączyć poziom dostępu
     * @param poziom    nazwa poziomu dostępu
     * @return          potwierdzenie wykonania operacji
     * @throws java.lang.Exception
     */
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception;

    public void zmienHaslo(Konto konto, String noweHaslo);

    public void zmienMojeHasloJesliPoprawne(String noweHaslo, String stareHaslo);

    /**
     * metody tworzy konto klienta(niepotwierdzone)
     * @param konto konto do utworzenia
     */
    void rejestrujKontoKlienta(Konto konto);
    
    /**
     * tworzenie konta o dowolnym, niewykluczajacym sie poziomie dostepu
     * @param konto konto jakie zostaje utworzone
     * @param poziomyDostepu poziomy dostepu jakie beda przypisane(string List)
     * @return true/false
     */
    void utworzKonto(Konto konto, List<String> poziomyDostepu);
    
    /**
     * generuje skrót MD5 z podanego hasła
     * @param password haslo w jawnej postaci
     * @return haslo w skrocie MD5
     */
    
}
