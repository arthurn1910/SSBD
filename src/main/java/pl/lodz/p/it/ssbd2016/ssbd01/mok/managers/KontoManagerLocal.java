package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author java
 */
@Local
public interface KontoManagerLocal {
    
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
    boolean utworzKonto(Konto konto, List<String> poziomyDostepu);
    
    /**
     * generuje skrót MD5 z podanego hasła
     * @param password haslo w jawnej postaci
     * @return haslo w skrocie MD5
     */
    String generateMD5Hash(String password);
    
}
