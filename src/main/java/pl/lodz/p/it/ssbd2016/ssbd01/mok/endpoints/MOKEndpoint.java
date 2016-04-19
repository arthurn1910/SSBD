package pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;


@Stateful
public class MOKEndpoint implements MOKEndpointLocal {
    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @Override
    public Konto pobierzPierwszeKonto() {
        List<Konto> konta = kontoFacade.findAll();
        return konta.get(0);
    }
    
    @Override
    public List<Konto> pobierzKonta() {
        List<Konto> konta = kontoFacade.findAll();
        return konta;
    }
}
