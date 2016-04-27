/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;
/*
 *
 * @author Patryk
 */
@SessionScoped
public class UzytkownikSession implements Serializable {
    @EJB
    private MOKEndpointLocal MOKEndpoint;
    private Konto kontoUzytkownika;

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
}
