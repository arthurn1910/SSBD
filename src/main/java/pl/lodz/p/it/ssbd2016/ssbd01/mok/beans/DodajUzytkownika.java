/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author Patryk
 */
@Named
@RequestScoped  
public class DodajUzytkownika {
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
    
    public String rejestrujKontoKlienta() {
        this.konto.setImie(this.imie);
        this.konto.setNazwisko(this.nazwisko);
        this.konto.setHaslo(this.haslo);
        this.konto.setEmail(this.email);
        this.konto.setLogin(this.login);
        this.konto.setTelefon(this.telefon);
        
        sesjaKonta.rejestrujKlienta(konto);
        return "success";
    }
    
    public String utworzKonto() {
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
