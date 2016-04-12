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
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author java
 */
@Stateless
public class KontoFacade extends AbstractFacade<Konto> implements KontoFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KontoFacade() {
        super(Konto.class);
    }
    
}
