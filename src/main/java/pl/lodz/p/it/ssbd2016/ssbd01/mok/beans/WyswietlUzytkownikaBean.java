/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author java
 */
@Named
@RequestScoped 
public class WyswietlUzytkownikaBean {
    @Inject
    private UzytkownikSession sesjaKonta;
    
    private Konto konto;

    public Konto getKonto() {
        return konto;
    }
        
    @PostConstruct
    private void initKonto() {
        konto = sesjaKonta.pobierzUrzytkownika("Szprync");
        if (konto == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("mokStronaBledu.xhtml");
            } catch (IOException e) {
                
            }
        }
    }
}
