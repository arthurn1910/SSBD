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
    void rejestrujKontoKlienta(Konto konto, PoziomDostepu poziomDostepu);
    
    List<Konto> pobierzWszystkieKonta();

    void potwierdzKonto(Konto konto);

    public void odblokujKonto(Konto rowData);

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

}
