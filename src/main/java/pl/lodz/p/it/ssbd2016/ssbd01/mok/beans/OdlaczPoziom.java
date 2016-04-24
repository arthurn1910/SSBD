/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

/**
 *
 * @author rpawlaczyk
 */

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class OdlaczPoziom {
    
    public String odlaczAgent(){
        if(false){
            return "odlaczPoziomE";
        }
        return "wyswietlInformacje";
    }
    public String odlaczMenadzer(){
        if(false){
            return "odlaczPoziomE";
        }
        return "wyswietlInformacje";
    }
    public String odlaczAdministrator(){
        if(true){
            return "odlaczPoziomE";
        }
        return "wyswietlInformacje";
    }
     
}
