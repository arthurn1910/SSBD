/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class Autoryzacja {
    
    
    public boolean czyAdministrator()
    {
        return sprawdzRole(PoziomDostepuManager.getPoziomyDostepu().get(0));
    }
    
    public boolean czyAgent()
    {
        return sprawdzRole(PoziomDostepuManager.getPoziomyDostepu().get(2));
    }
    
    public boolean czyKlient()
    {
        return sprawdzRole(PoziomDostepuManager.getPoziomyDostepu().get(3));
    }
    
    public boolean czyMenadzer()
    {
        return sprawdzRole(PoziomDostepuManager.getPoziomyDostepu().get(1));
    }
    
    private boolean sprawdzRole(String role) {
            if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role)) {
                return true;
            }
        return false;
    }
}
