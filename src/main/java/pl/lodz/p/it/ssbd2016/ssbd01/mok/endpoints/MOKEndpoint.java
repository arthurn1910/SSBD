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
import javax.persistence.NoResultException;
import pl.lodz.p.it.ssbd2016.ssbd01.Utils.CloneUtils;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.ssbd01adminPU.PoziomDostepu_;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacade;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

/**
 *
 * @author Patryk
 */
@Stateful
public class MOKEndpoint implements MOKEndpointLocal{
    
    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @EJB
    private KontoManagerLocal kontoManager;
    
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
        Konto k = kontoFacade.find(konto.getId());
        k.setPotwierdzone(true);
    }

    @Override
    public void odblokujKonto(Konto rowData) {
        Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(true);
    }

    @Override
    public void zablokujKonto(Konto rowData) {
        Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(false);
    }

    @Override
    public List<Konto> pobierzWszystkieNiepotwierdzoneKonta() {
        return kontoFacade.pobierzWszystkieNiepotwierdzoneKonta();
    }

    @Override
    public Konto pobierzUzytkownika(String login) {
        Konto konto;
        try {
            konto = kontoFacade.znajdzPoLoginie(login);
        } catch (Exception e) {
            return null;
        }
        return konto;
    }

    @Override
    public List<Konto> pobierzPodobneKonta(Konto konto) {
        return kontoManager.znajdzPodobne(konto);
    }

    @Override
    public boolean dodajPoziomDostepu(Konto konto, String poziom) {
        return kontoManager.dodajPoziomDostepu(konto, poziom);
    }

    @Override
    public boolean odlaczPoziomDostepu(Konto konto, String poziom) {
        return kontoManager.odlaczPoziomDostepu(konto, poziom);
    }
}
