package pl.lodz.p.it.ssbd2016.ssbd01.mos.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import pl.lodz.p.it.ssbd2016.ssbd01.Utils.ZalogowanyUzytkownik;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.KontoFacadeLocalInMOS;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.OgloszenieFacadeLocalInMOS;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady.SpotkanieFacadeLocal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Menadżer dla spotkań
 */
@Stateless
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SpotkanieManager implements SpotkanieManagerLocal {

    @EJB
    private SpotkanieFacadeLocal spotkanieFacade;
    @EJB
    private KontoFacadeLocalInMOS kontoFacade;
    @EJB
    private OgloszenieFacadeLocalInMOS ogloszenieFacade;

    @Override
    @RolesAllowed("pobierzSpotkaniaDlaOgloszenia")
    public List<Spotkanie> pobierzSpotkaniaDlaOgloszenia(Ogloszenie ogloszenie) {
        return spotkanieFacade.findByOgloszenie(ogloszenie);

    }

    @Override
    @RolesAllowed("edytujSpotkania")
    public void edytujSwojeSpotkanie(Spotkanie spotkanie) {
        spotkanieFacade.edit(spotkanie);
    }

    @Override
    @RolesAllowed("rezerwujSpotkanie")
    public void rezerwujSpotkanie(Spotkanie spotkanie, String login) throws WyjatekSystemu{
        spotkanie.setIdUzytkownika(kontoFacade.znajdzPoLoginie(login));
        List<Spotkanie> listaTerminowZajetych=new ArrayList<Spotkanie>();
        listaTerminowZajetych=spotkanieFacade.terminyUzytkownika(spotkanie.getIdUzytkownika());
        Konto agent=ogloszenieFacade.findById(spotkanie.getIdOgloszenia().getId()).getIdAgenta();
        List<Spotkanie> spotkaniaAgenta=(List<Spotkanie>) kontoFacade.find(agent.getId()).getSpotkanieCollection();
        if(spotkaniaAgenta!=null){
            for( Spotkanie ret : spotkaniaAgenta){
                listaTerminowZajetych.add(ret);
            }
        }
        for(Spotkanie s: listaTerminowZajetych){
            Calendar a=Calendar.getInstance();
            a.setTime(s.getDataSpotkania());
            long t=a.getTimeInMillis();
            Date zakonczeniaSpotkania= new Date(t + (s.getDlugoscSpotkania() * 60000));
            a.setTime(spotkanie.getDataSpotkania());
            t=a.getTimeInMillis();
            Date zakonczeniaSpotkania2= new Date(t + (spotkanie.getDlugoscSpotkania() * 60000));
            if(s.getDataSpotkania().getTime() < spotkanie.getDataSpotkania().getTime() 
                    && zakonczeniaSpotkania.getTime() < spotkanie.getDataSpotkania().getTime())
                continue;
            if(s.getDataSpotkania().getTime() > zakonczeniaSpotkania2.getTime() 
                    && zakonczeniaSpotkania.getTime() > zakonczeniaSpotkania2.getTime())
                continue;
            WyjatekSystemu e= new WyjatekSystemu("wyjatek.terminZajety", "MOS");
            throw new WyjatekSystemu(e.getMessage(), e, "MOS");
        }
        spotkanieFacade.create(spotkanie);
        spotkanie.setIdUzytkownika(agent);
        spotkanieFacade.create(spotkanie);
    }

    @Override
    @RolesAllowed("anulujSpotkanie")
    public void anulujSpotkanie(Spotkanie spotkanieDoAnulowania) {

        final Ogloszenie spotkaniaPowiazaneZOgloszeniem = spotkanieDoAnulowania.getIdOgloszenia();
        spotkaniaPowiazaneZOgloszeniem.getSpotkanieCollection().remove(spotkanieDoAnulowania);
        final Konto konto = kontoFacade.znajdzPoLoginie(ZalogowanyUzytkownik.getLoginZalogowanegoUzytkownika());
        konto.getSpotkanieCollection().remove(spotkanieDoAnulowania);
        ogloszenieFacade.edit(spotkaniaPowiazaneZOgloszeniem);
        spotkanieFacade.remove(spotkanieDoAnulowania);
        kontoFacade.edit(konto);
    }
}
