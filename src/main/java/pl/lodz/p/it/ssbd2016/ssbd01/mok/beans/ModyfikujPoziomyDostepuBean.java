/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

/**
 * Klasa ta jest wykorzystywana do dołaczenai i sprawdzenia poziomów dostępu użytkownika
 * @author rpawlaczyk
 */

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

@Named
@RequestScoped
public class ModyfikujPoziomyDostepuBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto;
    
    
    private DataModel<String> poziomyDostepuDataModel;

    public DataModel<String> getPoziomyDostepuDataModel() {
        return poziomyDostepuDataModel;
    }
    
    @PostConstruct
    private void initModel() {
        poziomyDostepuDataModel = new ListDataModel<String>(PoziomDostepuManager.getPoziomyDostepu());
        konto = uzytkownikSession.getWybraneKonto();
    }
    
    public void dodajPoziomDostepu() {
        try {
            uzytkownikSession.dodajPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        } catch (Exception e) {
            
        } 
        initModel();
    }
    
    public void odlaczPoziomDostepu() {
        try {
            uzytkownikSession.odlaczPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        } catch (Exception e) {
            
        }        
        initModel();
    }
    
    public boolean czyPosiadaAktywnyPoziomDostepu() {
        return PoziomDostepuManager.czyPosiadaAktywnyPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
    }
}
