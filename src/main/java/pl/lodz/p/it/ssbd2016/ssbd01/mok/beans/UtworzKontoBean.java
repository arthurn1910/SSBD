/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.Arrays;
import java.util.Collections;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

/**
 *
 * @author Patryk
 */
@Named
@RequestScoped  
public class UtworzKontoBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto = new Konto();
    private String powtorzoneHaslo;
    
    private String[] wybranePoziomy;
    
    public Konto getKonto() {
        return konto;
    }

    public String[] getWybranePoziomy() {
        return wybranePoziomy;
    }

    public void setWybranePoziomy(String[] wybranePoziomy) {
        this.wybranePoziomy = wybranePoziomy;
    }
    
    
    public String getPotórzoneHaslo() {
        return powtorzoneHaslo;
    }

    public void setPotórzoneHaslo(String potórzoneHaslo) {
        this.powtorzoneHaslo = potórzoneHaslo;
    }
    
    public String[] pobierzPoziomyDostepu() {
        return PoziomDostepuManager.getPoziomyDostepu().toArray(new String[0]);
    }
            
    public void rejestrujKontoKlienta() throws Exception {
        if (checkPasswordMatching() && sprawdzPoziomyDostepu()) {
            uzytkownikSession.utworzKonto(konto, Arrays.asList(wybranePoziomy));
        }
    }
    
    public boolean sprawdzPoziomyDostepu(){
        if (wybranePoziomy.length == 0) {
            FacesMessage message = new FacesMessage("Wybierz conajmniej 1");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:poziomy", message);
            return false;
        } else if (wybranePoziomy.length > 0 && !PoziomDostepuManager.czyPoprawnaKombinacjaPoziomowDostepu(Arrays.asList(wybranePoziomy))) {
            FacesMessage message = new FacesMessage("Poziomy sie wykluczaja");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:poziomy", message);
            return false;
        }
        return true;
    }
    
    public boolean checkPasswordMatching() {
        if (!(konto.getHaslo().equals(powtorzoneHaslo))) {
            FacesMessage message = new FacesMessage("passwords dont match");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:hasloPotworzone", message);
            return false;
        }
        return true;
    }
}
