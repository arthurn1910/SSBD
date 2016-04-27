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
import java.io.IOException;
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

    /**
     * Szuka konta o danym loginie
     *
     * @param login login konta
     * @return Konto
     */
    public Konto znajdzPoLoginie(String login) {
        return MOKEndpoint.znajdzPoLoginie(login);
    }

    public void setKontoEdytuj(Konto kontoEdytuj) {
        this.kontoEdytuj = kontoEdytuj;
    }

    public Konto getKontoEdytuj() {
        return kontoEdytuj;

    }

    /**
     * pobiera i zapisuje kopię konta do edycji
     *
     * @param konto konto do edycji
     */
    public void pobierzKontoDoEdycji(Konto konto) {
        try {
            kontoEdytuj = MOKEndpoint.pobierzKontoDoEdycji(konto);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * zapisuje kopię konta po edycji
     */
    public void zapiszKontoPoEdycji() {
        MOKEndpoint.zapiszKontoPoEdycji(kontoEdytuj);
    }

    /**
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MOKEndpoint.zmienMojeHaslo(noweHaslo, stareHaslo);
    }

    /**
     * @param konto     konto do zmiany
     * @param noweHaslo nowe hasło w postaci jawnej
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void zmienHaslo(Konto konto, String noweHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MOKEndpoint.zmienHaslo(konto, noweHaslo);
    }
}
