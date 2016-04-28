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

    List<Konto> pobierzWszystkieKonta();

    void potwierdzKonto(Konto konto);

    void odblokujKonto(Konto rowData);

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

    public void zablokujKonto(Konto rowData);

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
}

