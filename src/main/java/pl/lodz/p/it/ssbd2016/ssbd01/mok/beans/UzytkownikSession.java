/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/*
 *
 * @author Patryk
 */
@SessionScoped
@ManagedBean(name = "uzytkownikSession")
public class UzytkownikSession implements Serializable {
    private static final Logger logger = Logger.getLogger(EdytujDaneBean.class.getName());

    @EJB
    private MOKEndpointLocal MOKEndpoint;

    private Konto kontoEdytuj;

    public Konto getKontoEdytuj() {
        return kontoEdytuj;
    }

    public void setKontoEdytuj(Konto kontoEdytuj) {
        this.kontoEdytuj = kontoEdytuj;
    }

    public void rejestrujKlienta(Konto k) {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("2cd002d71ed9bc76bd123059c6beccef"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie("Janusz");
        kontoRejestracja.setNazwisko("Pospolity");
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());


        PoziomDostepu poziomDostepu = new PoziomDostepu();
        poziomDostepu.setPoziom("AGENT");
        poziomDostepu.setAktywny(true);
        poziomDostepu.setKontoId(k);
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja, poziomDostepu);
    }

    List<Konto> pobierzWszystkieKonta() {
        return MOKEndpoint.pobierzWszystkieKonta();
    }

    public void potwierdzKonto(Konto rowData) {
        MOKEndpoint.potwierdzKonto(rowData);
    }

    public void odblokujKonto(Konto rowData) {
        MOKEndpoint.odblokujKonto(rowData);
    }

    public void zablokujKonto(Konto rowData) {
        MOKEndpoint.zablokujKonto(rowData);
    }

    public void zmienHaslo(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MOKEndpoint.zmienHaslo(noweHaslo, stareHaslo);

    }
}
