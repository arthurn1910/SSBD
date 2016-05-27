/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.utils;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.beans.OgloszenieSession;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

/**
 * Klasa wykorzystana do określenia czy zalogowany użytkownik może mieć dostęp do aktualnie oglądanego ogłoszenia
 */
@Named
@RequestScoped
public class AutoryzacjaMOO {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    @Resource
    private SessionContext sessionContext;
    
    /**
     * Sprawdza czy użytkownik jest właścicielem lub agentem aktualnie otwartego ogłoszenia
     * @return
     */
    public boolean czyMojeOgloszenie()
    {
        Ogloszenie otwarte = ogloszenieSession.getOgloszenieDoWyswietlenia();
        String loginKonta = sessionContext.getCallerPrincipal().getName();
        if(otwarte.getIdWlasciciela().getLogin().equals(loginKonta) == false && otwarte.getIdAgenta().getLogin().equals(loginKonta) == false)
            return false;
        return true;
    }
}
