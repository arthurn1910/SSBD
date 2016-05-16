/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.io.IOException;
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
    
    private Spotkanie spotkanie;
    /**
     * Zapisuje spotkanie po edycji
     */
    public void zapiszSwojeSpotkaniePoEdycji() {
        spotkanieSession.zapiszSpotkaniePoEdycji(spotkanie);
        
    }
    
    /**
     * pobiera spotkanie do edycji
     * @return 
     */
    public Spotkanie getSpotkanieEdytuj() throws WyjatekSystemu, IOException, ClassNotFoundException {
        return spotkanieSession.pobierzSpotkanieDoEdycji(spotkanie);
    }
}
