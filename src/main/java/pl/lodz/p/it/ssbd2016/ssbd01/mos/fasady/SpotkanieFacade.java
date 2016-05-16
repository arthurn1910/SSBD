/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.fasady;

import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;

/**
 *
 * @author java
 */
@Stateless
public class SpotkanieFacade extends AbstractFacade<Spotkanie> implements SpotkanieFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpotkanieFacade() {
        super(Spotkanie.class);
    }

    @Override
    public List<Spotkanie> pobierzSpotkaniaUzytkownika(Konto konto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
