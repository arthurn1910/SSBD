/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.*;

import javax.ejb.Local;
import java.util.List;

/**
 * @author java
 */
@Local
public interface MOOEndpointLocal {

    Konto getKonto(String login);

    TypOgloszenia getTypOgloszenia(String typ);

    TypNieruchomosci getTypNieruchomosci(String typ);

    /**
     * Dodaje ogłszenie dla nieruchomości MOO. 1, Kamil Rogowski
     *
     * @param noweOgloszenie   ogłoszenie
     * @param nowaNieruchomosc nieruchomości
     */
    void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc);

    List<Ogloszenie> pobierzWszytkieOgloszenia();

    void aktywujOgloszenie(Ogloszenie rowData);

    void deaktywujOgloszenie(Ogloszenie rowData);

    void dodajDoUlubionych(Ogloszenie rowData);

    List<Ogloszenie> pobierzUlubioneOgloszenia();

}
