package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

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
    
    @PostConstruct
    public void init() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        kontaWyszukiwanie = new ArrayList();
        
        for(int i = 0; i < temp.size(); i++)
            kontaWyszukiwanie.add(temp.get(i));
    }
    
    public void initWyszukiwanie() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        kontaWyszukiwanie = new ArrayList();
        
        for(int i = 0; i < temp.size(); i++)
        {
            if(login.length() != 0)
            {
                if(temp.get(i).getLogin().contains(login) == true)
                {
                    kontaWyszukiwanie.add(temp.get(i));
                    continue;
                }
            }
            if(imie.length() != 0)
            {
                if(temp.get(i).getImie().contains(imie) == true)
                {
                    kontaWyszukiwanie.add(temp.get(i));
                    continue;
                }
            }
            if(nazwisko.length() != 0)
            {
                if(temp.get(i).getNazwisko().contains(nazwisko) == true)
                {
                    kontaWyszukiwanie.add(temp.get(i));
                    continue;
                }
            }
            if(email.length() != 0)
            {
                if(temp.get(i).getEmail().contains(email) == true)
                {
                    kontaWyszukiwanie.add(temp.get(i));
                    continue;
                }
            }
            if(telefon.length() != 0)
            {
                if(temp.get(i).getTelefon().contains(telefon) == true)
                {
                    kontaWyszukiwanie.add(temp.get(i));
                    continue;
                }
            }
            if(login.length() == 0 && imie.length() == 0 && nazwisko.length() == 0 && email.length() == 0 &&
                    telefon.length() == 0)
                kontaWyszukiwanie.add(temp.get(i));
        }
    }
}
