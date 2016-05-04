/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class Autoryzacja {
    
    public boolean czyAdministrator()
    {
        return sprawdzRole("ADMINISTRATOR");
    }
    
    private boolean sprawdzRole(String role) {
            if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role)) {
                return true;
            }
        return false;
    }
}
