/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.ssbd2016.ssbd01.fasady.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        
    @Override
    public Konto znajdzPoLoginie(String login) {
        Query q = em.createNamedQuery("Konto.findByLogin");
        q.setParameter("login", login);
        return (Konto) q.getSingleResult();
    }
    
    @Override
    public List<Konto> pobierzWszystkieNiepotwierdzoneKonta() {        
        Query query = em.createNamedQuery("Konto.findByPotwierdzone");
        query.setParameter("potwierdzone", false);
        List<Konto> listaKontNiepotwierdzonych = 
                (List<Konto>) query.getResultList();
        if (listaKontNiepotwierdzonych == null) {
            return new ArrayList<Konto>();
        }
        return listaKontNiepotwierdzonych;
    }

    @Override
    public List<Konto> znajdzPodobne(Konto konto) {
        Query query = em.createNamedQuery("Konto.findSimilar");
        query.setParameter("imie", konto.getImie());
        query.setParameter("nazwisko", konto.getNazwisko());
        query.setParameter("email", konto.getEmail());
        query.setParameter("telefon", konto.getTelefon());
        query.setParameter("potwierdzone", konto.getPotwierdzone());
        query.setParameter("aktywne", konto.getAktywne());
        List<Konto> listaKontPodobnych = (List<Konto>) query.getResultList();
        
        if (listaKontPodobnych == null) {
            return new ArrayList<Konto>();
        }
        return listaKontPodobnych;
    }
    
}
