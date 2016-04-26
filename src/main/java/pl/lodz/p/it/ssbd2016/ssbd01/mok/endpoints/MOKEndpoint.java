package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.managers.KontoManagerLocal;

/**
 * API servera dla modułu funkcjonalnego MOK
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
    public void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        kontoManager.dodajPoziomDostepu(konto, poziom);
    }

    @Override
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        kontoManager.odlaczPoziomDostepu(konto, poziom);
    }
}
