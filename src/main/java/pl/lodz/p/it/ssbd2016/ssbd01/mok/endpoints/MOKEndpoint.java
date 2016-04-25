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
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManager;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;
import pl.lodz.p.it.ssbd2016ssbd01.mok.utils.PoziomDostepuManager;

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
    
    @EJB
    private KontoManagerLocal kontoManager;
    
    @Override
    public void rejestrujKontoKlienta(Konto konto) {
        kontoManager.rejestrujKontoKlienta(konto);
    }
    
    @Override
    public boolean utworzKonto(Konto konto, List<String> poziomyDostepu) {
        return kontoManager.utworzKonto(konto, poziomyDostepu);
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
    public void odblokujKonto(Konto konto) {
        Konto o = kontoFacade.find(konto.getId());
        o.setAktywne(true);
    }

    
    @Override
    public void zablokujKonto(Konto rowData) {
        Konto o = kontoFacade.find(rowData.getId());
        o.setAktywne(false);
    }
}
