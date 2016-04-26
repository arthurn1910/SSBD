/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;

/**
 *
 * @author rpawlaczyk
 */
@SessionScoped
public class LogowanieSession implements Serializable {
    @EJB
    private MOKEndpointLocal MOKEndpoint;
    
    /***
     * Funkcjia wywołująca funkcje zaloguj z MOKEndpoint
     * @param login
     * @param haslo
     * @return
     */
    String zaloguj(String login, String haslo){
        if(MOKEndpoint.zaloguj(login,haslo)==true)
            return "zalogowano";
        return "errorLogin";
    }
}
