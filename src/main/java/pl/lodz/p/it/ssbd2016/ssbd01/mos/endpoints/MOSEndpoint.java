package pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.KontoFacadeLocalInMOS;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.OgloszenieFacadeLocalInMOS;


@Stateful
public class MOSEndpoint implements MOSEndpointLocal {
    @EJB
    private SpotkanieFacadeLocal spotkanieFacade;
    @EJB
    private KontoFacadeLocalInMOS kontoFacade;
    @EJB
    private OgloszenieFacadeLocalInMOS ogloszenieFacade;
    
    @Override
    public void dodajSpotkanie(Spotkanie spotkanie) {
        spotkanieFacade.create(spotkanie);
    }
    
    @Override
    public List<Konto> pobierzKonta() {
        List<Konto> konta = kontoFacade.findAll();
        return konta;
    }
    
    @Override
    public Konto pobierzPierwszeKonto() {
        List<Konto> konta = kontoFacade.findAll();
        return konta.get(0);
    }
    
    @Override
    public Ogloszenie pobierzPierwszeOgloszenie() {
        List<Ogloszenie> ogloszenia = ogloszenieFacade.findAll();
        return ogloszenia.get(0);
    }

}
