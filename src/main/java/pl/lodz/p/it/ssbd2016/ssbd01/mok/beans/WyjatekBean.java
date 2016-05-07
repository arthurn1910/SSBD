/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Obsługa widoku wyjatek.xhtml
 * @author java
 */
@Named
@RequestScoped
public class WyjatekBean {
    private String aaa="bladPliku";
    @Inject
    private UzytkownikSession uzytkownikSession;

    public String getWiadomosc() {
        return "aa";//uzytkownikSession.getException().getMessage();
    }

    public String getAaa() {
        return aaa;
    }
    
    
    
   
}
