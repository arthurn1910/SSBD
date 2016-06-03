/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author java
 */
@Stateless
public class OgloszenieFacadeInMOS extends AbstractFacade<Ogloszenie> implements OgloszenieFacadeLocalInMOS {

    @PersistenceContext(unitName = "ssbd01mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OgloszenieFacadeInMOS() {
        super(Ogloszenie.class);
    }


    @Override
    public Ogloszenie findById(Long id) {
        Query q = em.createNamedQuery("Ogloszenie.findById");
        q.setParameter("id", id);
        return (Ogloszenie) q.getSingleResult();
    }

}
