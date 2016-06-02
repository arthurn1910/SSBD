/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.fasady;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

/**
 *
 * @author java
 */
@Stateless
public class OgloszenieFacade extends AbstractFacade<Ogloszenie> implements OgloszenieFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OgloszenieFacade() {
        super(Ogloszenie.class);
    }

    @Override
    public Ogloszenie znajdzPoID(Long ID) {
        Query q = em.createNamedQuery("Ogloszenie.findById");
        q.setParameter("id", ID);
        return (Ogloszenie) q.getSingleResult();
    }
    
    @Override
    public void create(Ogloszenie ogloszenie) {
        super.create(ogloszenie);
    }

    @Override
    public void edit(Ogloszenie ogloszenie) {
        super.edit(ogloszenie);
    }

    @Override
    public Ogloszenie find(Object id) {
        return super.find(id);
    }

    @Override
    public List<Ogloszenie> findAll() {
        return super.findAll();
    }
}
