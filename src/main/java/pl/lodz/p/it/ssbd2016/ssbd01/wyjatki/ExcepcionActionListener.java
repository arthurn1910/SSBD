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
    Logger logger = Logger.getLogger(ExcepcionActionListener.class.getName());;
    
    @Override
    public void processAction(ActionEvent event){
        try{
            super.processAction(event);
        }catch(FacesException fe){
            /*Throwable tmp1=fe, tmp2=fe;
            while(tmp1.getCause()!=null){
                tmp2=tmp1;
                tmp1=tmp1.getCause();
            }*/
            logger.log(Level.SEVERE, "Złapany wyjątek w "+ExcepcionActionListener.class.getName(), fe.getCause());
            FacesContext fC=FacesContext.getCurrentInstance();
            Application app=fC.getApplication();
            NavigationHandler nH=app.getNavigationHandler();
            nH.handleNavigation(fC, null, "/wyjatki/wyjatek.xhtml");
            fC.renderResponse();
        }
    }
}
