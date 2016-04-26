/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

/**
 *
 * @author java
 */
@Named
@RequestScoped 
public class WyswietlUzytkownikaBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto;

    private DataModel<String> poziomyDostepuDataModel;

    public DataModel<String> getPoziomyDostepuDataModel() {
        return poziomyDostepuDataModel;
    }
    
    public Konto getKonto() {
        return konto;
    }
        
    @PostConstruct
    private void initKonto() {
        poziomyDostepuDataModel = new ListDataModel<String>(PoziomDostepuManager.getPoziomyDostepu());
        konto = uzytkownikSession.pobierzUrzytkownika("test1");
        if (konto == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("mokStronaBledu.xhtml");
            } catch (IOException e) {
                
            }
        }
    }
    
    public void dodajPoziomDostepu() {
        try {
            uzytkownikSession.dodajPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        } catch (Exception e) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("mokStronaBledu.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(WyswietlUzytkownikaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        initKonto();
    }
    
    public void odlaczPoziomDostepu() {
        try {
            uzytkownikSession.odlaczPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        } catch (Exception e) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("mokStronaBledu.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(WyswietlUzytkownikaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        initKonto();
    }
}
