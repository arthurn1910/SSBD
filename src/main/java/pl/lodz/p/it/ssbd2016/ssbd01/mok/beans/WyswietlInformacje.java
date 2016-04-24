/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author rpawlaczyk
 */
@Named
@RequestScoped
public class WyswietlInformacje {
    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
    private String login;
    private String aktywne;
    private String potwierdzone;
    private String poziomyDostepu;

    public WyswietlInformacje() {
        imie="anna";
        nazwisko="mann";
        email="111@op.pl";
        telefon="123456789";
        login="am";
        aktywne="tak";
        potwierdzone="nie";
        poziomyDostepu="klient";
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getLogin() {
        return login;
    }

    public String getAktywne() {
        return aktywne;
    }

    public String getPotwierdzone() {
        return potwierdzone;
    }

    public String getPoziomyDostepu() {
        return poziomyDostepu;
    }
    
    public String zablokuj(){
        if(true)
            return "zablokujError";
        this.aktywne="nie";
        return "wyswietlInformacje";
    }
    public String odblokuj(){
        if(false)
            return "odblokujError";
        this.aktywne="tak";
        return "wyswietlInformacje";
    }
    public String potwierdz(){
        if(true)
            return "potwierdzError";
        this.potwierdzone="tak";
        return "wyswietlInformacje";
    }
    
}
