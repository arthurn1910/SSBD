/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady;

import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

/**
 *
 * @author java
 */
@Stateless
public class PoziomDostepuFacade extends AbstractFacade<PoziomDostepu> implements PoziomDostepuFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PoziomDostepuFacade() {
        super(PoziomDostepu.class);
    }
    
}
