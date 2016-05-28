/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci;

/**
 *
 * @author Gleam
 */
@Stateless
public class TypNieruchomosciFacade extends AbstractFacade<TypNieruchomosci> implements TypNieruchomosciFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypNieruchomosciFacade() {
        super(TypNieruchomosci.class);
    }

    public TypNieruchomosci znajdzPoNazwie(String typ) {
        Query q = em.createNamedQuery("TypNieruchomosci.findAll");
        q.setParameter("nazwa", typ);
        return (TypNieruchomosci) q.getResultList().get(0);
    }    
}
