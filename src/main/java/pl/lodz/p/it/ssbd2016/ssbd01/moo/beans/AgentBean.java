/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class AgentBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    private Ogloszenie ogloszenie;
    
    
    private DataModel<Konto> agenci;

    /**
     * Metoda inizjalizująca dostępne poziomy dostępu i konta, dla którego
     * będą modyfikowane poziomy dostępu
     */
    @PostConstruct
    private void initModel(){
        agenci = new ListDataModel<Konto>(ogloszenieSession.getAgenci());
        ogloszenie = ogloszenieSession.getOgloszenieDoWyswietlenia();
    }
    
   /***
     * Metoda wywołuje metodę przydzielAgentaDoOgloszenia w OgloszenieSession przekazując jej parametry Ogloszenie, Konto
     * Stowrzył Radosław Pawlaczyk
     * MOO 6
     * MOO 7
     */
    public void przydzielAgenta(){
        ogloszenieSession.przydzielAgentaDoOgloszenia(ogloszenie, agenci.getRowData());
        initModel();
    }
    
    /**
     * Metoda dla widoku definiująca czy ogloszenie posiada danego agenta. 
     * Zmienia dostępne klawisze między dołącz i odłącz
     * @return  decyzcja czy konto posiada aktywny poziom dostępu
     */
    public boolean czyPosiadaAgenta(){
        return ogloszenieSession.czyPosiadaAgenta(ogloszenie, agenci.getRowData());
    }
    
    /**
     * Metoda dla widoku definiująca czy ogloszenie posiada jakiegos agenta. 
     * Zmienia dostępne klawisze między dołącz i odłącz
     * @return  decyzcja czy konto posiada aktywny poziom dostępu
     */
    public boolean czyPosiadaJakiegosAgenta(){
        return ogloszenieSession.czyPosiadaJakiegosAgenta(ogloszenie);
    }
    
    // Gettery i Settery    
    
    public DataModel<Konto> getAgenci() {
        return agenci;
    }    
}
