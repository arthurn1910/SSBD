package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Obsługa zmiany hasła przez użytkownika i admina
 */
@Named
@RequestScoped
public class EdytujHasloBean {

    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private String nowePowtorzoneHaslo;
    private String noweHaslo;
    private String stareHaslo;

    /**
     * Metoda sprawdzająca czy podane hasła są identyczne
     * @return  decyzja czy hasła są identyczne
     */
    public boolean checkPasswordMatching() {
        if (!(noweHaslo.equals(nowePowtorzoneHaslo))) {
            FacesMessage message = new FacesMessage("passwords dont match");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:powtorzoneHaslo", message);
            return false;
        }
        return true;
    }
    
    /**
     * Handler dla przyciku potwierdź. Metoda zmienia hasło dla obecnie
     * zalogowanego użytkownika i przekierowuje do szczegółów danego konta
     * @return  przekierowanie do szczegółów konta
     * @throws Exception 
     */
    public String zmienMojeHaslo() throws Exception {
        if (checkPasswordMatching()) {
            uzytkownikSession.zmienMojeHaslo(noweHaslo, stareHaslo);
        }
        return "wyswietlSzczegolyKonta";
    }

    /**
     * Handler dla przyciku potwierdź. Metoda zmienia hasło dla wybranego konta
     * i przekierowuje do szczegółów danego konta
     * @return  przekierowanie do szczegółów konta
     * @throws Exception 
     */
    public String zmienHaslo() throws Exception {
        if (checkPasswordMatching()) {
            uzytkownikSession.zmienHaslo(noweHaslo);
        }
        return "wyswietlSzczegolyKonta";
    }

    // Gettery i Settery
    
    public String getStareHaslo() {
        return stareHaslo;
    }

    public void setStareHaslo(String stareHaslo) {
        this.stareHaslo = stareHaslo;
    }

    public String getNoweHaslo() {
        return noweHaslo;
    }

    public void setNoweHaslo(String noweHaslo) {
        this.noweHaslo = noweHaslo;
    }

    public String getNowePowtorzoneHaslo() {
        return nowePowtorzoneHaslo;
    }

    public void setNowePowtorzoneHaslo(String nowePowtorzoneHaslo) {
        this.nowePowtorzoneHaslo = nowePowtorzoneHaslo;
    }
}
