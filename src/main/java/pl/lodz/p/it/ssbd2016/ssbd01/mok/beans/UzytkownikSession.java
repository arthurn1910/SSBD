/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;

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
    
    private Konto wybraneKonto;
    
    private Konto kontoEdytuj;
    
    private DataModel<Konto> kontaDataModel;

    public DataModel<Konto> getKontaDataModel() {
        return kontaDataModel;
    }

    public void setKontaDataModel(DataModel<Konto> kontaDataModel) {
        this.kontaDataModel = kontaDataModel;
    }  
    
    /**
     * Rejestruje konto, nadając mu poziom dostępu klienta
     * @param  k  konto, które ma zostać zarejestrowane
     */
    public void rejestrujKontoKlienta(Konto k) {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo(k.getHaslo()); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(Date.from(Instant.now()));
        kontoRejestracja.setTelefon(k.getTelefon());
       
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja);
    }
        
    
    /**
     * Rejestruje konto, nadając mu jeden z poziomów dostępu (klient, agent, menadzer, administrator)
     * @param  k  konto, które ma zostać zarejestrowane
     * @param  poziomyDostepu  poziomy dostępu, który ma mieć nowo tworzone konto
     */
    public void utworzKonto(Konto k, List<String> poziomyDostepu)
    {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo(k.getHaslo()); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(Date.from(Instant.now()));
        kontoRejestracja.setTelefon(k.getTelefon());
        
        MOKEndpoint.utworzKonto(kontoRejestracja, poziomyDostepu);
    }
    
    /**
     * Pobiera z endpointa listę kont, których dane pasują do wzorców zawartych w obiekcie Konto, przekazywanym jako parametr
     * @param  k  konto, które zawiera wzorce
     * @return lista kont spełniających wymagania dotyczące wzorców
     */    
    List<Konto> pobierzWszystkieKonta() {
        return MOKEndpoint.pobierzWszystkieKonta();
    }
        
    public void potwierdzKonto(Konto konto) {
        MOKEndpoint.potwierdzKonto(konto);
    }

    public void odblokujKonto(Konto konto) {
        MOKEndpoint.odblokujKonto(konto);
    }

    public void zablokujKonto(Konto konto) {
        MOKEndpoint.zablokujKonto(konto);
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
        setKontoEdytuj(MOKEndpoint.pobierzKontoDoEdycji(konto));
    }

    /**
     * zapisuje kopię konta po edycji
     */
    public void zapiszKontoPoEdycji() {
        MOKEndpoint.zapiszKontoPoEdycji(kontoEdytuj);
    }

    /**
     * Przekazuje dane w postaci jawnej do endpointa
     * @param noweHaslo  nowe hasło w postaci jawnej
     * @param stareHaslo stare hasło w postaci jawnej
     */
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) {           
        MOKEndpoint.zmienMojeHaslo(noweHaslo, stareHaslo);
    }

    /**
     * @param konto     konto do zmiany
     * @param noweHaslo nowe hasło w postaci jawnej
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    
    List<Konto> pobierzWszystkieNiepotwierdzoneKonta() {
        return MOKEndpoint.pobierzWszystkieNiepotwierdzoneKonta();
    }

    Konto pobierzUrzytkownika(String login) {
        return MOKEndpoint.pobierzUzytkownika(login);
    }

    List<Konto> pobierzPodobneKonta(Konto konto) {
        return MOKEndpoint.pobierzPodobneKonta(konto);
    }

    void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        MOKEndpoint.dodajPoziomDostepu(konto, poziom);
    }

    void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        MOKEndpoint.odlaczPoziomDostepu(konto, poziom);
    }
    /**
     * Przekazuje konto i hasło do endpointa
     * @param konto     konto do zmiany
     * @param noweHaslo nowe hasło w postaci jawnej
     */
    public void zmienHaslo(Konto konto, String noweHaslo) {
        MOKEndpoint.zmienHaslo(konto, noweHaslo);
    }

    public Konto getWybraneKonto() {
        return MOKEndpoint.pobierzUzytkownika(wybraneKonto.getLogin());
    }

    public void setWybraneKonto(Konto wybraneKonto) {
        this.wybraneKonto = wybraneKonto;
    }
}
