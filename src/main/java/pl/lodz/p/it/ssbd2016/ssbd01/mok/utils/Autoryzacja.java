/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class Autoryzacja {
    
    
    public boolean czyAdministrator() throws WyjatekSystemu
    {
        PoziomDostepuManager tmp=new PoziomDostepuManager();
        return sprawdzRole(tmp.getPoziomyDostepu().get(0));
    }
    
    public boolean czyAgent() throws WyjatekSystemu
    {
        PoziomDostepuManager tmp=new PoziomDostepuManager();
        return sprawdzRole(tmp.getPoziomyDostepu().get(2));
    }
    
    public boolean czyKlient() throws WyjatekSystemu
    {
        PoziomDostepuManager tmp=new PoziomDostepuManager();
        return sprawdzRole(tmp.getPoziomyDostepu().get(3));
    }
    
    public boolean czyMenadzer() throws WyjatekSystemu
    {
        PoziomDostepuManager tmp=new PoziomDostepuManager();
        return sprawdzRole(tmp.getPoziomyDostepu().get(1));
    }
    
    private boolean sprawdzRole(String role) {
            if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role)) {
                return true;
            }
        return false;
    }
}
