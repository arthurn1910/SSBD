/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

import com.sun.faces.application.ActionListenerImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 *
 * @author java
 */
public class ExcepcionActionListener extends ActionListenerImpl implements ActionListener{
    @Override
    public void processAction(ActionEvent event){
        try{
            super.processAction(event);
        }catch(FacesException fe){
            Logger lg=Logger.getLogger("javax.enterprice.system.conteiner.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ",fe);
            FacesContext fC=FacesContext.getCurrentInstance();
            Application app=fC.getApplication();
            NavigationHandler nH=app.getNavigationHandler();
            nH.handleNavigation(fC, null, "/wyjatki/brakKontaDoEdycji.xhtml");
            fC.renderResponse();
        }
    }
}
