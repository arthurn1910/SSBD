/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author Patryk
 */
@Named
@RequestScoped  
public class RejestracjaKontaKlientaBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto = new Konto();
    private String potórzoneHaslo;
    
    public Konto getKonto() {
        return konto;
    }

    public String getPotórzoneHaslo() {
        return potórzoneHaslo;
    }

    public void setPotórzoneHaslo(String potórzoneHaslo) {
        this.potórzoneHaslo = potórzoneHaslo;
    }
            
    public void rejestrujKontoKlienta() throws Exception {
        if (checkPasswordMatching()) {
            uzytkownikSession.rejestrujKontoKlienta(konto);
        }
    }
    
    public boolean checkPasswordMatching(){
        if (!(konto.getHaslo().equals(potórzoneHaslo))) {
            FacesMessage message = new FacesMessage("passwords dont match");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:hasloPotworzone", message);
            return false;
        }
        return true;
    }
}
