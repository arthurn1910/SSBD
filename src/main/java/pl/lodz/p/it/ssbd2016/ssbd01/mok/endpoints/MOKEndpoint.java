/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2016.ssbd01.Utils.CloneUtils;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.ssbd01adminPU.PoziomDostepu_;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacade;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;

/**
 *
 * @author Patryk
 */
@Stateful
public class MOKEndpoint implements MOKEndpointLocal{
    
    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;
   
    private Konto kontoStan;
    
    @Override
    public void rejestrujKontoKlienta(Konto konto, PoziomDostepu poziomDostepu) {
        konto.setAktywne(true);
        konto.setPotwierdzone(false);
        kontoFacade.create(konto);
        
        poziomDostepu.setKontoId(konto);
        poziomDostepuFacade.create(poziomDostepu);
        konto.getPoziomDostepuCollection().add(poziomDostepu);
        
        
    }

    @Override
    public List<Konto> pobierzWszystkieKonta() {
        return kontoFacade.findAll();
    }

    @Override
    public void potwierdzKonto(Konto konto) {
        /*Konto k = kontoFacade.find(konto.getId());
        k.setPotwierdzone(true);*/
    }

    @Override
    public void odblokujKonto(Konto rowData) {
        /*Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(true);*/
    }

    @Override
    public void zablokujKonto(Konto rowData) {
        /*Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(false);*/
    }
    
    @Override
    public Boolean zaloguj(String login, String haslo) {
        return "a".equals(login) && "a".equals(haslo);
    }

    @Override
    public void dolaczPoziomAgent(Konto konto) {
    }

    @Override
    public void dolaczPoziomMenadzer(Konto konto) {
    }

    @Override
    public void dolaczPoziomAdministrator(Konto konto) {
    }

    @Override
    public void odlaczPoziomAgent(Konto konto) {
    }

    @Override
    public void odlaczPoziomMenadzer(Konto konto) {
    }

    @Override
    public void odlaczPoziomAdministrator(Konto konto) {
    }

    @Override
    public Boolean sprawdzPoziomAgent(Konto konto) {
        return true;
    }

    @Override
    public Boolean sprawdzPoziomMenadzer(Konto konto) {
        return false;
    }

    @Override
    public Boolean sprawdzPoziomAdministrator(Konto konto) {
        return true;
    }

    @Override
    public Konto pobierzUzytkownika() {
        Konto a=new Konto(new Long(1221123131), "aaa", "aaa", false, false, 0);
        a.setEmail("aaa");
        a.setTelefon("cccc");
        a.setImie("anna");
        a.setNazwisko("1111");
        return a;
    }

    @Override
    public String pobierzPoziomy(Konto kontoUzytkownika) {
        return "Klient, Agent";
    }

}
