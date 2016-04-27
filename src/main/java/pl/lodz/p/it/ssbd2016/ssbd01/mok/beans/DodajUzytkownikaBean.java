package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author Patryk
 */
@Named
@RequestScoped  
public class DodajUzytkownikaBean {
    @Inject
    private UzytkownikSession sesjaKonta;
    
    private final Konto konto = new Konto();
    private String imie;
    private String nazwisko;
    private String login;
    private String haslo;
    private String powtorzHaslo;
    private String telefon;
    private String email;
    private String rola;
    
    public String getImie() {
        return imie;
    }  
    public void setImie(String i) {
        this.imie = i;
    }
    
    public String getNazwisko() {
        return nazwisko;
    }  
    public void setNazwisko(String n) {
        this.nazwisko = n;
    }
    
    public String getLogin() {
        return login;
    }  
    public void setLogin(String l) {
        this.login = l;
    }
    
    public String getHaslo() {
        return haslo;
    }  
    public void setHaslo(String h) {
        this.haslo = h;
    }
    
    public String getPowtorzHaslo() {
        return powtorzHaslo;
    }  
    public void setPowtorzHaslo(String ph) {
        this.powtorzHaslo = ph;
    }
    
    public String getTelefon() {
        return telefon;
    }  
    public void setTelefon(String t) {
        this.telefon = t;
    }
    
    public String getEmail() {
        return email;
    }  
    public void setEmail(String e) {
        this.email = e;
    }
    
    public String getRola() {
        return rola;
    }  
    public void setRola(String r) {
        this.rola = r;
    }

    public Konto getKonto() {
        return konto;
    }
    
  /**
 * Sprawdza czy dwa hasła znajdujące się w formularzach są identyczne
 * @return true, jeżeli hasła są identyczne; false, jeżeli hasła są różne
 */
    public boolean checkPasswordMatching(){
        if (!(haslo.equals(powtorzHaslo))) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            
            ResourceBundle rbl = ResourceBundle.getBundle("i18n.messages");
            FacesMessage fmsg = new FacesMessage(rbl.getString("passwords.not.matching"));

            fctx.addMessage(null, fmsg);
            return false;
        }
        return true;
    }

  /**
 * Rejestruje konto z poziomem dostępu klienta, metoda wywoływana po naciśnięciu odpowiedniego przycisku na formularzu
 * @return "success", jeżeli konto zostało zarejestrowane; null, jeżeli hasła podane w formularzu są różne
 */
    public String rejestrujKontoKlienta() {
        if(checkPasswordMatching() == false)
            return null;
        this.konto.setImie(this.imie);
        this.konto.setNazwisko(this.nazwisko);
        this.konto.setHaslo(this.haslo);
        this.konto.setEmail(this.email);
        this.konto.setLogin(this.login);
        this.konto.setTelefon(this.telefon);
        
        sesjaKonta.rejestrujKlienta(konto);
        return "success";
    }
    
  /**
 * Rejestruje konto z poziomem dostępu wskazanym w formularzu, metoda
 * wywoływana po naciśnięciu odpowiedniego przycisku na formularzu
 * @return "success", jeżeli konto zostało zarejestrowane; null, jeżeli hasła podane w formularzu są różne
 */
    public String utworzKonto() {
        if(checkPasswordMatching() == false)
            return null;
        this.konto.setImie(this.imie);
        this.konto.setNazwisko(this.nazwisko);
        this.konto.setHaslo(this.haslo);
        this.konto.setEmail(this.email);
        this.konto.setLogin(this.login);
        this.konto.setTelefon(this.telefon);
        
        sesjaKonta.utworzKonto(konto, rola);
        return "success";
    }
}
