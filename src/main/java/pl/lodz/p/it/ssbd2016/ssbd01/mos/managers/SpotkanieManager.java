package pl.lodz.p.it.ssbd2016.ssbd01.mos.managers;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Kamil Rogowski
 *         Menadżer dla spotkań
 */

@Stateless
public class SpotkanieManager implements SpotkanieManagerLocal {

    @EJB
    SpotkanieFacadeLocal spotkanieFacade;

    @Override
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {

        return null;
    }

    @Override
    public List<Spotkanie> pobierzSpotkaniaDlaKonta(Konto spotkaniaDlakonta) {
        return null;
    }
}
