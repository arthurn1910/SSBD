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
import javax.annotation.security.RolesAllowed;


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
    @RolesAllowed("pobierzSpotkania")
    public List<Spotkanie> pobierzSpotkania(Konto spotkaniaDlaKonta) {
        return spotkanieManager.pobierzUmowioneSpotkania(spotkaniaDlaKonta);
    }

    @Override
    @RolesAllowed("anulujSpotkanie")
    public void anulujSpotkanie(Konto konto, Spotkanie spotkanieDoAnulowania) {
        spotkanieFacade.remove(spotkanieDoAnulowania);
    }

    @Override
    @RolesAllowed("pobierzSpotkaniaDlaOgloszenia")
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {
        return spotkanieManager.pobierzSpotkaniaDlaOgloszenia(ogloszenie);
    }

    @Override
    @RolesAllowed("rezerwujSpotkanie")
    public void rezerwujSpotkanie(Spotkanie spotkanie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @RolesAllowed("pobierzSpotkanieDoEdycji")
    public Spotkanie pobierzSpotkanieDoEdycji(Spotkanie spotkanie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @RolesAllowed("zapiszSpotkaniePoEdycji")
    public void zapiszSpotkaniePoEdycji(Spotkanie spotkanie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @RolesAllowed("pobierzUmowioneSpotkania")
    public List<Spotkanie> pobierzUmowioneSpotkania(Konto konto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
