/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

import javax.ejb.Local;
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

    void zablokujKonto(Konto rowData);

    /**
     *  Przekazuje hasła w postaci jawnej do managera
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     */
    void zmienMojeHaslo(String noweHaslo, String stareHaslo);

    /**
     * Przekazuje konto do zmiany do managera z hasłem postaci jawnej
     * @param noweHaslo nowe hasło w postaci jawnej
     * @param konto     konto które podlegało edycji
     */
    void zmienHaslo(Konto konto, String noweHaslo);

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
     */

    Konto pobierzKontoDoEdycji(Konto konto);
}

