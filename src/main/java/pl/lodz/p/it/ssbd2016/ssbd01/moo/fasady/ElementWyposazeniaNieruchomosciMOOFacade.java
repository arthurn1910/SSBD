/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.ElementWyposazeniaNieruchomosci;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;

/**
 *
 * @author java
 */
@Stateless
public class ElementWyposazeniaNieruchomosciMOOFacade extends AbstractFacade<ElementWyposazeniaNieruchomosci> implements ElementWyposazeniaNieruchomosciMOOFacadeLocal {
    
    @PersistenceContext(unitName = "ssbd01mooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ElementWyposazeniaNieruchomosciMOOFacade() {
        super(ElementWyposazeniaNieruchomosci.class);
    }

    @Override
    public List<ElementWyposazeniaNieruchomosci> znajdzPoIdNieruchomosci(Long idNieruchomosci) {
        List<ElementWyposazeniaNieruchomosci> wyposazenie = new ArrayList();
        Query q = em.createNamedQuery("ElementWyposazeniaNieruchomosci.findAll");
        List<ElementWyposazeniaNieruchomosci> l = (List<ElementWyposazeniaNieruchomosci>) q.getResultList();
        for (int i = 0; i < l.size(); i++) {
            List<Nieruchomosc> nieruchomosci = new ArrayList(l.get(i).getNieruchomoscWyposazonaCollection());
            for(int j = 0; j < nieruchomosci.size(); j++) {
                if(nieruchomosci.get(j).getId().equals(idNieruchomosci))
                    wyposazenie.add(l.get(i));
            }
        }
        return wyposazenie;
    }
    
    @Override
    public ElementWyposazeniaNieruchomosci znajdzPoId(long id) {
        Query q = em.createNamedQuery("ElementWyposazeniaNieruchomosci.findAll");
        List<ElementWyposazeniaNieruchomosci> l = (List<ElementWyposazeniaNieruchomosci>) q.getResultList();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId() == id) {
                return l.get(i);
            }
        }
        return l.get(0);
    }
}
