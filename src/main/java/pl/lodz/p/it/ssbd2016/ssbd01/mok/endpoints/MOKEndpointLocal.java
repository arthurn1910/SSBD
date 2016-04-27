/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import java.io.IOException;
import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

/**
 *
 * @author Patryk
 */
@Local
public interface MOKEndpointLocal {
    
    /**
     * metody tworzy konto klienta(niepotwierdzone)
     * @param konto konto do utworzenia
     */
    void rejestrujKontoKlienta(Konto konto);
    
    /**
     * tworzenie konta o dowolnym, niewykluczajacym sie poziomie dostepu
     * @param konto konto jakie zostaje utworzone
     * @param poziomyDostepu poziomy dostepu jakie beda przypisane(string List)
     * @return 
     */
    void utworzKonto(Konto konto, List<String> poziomyDostepu);
    
    /**
     * Metoda, która pobiera wszystkie konta
     * @return Lista kont
     */
    List<Konto> pobierzWszystkieKonta();

    /**
     * Metoda zmienia stan konta na potwierdzone
     * @param konto konto,ktore ma zostac potwierdzone
     */
    void potwierdzKonto(Konto konto);

    /**
     * Metoda zmienia stan konta na aktywne
     * @param konto konto które ma zostać odblokowane 
     */
    public void odblokujKonto(Konto konto);

    /**
     * Metoda zmienia stan konta na nieaktywne
     * @param konto konto, ktore ma zostac zablokowane
     */
    public void zablokujKonto(Konto konto);
}
