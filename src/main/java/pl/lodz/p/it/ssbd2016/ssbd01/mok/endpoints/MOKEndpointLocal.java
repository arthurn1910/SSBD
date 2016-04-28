/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

import javax.ejb.Local;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Patryk
 */
@Local
public interface MOKEndpointLocal {

    void rejestrujKontoKlienta(Konto konto, PoziomDostepu poziomDostepu);
    
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
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * @param noweHaslo nowe hasło w postaci jawnej
     * @param konto     konto które podlegało edycji
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    void zmienHaslo(Konto konto, String noweHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    public Boolean zaloguj(String login, String haslo);

    public void dolaczPoziomAgent(Konto konto);

    public void dolaczPoziomMenadzer(Konto konto);

    public void dolaczPoziomAdministrator(Konto konto);

    public void odlaczPoziomAgent(Konto konto);

    public void odlaczPoziomMenadzer(Konto konto);

    public void odlaczPoziomAdministrator(Konto konto);

    public Boolean sprawdzPoziomAgent(Konto konto);

    public Boolean sprawdzPoziomMenadzer(Konto konto);

    public Boolean sprawdzPoziomAdministrator(Konto konto);

    public Konto pobierzUzytkownika();

    public String pobierzPoziomy(Konto kontoUzytkownika);

    /**
     * zapisuje konto ze zmienionymi parameterami
     *
     * @param konto konto do zmiany
     */
    void zapiszKontoPoEdycji(Konto konto);

    /**
     * Wyszukuje konto o podanym loginie
     *
     * @param login login jako string
     * @return znalezione konto
     */
    Konto znajdzPoLoginie(String login);

    /**
     * Pobiera konto do edycji
     *
     * @param konto przyjmuje konto do edytowania
     * @return zwraca kopię
     * @throws IOException
     * @throws ClassNotFoundException
     */
    Konto pobierzKontoDoEdycji(Konto konto) throws IOException, ClassNotFoundException;
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

