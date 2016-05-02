/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;

/**
 *
 * @author java
 */
@Local
public interface MOOEndpointLocal {

    public Konto getKonto(String login);

    public TypOgloszenia getTypOgloszenia(String typ);

    public TypNieruchomosci getTypNieruchomosci(String typ);

    public void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc);

    public List<Ogloszenie> pobierzWszytkieOgloszenia();

    public void aktywujOgloszenie(Ogloszenie rowData);

    public void deaktywujOgloszenie(Ogloszenie rowData);

    public void dodajDoUlubionych(Ogloszenie rowData);

    public List<Ogloszenie> pobierzUlubioneOgloszenia();
    
    public void deaktywujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenie) throws Exception;
    
    public void edytujOgloszenieDanegoUzytkownika(Ogloszenie ogloszenieNowe, Ogloszenie ogloszenieStare) throws Exception;
}
