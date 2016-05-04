/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Nieruchomosc;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class DodajOgloszenieBean {
    
    public DodajOgloszenieBean() {        
    }
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private final Ogloszenie ogloszenie = new Ogloszenie();

    private final Nieruchomosc nieruchomosc = new Nieruchomosc();
    
    public Ogloszenie getOgloszenie() {
        return ogloszenie;
    }

    public Nieruchomosc getNieruchomosc() {
        return nieruchomosc;
    }

    /**
     * MOO. 1 Dodaje ogłoszenie dla nieruchomości, Kamil Rogowski
     *
     * @return success
     */
    public String dodajOgloszenie() {
        ogloszenieSession.dodajOgloszenie(ogloszenie, nieruchomosc);
        return "success";
    }
}
