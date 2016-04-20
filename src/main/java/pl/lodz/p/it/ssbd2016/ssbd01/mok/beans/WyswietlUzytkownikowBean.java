/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
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
public class WyswietlUzytkownikowBean {
    
    public WyswietlUzytkownikowBean() {
    }
    
    @Inject
    private UzytkownikSession sesjaKonta;
    
    private List<Konto> konta;
    private DataModel<Konto> kontaDataModel;

    public DataModel<Konto> getKontaDataModel() {
        return kontaDataModel;
    }
    
    @PostConstruct
    private void initModel() {
        konta = sesjaKonta.pobierzWszystkieKonta();
        kontaDataModel = new ListDataModel<Konto>(konta);
    }
    
    public void potwierdzKonto() {
        sesjaKonta.potwierdzKonto(kontaDataModel.getRowData());
        initModel();
    }
    
    public void odblokujKonto() {
        sesjaKonta.odblokujKonto(kontaDataModel.getRowData());
        initModel();
    }
    
    public void zablokujKonto() {
        sesjaKonta.zablokujKonto(kontaDataModel.getRowData());
        initModel();
    }
    
}
