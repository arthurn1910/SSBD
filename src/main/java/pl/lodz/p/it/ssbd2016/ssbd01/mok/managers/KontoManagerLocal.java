package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
    void utworzKonto(Konto konto, List<String> poziomyDostepu);
    
   /**
     * Przypadek gdy modyfikujemy własne hasło
     *
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasłow w postaci jawnej
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    void zmienMojeHasloJesliPoprawne(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * Przypadek gdy admin modyfikuje nam hasło
     * @param konto
     * @param noweHaslo
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    void zmienHaslo(Konto konto, String noweHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    
}
