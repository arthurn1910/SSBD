package pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.KontoFacadeLocalInMOS;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.OgloszenieFacadeLocalInMOS;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.managers.SpotkanieManagerLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.List;


@Stateful
public class MOSEndpoint implements MOSEndpointLocal {
    @EJB
    private SpotkanieFacadeLocal spotkanieFacade;
    @EJB
    private KontoFacadeLocalInMOS kontoFacade;
    @EJB
    private OgloszenieFacadeLocalInMOS ogloszenieFacade;
    @EJB
    private SpotkanieManagerLocal spotkanieManager;

    @Override
    public void dodajSpotkanie(Spotkanie spotkanie) {
        spotkanieFacade.create(spotkanie);
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
    @Override
    public List<Spotkanie> pobierzSpotkania(Konto spotkaniaDlaKonta) {
        return spotkanieManager.pobierzSpotkaniaDlaKonta(spotkaniaDlaKonta);
    }

    @Override
    public void anulujSpotkanie(Konto konto, Spotkanie spotkanieDoAnulowania) {
        spotkanieFacade.remove(spotkanieDoAnulowania);
    }

    @Override
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {
        return spotkanieManager.pobierzSpotkaniaDlaOgloszenia(ogloszenie);
    }
}
