/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.UzytkownikSession;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class EdytujSwojeSpotkanieBean {
    @Inject
    private SpotkanieSession spotkanieSession;
    
    /**
     * Zapisuje spotkanie po edycji
     * @return 
     */
    public String zapiszSwojeSpotkaniePoEdycji() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
    

    /**
     * pobiera spotkanie do edycji
     */
    public Spotkanie getSpotkanieEdytuj() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
}
