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
public class DolaczPoziom {
    public String dolaczAgent(){
        if(false){
            return "dolaczPoziomE";
        }
        return "wyswietlInformacje";
    }
    public String dolaczMenadzer(){
        if(true){
            return "dolaczPoziomE";
        }
        return "wyswietlInformacje";
    }
    public String dolaczAdministrator(){
        if(false){
            return "dolaczPoziomE";
        }
        return "wyswietlInformacje";
    }
     
}
