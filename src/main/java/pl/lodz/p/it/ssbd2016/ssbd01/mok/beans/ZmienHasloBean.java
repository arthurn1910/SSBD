package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;


import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by Kamil Rogowski on 26.04.2016.
 * Obsługa zmiany hasła przez użytkownika i admina
 */
@ManagedBean
@RequestScoped
public class ZmienHasloBean {

    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private String nowePowtorzoneHaslo;
    private String noweHaslo;
    private String stareHaslo;
    private Konto konto;

    @PostConstruct
    private void initKonto() {
        konto = uzytkownikSession.getWybraneKonto();
    }

    /** 
     * Sprawdza czy dwa hasła znajdujące się w formularzach są identyczne
     * @return true, jeżeli hasła są identyczne; false, jeżeli hasła są różne
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
    
    public void zmienMojeHaslo() {
        if (checkPasswordMatching()) {
            uzytkownikSession.zmienMojeHaslo(noweHaslo, stareHaslo);
        }
    }

    /**
     * sprawdza czy nowe hasło jest identyczne, jak powtórzone nowe hasło, przypadek gdy admin zmienia nam hasło
     * @return true jeśli operacja zakończona sukcesem, false, jeśli nie
     */
    public void zmienHaslo() {
        if (checkPasswordMatching()) {
            uzytkownikSession.zmienHaslo(konto, noweHaslo);
        }
    }

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
