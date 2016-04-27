/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.TypOgloszenia;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;

/**
 *
 * @author java
 */
@Stateless
public class TypOgloszeniaFacade extends AbstractFacade<TypOgloszenia> implements TypOgloszeniaFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypOgloszeniaFacade() {
        super(TypOgloszenia.class);
    }

    @Override
    public TypOgloszenia znajdzPoNazwie(String typ) {
        Query q = em.createNamedQuery("TypOgloszenia.findByNazwa");
        q.setParameter("nazwa", typ);
        return (TypOgloszenia) q.getSingleResult();
    }
    
}