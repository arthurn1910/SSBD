/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;

/**
 *
 * @author java
 */
@Stateless
public class NieruchomoscFacade extends AbstractFacade<Nieruchomosc> implements NieruchomoscFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NieruchomoscFacade() {
        super(Nieruchomosc.class);
    }
    
    @Override
    public Nieruchomosc znajdzPoId(long id) {
        Query q = em.createNamedQuery("Nieruchomosc.findAll");
        List<Nieruchomosc> l = (List<Nieruchomosc>) q.getResultList();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId() == id) {
                return l.get(i);
            }
        }
        return l.get(0);
    }
}
