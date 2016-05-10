package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

import com.sun.faces.application.ActionListenerImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletRequest;

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
            try {
                Throwable tmp1=fe, tmp2=fe;
                while(tmp1.getCause()!=null){
                tmp2=tmp1;
                tmp1=tmp1.getCause();
                }
                logger.log(Level.SEVERE, "Złapany wyjątek w "+ExcepcionActionListener.class.getName(), fe.getCause());
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                externalContext.redirect(origRequest.getContextPath() + "/wyjatki/wyjatek.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ExcepcionActionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
