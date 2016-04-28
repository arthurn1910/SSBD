package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;


import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        konto = uzytkownikSession.znajdzPoLoginie("kontoC");
    }

    /**
     * sprawdza czy nowe hasło jest identyczne, jak powtórzone nowe hasło, przypadek gdy zmieniamy swoje hasło
     *
     */
    public boolean zmienMojeHaslo() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (noweHaslo.equals(nowePowtorzoneHaslo)) {
            uzytkownikSession.zmienMojeHaslo(noweHaslo, stareHaslo);
            return true;
        }
        return false;
    }

    /**
     * sprawdza czy nowe hasło jest identyczne, jak powtórzone nowe hasło, przypadek gdy admin zmienia nam hasło
     * @return true jeśli operacja zakończona sukcesem, false, jeśli nie
     */
    public boolean zmienHaslo() {

        if (noweHaslo.equals(nowePowtorzoneHaslo)) {
            try {
                uzytkownikSession.zmienHaslo(konto, noweHaslo);
                return true;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ZmienHasloBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ZmienHasloBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
     /*
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void zmienMojeHaslo2() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (this.czyHaslaSiezgadzaja())
            uzytkownikSession.zmienMojeHaslo(noweHaslo, stareHaslo);
    }
    
  /** Sprawdza czy dwa hasła znajdujące się w formularzach są identyczne
 * @return true, jeżeli hasła są identyczne; false, jeżeli hasła są różne
 */
    public boolean czyHaslaSiezgadzaja(){
        if (!(noweHaslo.equals(nowePowtorzoneHaslo))) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            
            ResourceBundle rbl = ResourceBundle.getBundle("i18n.messages");
            FacesMessage fmsg = new FacesMessage(rbl.getString("passwords.not.matching"));

            fctx.addMessage(null, fmsg);
            return false;
        }
        return true;
    }


    /**
     * sprawdza czy nowe hasło jest identyczne, jak powtórzone nowe hasło, przypadek gdy admin zmienia nam hasło
     *
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void zmienHaslo2() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (noweHaslo.equals(nowePowtorzoneHaslo))
            uzytkownikSession.zmienHaslo(konto, noweHaslo);
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
