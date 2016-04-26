package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;


import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
        konto = uzytkownikSession.znajdzPoLoginie("kontoA");
    }

    /**
     * sprawdza czy nowe hasło jest identyczne, jak powtórzone nowe hasło, przypadek gdy zmieniamy swoje hasło
     *
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void zmienMojeHaslo() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (noweHaslo.equals(nowePowtorzoneHaslo))
            uzytkownikSession.zmienMojeHaslo(noweHaslo, stareHaslo);
    }

    /**
     * sprawdza czy nowe hasło jest identyczne, jak powtórzone nowe hasło, przypadek gdy admin zmienia nam hasło
     *
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void zmienHaslo() throws UnsupportedEncodingException, NoSuchAlgorithmException {

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
