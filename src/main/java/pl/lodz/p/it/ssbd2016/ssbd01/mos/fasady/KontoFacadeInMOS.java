/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author java
 */
@Stateless
public class KontoFacadeInMOS extends AbstractFacade<Konto> implements KontoFacadeLocalInMOS {

    @PersistenceContext(unitName = "ssbd01mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KontoFacadeInMOS() {
        super(Konto.class);
    }

    @Override
    public Konto znajdzPoLoginie(String login) {
        Query q = em.createNamedQuery("Konto.findByLogin");
        q.setParameter("login", login);
        return (Konto) q.getSingleResult();
    }
}
