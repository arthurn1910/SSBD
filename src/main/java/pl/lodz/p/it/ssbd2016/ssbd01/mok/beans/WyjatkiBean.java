/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class WyjatkiBean {
    private String miejsce, plik, poziom, operacja, metoda;
    private int status;
    
    
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    public String getMiejsceBladDeSerializacjiObiektu(){
        return "aaaaa";
        //return uzytkownikSession.getBladDeSerializajiObiektu().getMiejsce();
    }
    
    public String getMiejsceBladPliku(){
        return uzytkownikSession.getBladPliku().getMiejsce();
    }
    
    public String getPlikBladPliku(){
        return uzytkownikSession.getBladPliku().getPlik();
    }
    
    public String getMiejsceBladPoziomDostepu(){
        return uzytkownikSession.getBladPoziomDostepu().getMiejsce();
    }
    
    public String getPoziomBladPoziomDostepu(){
        return uzytkownikSession.getBladPoziomDostepu().getPoziom();
    }
    
    public String getStatusBladPoziomDostepu(){
        int a=uzytkownikSession.getBladPoziomDostepu().getStatus();
        if(a==1)
            return "dołaczanie";
        else if(a==0)
            return "sprawdzanie";
        else
            return "odłaczanie";
    }
    
    public String getMiejsceBrakAlgorytmuKodowanialadWywolania(){
        return uzytkownikSession.getBrakAlgorytmuKodowania().getMiejsce();
    }
    
    public String getMiejsceBrakKontaDoEdycji(){
        return uzytkownikSession.getBrakKontaDoEdycji().getMiejsce();
    }
    
    public String getMiejsceKontoNiezgodneWczytanym(){
        return uzytkownikSession.getKontoNiezgodneWczytanym().getMiejsce();
    }
    
    public String getMiejsceNieobslugiwaneKodowanie(){
        return uzytkownikSession.getNieobslugiwaneKodowanie().getMiejsce();
    }
    
    public String getMiejsceNiezgodneHasla(){
        return uzytkownikSession.getNiezgodneHasla().getMiejsce();
    }
    
    public String getMiejsceNiezgodnyLogin(){
        return uzytkownikSession.getNiezgodnyLogin().getMiejsce();
    }
    
    public String getMiejscePoziomDostepuNieIstnieje(){
        return uzytkownikSession.getPoziomDostepuNieIstnieje().getMiejsce();
    }
    
    public String getPoziomPoziomDostepuNieIstnieje(){
        return uzytkownikSession.getPoziomDostepuNieIstnieje().getPoziom();
    }
}
