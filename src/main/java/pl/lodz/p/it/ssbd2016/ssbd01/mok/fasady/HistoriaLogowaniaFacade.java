package pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.HistoriaLogowania;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;
import pl.lodz.p.it.ssbd2016.ssbd01.interceptors.TrackerInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Kamil Rogowski on 01.05.2016.
 *
 * @author Kamil Rogowski
 */
@Stateless
@Interceptors({TrackerInterceptor.class})
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class HistoriaLogowaniaFacade extends AbstractFacade<HistoriaLogowania> implements HistoriaLogowaniaFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public HistoriaLogowaniaFacade() {
        super(HistoriaLogowania.class);
    }

    /**
     * zwraca null lub jeden wynik, bo na niej bazuje insert/update
     *
     * @param konto
     * @return
     */
    @Override
    public HistoriaLogowania findyByIdKonta(Konto konto) {
        Query q = em.createNamedQuery("HistoriaLogowania.findByIdKonta");
        q.setParameter("idKonta", konto.getId());

        q.setMaxResults(1);
        List<HistoriaLogowania> historiaLogowan = q.getResultList();
        if (historiaLogowan.isEmpty()) {
            return null;
        }
        return historiaLogowan.get(0);

    }
}
