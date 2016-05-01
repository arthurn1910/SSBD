package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Ziarno odpowiedzialne za wylogowanie/zakończenie sesji
 */
@Named
@RequestScoped
public class WylogujBean {
    
    /**
     * Metoda zwracająca zewnętrzny kontekst
     * @return  zewnętrzny kontekst
     */
    public static ExternalContext getContext(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }
    
    /**
     * Metoda unieważniająca obecną sesję
     * @return  przekierowanie do strony głównej
     */
    public String wyloguj() {
        getContext().invalidateSession();
        return "index";
    }
}
