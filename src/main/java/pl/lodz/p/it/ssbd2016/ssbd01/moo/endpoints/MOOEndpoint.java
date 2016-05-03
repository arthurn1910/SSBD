/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.NieruchomoscFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.OgloszenieFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.TypNieruchomosciFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.TypOgloszeniaFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady.KontoMOOFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.OgloszenieDeaktywowaneWczesniej;

/**
 *
 * @author java
 */
@Stateful
public class MOOEndpoint implements MOOEndpointLocal {

    @EJB
    private KontoMOOFacadeLocal kontoFacade;
    @EJB
    private TypOgloszeniaFacadeLocal typOgloszeniaFacade;
    @EJB
    private TypNieruchomosciFacadeLocal typNieruchomosciFacade;
    @EJB
    private OgloszenieFacadeLocal ogloszenieFacadeLocal;
    @EJB
    private NieruchomoscFacadeLocal nieruchomoscFacadeLocal;
    
    @Override
    public Konto getKonto(String login) {
        return kontoFacade.znajdzPoLoginie(login);
    }

    @Override
    public TypOgloszenia getTypOgloszenia(String typ) {
        return typOgloszeniaFacade.znajdzPoNazwie(typ);
    }

    @Override
    public TypNieruchomosci getTypNieruchomosci(String typ) {
        return typNieruchomosciFacade.znajdzPoNazwie(typ);
    }

    @Override
    public void dodajOgloszenie(Ogloszenie noweOgloszenie, Nieruchomosc nowaNieruchomosc) {
        
        nieruchomoscFacadeLocal.create(nowaNieruchomosc);
        ogloszenieFacadeLocal.create(noweOgloszenie);
    }

    @Override
    public List<Ogloszenie> pobierzWszytkieOgloszenia() {
        return ogloszenieFacadeLocal.findAll();
    }

    @Override
    public void aktywujOgloszenie(Ogloszenie rowData) {
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        o.setAktywne(true);
    }

    @Override
    public void deaktywujOgloszenie(Ogloszenie rowData) throws OgloszenieDeaktywowaneWczesniej{
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        if(o.getAktywne())
            o.setAktywne(false);
        else 
            throw new OgloszenieDeaktywowaneWczesniej("pl.lodz.p.it.ssbd2016.ssbd01.moo.endpoints.MOOEndpoint.deaktywujOgloszenie()"); 
    }

    @Override
    public void dodajDoUlubionych(Ogloszenie rowData) {
        Konto k = kontoFacade.znajdzPoLoginie("janusz");
        Ogloszenie o = ogloszenieFacadeLocal.find(rowData.getId());
        o.getKontoCollection().add(k);
        k.getOgloszenieUlubioneCollection().add(o);
    }

    @Override
    public List<Ogloszenie> pobierzUlubioneOgloszenia() {
        Konto l = kontoFacade.znajdzPoLoginie("janusz");
        return (List<Ogloszenie>) l.getOgloszenieUlubioneCollection();
    }
    
}
