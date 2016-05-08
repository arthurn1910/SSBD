/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author java
 */
@Named
@RequestScoped
public class wyjatkiMOOBean {
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    private void getMiejsceOgloszenieDeaktywowaneWczesniej(){
        
    }
}
