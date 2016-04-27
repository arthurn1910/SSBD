/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
/**
 *
 * @author tomaszj
 */
@Named
@RequestScoped
public class ZmienHaslo{
     @Inject
    private UzytkownikSession sesjaKonta;
    
    private final Konto konto = new Konto();
    
    private String stareHaslo;
    private String noweHaslo;
    private String powtorzoneNoweHaslo;
    
    /**
     * Metoda pobierająca stare hasło
     */
    public String getStareHaslo() {
        return stareHaslo;
    }

    /**
     * Metoda pobierająca nowe hasło
     */
    public String getNoweHaslo() {
        return noweHaslo;
    }
    
    /**
     * Metoda pobierająca powtórzone nowe hasło 
     */
    public String getPowtorzNoweHaslo() {
        return powtorzoneNoweHaslo;
    }

    /**
     * Metoda ustawiajaca nowe hasło
     */
    public void setNoweHaslo(String noweHaslo) {
        this.noweHaslo = noweHaslo;
    }


    
     public String rejestrujKontoKlienta(){
          if(checkPasswordMatching() == false)
          {
            return null;
          }
         
           this.konto.setHaslo(noweHaslo);
           return "sukces";
     }
    
     /** Sprawdza czy dwa hasła znajdujące się w formularzach są identyczne
 * @return true, jeżeli hasła są identyczne; false, jeżeli hasła są różne
 */
    public boolean checkPasswordMatching(){
        if (!(noweHaslo.equals(powtorzoneNoweHaslo))) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            
            ResourceBundle rbl = ResourceBundle.getBundle("i18n.messages");
            FacesMessage fmsg = new FacesMessage(rbl.getString("passwords.not.matching"));

            fctx.addMessage(null, fmsg);
            return false;
        }
        return true;
    }

}