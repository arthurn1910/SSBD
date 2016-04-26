package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 * Obiekty tej klasy są wykorzystywane do wyszukiwania kont użytkowników
 * @author Maksymilian Zgierski
 */
@Named
@RequestScoped
public class WyszukajKonta {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private ArrayList<Konto> kontaWyszukiwanie = new ArrayList();
    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
    private String login;
    
    public ArrayList<Konto> getKontaWyszukiwanie() {
        return kontaWyszukiwanie;
    }
    
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
    
    public String getEmail() {
        return email;
    }    
    public void setEmail(String e) {
        this.email = e;
    }
    
    public String getTelefon() {
        return telefon;
    }    
    public void setTelefon(String t) {
        this.telefon = t;
    }
    
  /**
 * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole kontaWyszukiwanie wszystkimi kontami
 */
    @PostConstruct
    public void init() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        kontaWyszukiwanie = new ArrayList();
        
        for(int i = 0; i < temp.size(); i++)
            kontaWyszukiwanie.add(temp.get(i));
    }
  
  /**
 * Inicjalizuje pole kontaWyszukiwanie wszystkimi kontami, których dane pasują do wzorców
 * wpisanych w odpowiednie pola w formularzu
 */
    public void initWyszukiwanie() {
        kontaWyszukiwanie = new ArrayList();
        Konto temp = new Konto();
        temp.setLogin(login);
        temp.setImie(imie);
        temp.setNazwisko(nazwisko);
        temp.setEmail(email);
        temp.setTelefon(telefon);
        List<Konto> konta = uzytkownikSession.pobierzPodobneKonta(temp);
        for(int i = 0; i < konta.size(); i++)
            kontaWyszukiwanie.add(konta.get(i));
    }
}
