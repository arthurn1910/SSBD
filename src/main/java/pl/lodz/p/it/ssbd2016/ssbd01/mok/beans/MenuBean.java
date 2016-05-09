package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;

/**
 * Ziarno odpowiedzialne za wylogowanie/zakończenie sesji
 */
@Named
@RequestScoped
public class MenuBean {
    
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    String login;
    String poziomyDostepu;
    boolean zalogowany;
    
    /**
    * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
    * konto przez konto użytkownika obecnie zalogowanego
    */
    @PostConstruct
    public void initModel() {
        String str = getContext().getRemoteUser();
        if (str != null) {
            Konto konto = uzytkownikSession.znajdzPoLoginie(str);
            login = konto.getLogin();
            poziomyDostepu = "";
            for (PoziomDostepu poziom:konto.getPoziomDostepuCollection()) {
                if (poziom.getAktywny()) {
                    poziomyDostepu += poziom.getPoziom() + ", ";
                }
            }
            zalogowany = true;
        } else {
            zalogowany = false;
        }
    }
    
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
    
    public String getLogin() {
        return login;
    }

    public String getPoziomyDostepu() {
        return poziomyDostepu;
    }

    public boolean isZalogowany() {
        return zalogowany;
    }
}