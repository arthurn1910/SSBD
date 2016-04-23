/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;

/**
 *
 * @author java
 */
@Stateless
public class KontoManager implements KontoManagerLocal {

    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @Override
    public List<Konto> znajdzPodobne(Konto konto) {
        Konto kontoWyszukaj = new Konto();
        if (konto.getImie() == null) {
            kontoWyszukaj.setImie("%%");
        } else {
            kontoWyszukaj.setImie("%" + konto.getImie() + "%");
        }
        
        if (konto.getNazwisko()== null) {
            kontoWyszukaj.setNazwisko("%%");
        } else {
            kontoWyszukaj.setNazwisko("%" + konto.getNazwisko()+ "%");
        }
        
        if (konto.getEmail()== null) {
            kontoWyszukaj.setEmail("%%");
        } else {
            kontoWyszukaj.setEmail("%" + konto.getEmail()+ "%");
        }
        
        if (konto.getTelefon()== null) {
            kontoWyszukaj.setTelefon("%%");
        } else {
            kontoWyszukaj.setTelefon("%" + konto.getTelefon()+ "%");
        }
        kontoWyszukaj.setAktywne(konto.getAktywne());
        kontoWyszukaj.setPotwierdzone(konto.getPotwierdzone());
        
        return kontoFacade.znajdzPodobne(kontoWyszukaj);
    }
    
}
