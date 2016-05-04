/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;
/**
 *
 * @author java
 */
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import com.sun.faces.application.ActionListenerImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
public class ExceptionsActionListener extends ActionListenerImpl implements ActionListener{

    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        try{
            super.processAction(event);
        }catch(BrakDostepu ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/brakDostepu.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }catch(NaruszenieUniq ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/naruszenieUniq.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(BladDeSerializacjiObiektu ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/bladDeSerializacjiObiektu.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(BladPoziomDostepu ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/bladPoziomDostepu.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(BladWywolania ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/bladWywolania.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(BrakAlgorytmuKodowania ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/brakAlgorytmuKodowania.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(BrakKontaDoEdycji ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/brakKontaDoEdycji.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(KontoNiezgodneWczytanym ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatkikontoNiezgodneWczytanym.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(NieobslugiwaneKodowanie ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/nieobslugiwaneKodowanie.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(NiewykonanaOperacja ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/niewykonanaOperacja.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(NiezgodneHasla ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/niezgodneHasla.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(NiezgodnyLogin ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/niezgodnyLogin.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(OgloszenieDeaktywowaneWczesniej ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/ogloszenieDeaktywowaneWczesniej.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(PoziomDostepuNieIstnieje ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/poziomDostepuNieIstnieje.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }catch(Exception ex){
            Logger lg=Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass()+": Wystąpił wyjątek: ", ex);
            FacesContext facesContext=FacesContext.getCurrentInstance();
            try {
                facesContext.getExternalContext().redirect("ssbd201601//wyjatki/naruszenieUniq.xhtml");
                facesContext.renderResponse();
            } catch (IOException ex1) {
                Logger.getLogger(ExceptionsActionListener.class.getName()).log(Level.SEVERE, null, ex1);
            }   
        }
    }
    
}
