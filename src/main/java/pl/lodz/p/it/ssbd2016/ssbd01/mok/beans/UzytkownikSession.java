/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
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
import javax.enterprise.context.SessionScoped;
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

    private Konto kontoUzytkownika;
    private Konto kontoEdytuj;
    /***
     * Funkcja zwracająca kontoUzytkownika
     * @return 
     */
    public Konto getKontoUzytkownika() {
        ustawKontoUzytkownika();
        return kontoUzytkownika;
    }
    /***
     * Metoda ustawiajaca kontoUzytkownika
     */
    public void ustawKontoUzytkownika(){
        kontoUzytkownika=MOKEndpoint.pobierzUzytkownika();
    }
    
    /**
     * Rejestruje konto, nadając mu poziom dostępu klienta
     * @param  k  konto, które ma zostać zarejestrowane
     */
    public void rejestrujKlienta2(Konto k) {
        
        
        //String cryptedPass = Arrays.toString(cryptedBytes);
        
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("blablabla"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie("Janusz");
        kontoRejestracja.setNazwisko("Andrzej");
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());
       
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja);
    }
    
    public void rejestrujKlienta(Konto k) {
        
        
        //String cryptedPass = Arrays.toString(cryptedBytes);
        
        
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("2cd002d71ed9bc76bd123059c6beccef"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());
        PoziomDostepu poziomDostepu = new PoziomDostepu();
        poziomDostepu.setPoziom("KLIENT");
        poziomDostepu.setAktywny(true);
        poziomDostepu.setKontoId(k);
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja, poziomDostepu);
    }
    
    
    /**
 * Rejestruje konto, nadając mu jeden z poziomów dostępu (klient, agent, menadzer, administrator)
 * @param  k  konto, które ma zostać zarejestrowane
 * @param  poziom  poziom dostępu, który ma mieć nowo tworzone konto
 */
    public void utworzKonto(Konto k, String poziom)
    {
        Konto kontoRejestracja = new Konto();
        kontoRejestracja.setLogin(k.getLogin());
        kontoRejestracja.setHaslo("2cd002d71ed9bc76bd123059c6beccef"); //!!! Hasło powinno być w postaci skrótu np. MD5!
        kontoRejestracja.setImie(k.getImie());
        kontoRejestracja.setNazwisko(k.getNazwisko());
        kontoRejestracja.setEmail(k.getEmail());
        kontoRejestracja.setDataUtworzenia(new Date());
        kontoRejestracja.setTelefon(k.getTelefon());
        PoziomDostepu poziomDostepu = new PoziomDostepu();
        poziomDostepu.setPoziom(poziom.toUpperCase());
        poziomDostepu.setAktywny(true);
        poziomDostepu.setKontoId(k);
        MOKEndpoint.rejestrujKontoKlienta(kontoRejestracja, poziomDostepu);
    }
    
  /**
 * Pobiera z endpointa listę kont, których dane pasują do wzorców zawartych w obiekcie Konto, przekazywanym jako parametr
 * @param  k  konto, które zawiera wzorce
 * @return lista kont spełniających wymagania dotyczące wzorców
 */
    
    List<Konto> pobierzWszystkieKonta() {
        return MOKEndpoint.pobierzWszystkieKonta();
    }
    
    /***
     * Metoda przekazująca parametr do metody potwierdzKonto w MOKEndpoint
     * @param rowData 
     */
    void potwierdzKonto() {
        MOKEndpoint.potwierdzKonto(kontoUzytkownika);
    }
    /***
     * Metoda przekazująca parametr do metody odblokujKonto w MOKEndpoint
     * @param rowData 
     */
    void odblokujKonto() {
        MOKEndpoint.odblokujKonto(kontoUzytkownika);
    }
    /***
     * Metoda przekazująca parametr do metody zablokujKonto w MOKEndpoint
     * @param rowData 
     */
    void zablokujKonto() {
        MOKEndpoint.zablokujKonto(kontoUzytkownika);
    }

    /***
     * Metoda przekazująca paraqmetr do metody dolaczPoziomAgent w MOKEndpoint
     * @param konto 
     */
    void dolaczPoziomAgent(){
        MOKEndpoint.dolaczPoziomAgent(kontoUzytkownika);
    }
    /***
     * Metoda przekazująca paraqmetr do metody dolaczPoziomMenadzer w MOKEndpoint
     * @param konto 
     */
    void dolaczPoziomMenadzer(){
         MOKEndpoint.dolaczPoziomMenadzer(kontoUzytkownika);
    }
    /***
     * Metoda przekazująca paraqmetr do metody dolaczPoziomAdministrator w MOKEndpoint
     * @param konto 
     */
    void dolaczPoziomAdministrator(){
         MOKEndpoint.dolaczPoziomAdministrator(kontoUzytkownika);
    }
    /***
     * Metoda przekazująca paraqmetr do metody odlaczPoziomAgent w MOKEndpoint
     * @param konto 
     */
    void odlaczPoziomAgent(){
        MOKEndpoint.odlaczPoziomAgent(kontoUzytkownika);
    }
    /***
     * Metoda przekazująca paraqmetr do metody odlaczPoziomMenadzer w MOKEndpoint
     * @param konto 
     */
    void odlaczPoziomMenadzer(){
        MOKEndpoint.odlaczPoziomMenadzer(kontoUzytkownika);
    }
    /***
     * Metoda przekazująca paraqmetr do metody odlaczPoziomAdministrator w MOKEndpoint
     * @param konto 
     */
    void odlaczPoziomAdministrator(){
        MOKEndpoint.odlaczPoziomAdministrator(kontoUzytkownika);
    }
    /***
     * Funkcja przekazująca paraqmetr do funkcji sprawdzPoziomAgent w MOKEndpoint pobierająca typ Boolean i zwracająca go 
     * @param konto
     * @return 
     */
    Boolean sprawdzPoziomAgent(){
        return MOKEndpoint.sprawdzPoziomAgent(kontoUzytkownika);
    }
    /***
     * Funkcja przekazująca paraqmetr do funkcji sprawdzPoziomMenadzer w MOKEndpoint pobierająca typ Boolean i zwracająca go 
     * @param konto
     * @return 
     */
    Boolean sprawdzPoziomMenadzer(){
        return MOKEndpoint.sprawdzPoziomMenadzer(kontoUzytkownika);
    }
    /***
     * Funkcja przekazująca paraqmetr do funkcji sprawdzPoziomAdministrator w MOKEndpoint pobierająca typ Boolean i zwracająca go 
     * @param konto
     * @return 
     */
    Boolean sprawdzPoziomAdministrator(){
        return MOKEndpoint.sprawdzPoziomAdministrator(kontoUzytkownika);
    }
    /***
     * Funkcja przekazująca parametr kontoUzytkownika do funkcji pobierzPoziomy w MOKEndpoint i zwracająca typ String
     * @return 
     */
    String pobierzPoziomy() {
        return MOKEndpoint.pobierzPoziomy(kontoUzytkownika);
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
        kontoEdytuj = MOKEndpoint.pobierzKontoDoEdycji(konto);
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
    public void zmienMojeHaslo(String noweHaslo, String stareHaslo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
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
}
