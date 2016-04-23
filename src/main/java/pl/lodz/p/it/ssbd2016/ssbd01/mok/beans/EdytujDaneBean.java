package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by Kamil Rogowski on 23.04.2016.
 */
@ManagedBean
@RequestScoped
public class EdytujDaneBean implements Serializable {
    private static final Logger logger = Logger.getLogger(EdytujDaneBean.class.getName());

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

    public void edytujDaneUzytkownika() {
        uzytkownikSession.edytujDaneUzytkownika(konto);
    }
    public void zmienMojeHaslo() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (noweHaslo.equals(nowePowtorzoneHaslo))
            uzytkownikSession.zmienHaslo(noweHaslo, stareHaslo);
    }

    public String getNoweHaslo() {
        return noweHaslo;
    }

    public void setNoweHaslo(String noweHaslo) {
        this.noweHaslo = noweHaslo;
    }

    public String getStareHaslo() {
        return stareHaslo;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setStareHaslo(String stareHaslo) {
        this.stareHaslo = stareHaslo;
    }

    public String getNowePowtorzoneHaslo() {
        return nowePowtorzoneHaslo;
    }

    public void setNowePowtorzoneHaslo(String nowePowtorzoneHaslo) {
        this.nowePowtorzoneHaslo = nowePowtorzoneHaslo;
    }
}
