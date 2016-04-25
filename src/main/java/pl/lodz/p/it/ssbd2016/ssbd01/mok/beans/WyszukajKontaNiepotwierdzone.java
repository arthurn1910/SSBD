package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

@ManagedBean(name="wyszukajKontaNiepotwierdzone")
@ViewScoped
public class WyszukajKontaNiepotwierdzone {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private ArrayList<Konto> kontaNiepotwierdzoneWyszukiwanie = new ArrayList();
    private String parametr;
    private String wartoscParametru;
    private boolean first = true;
    
    public ArrayList<Konto> getKontaNiepotwierdzoneWyszukiwanie() {
        return kontaNiepotwierdzoneWyszukiwanie;
    }
    
    public String getParametr() {
        return parametr;
    }    
    public void setParametr(String p) {
        this.parametr = p;
    }
    
    public String getWartoscParametru() {
        return wartoscParametru;
    }    
    public void setWartoscParametru(String w) {
        this.wartoscParametru = w;
    }
    
    @PostConstruct
    public void init() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        kontaNiepotwierdzoneWyszukiwanie = new ArrayList<Konto>();
        
        for(int i = 0; i < temp.size(); i++)
        {
            if(temp.get(i).getPotwierdzone() == false)
                kontaNiepotwierdzoneWyszukiwanie.add(temp.get(i));
        }
    }
    
    public void initWyszukiwanie() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        kontaNiepotwierdzoneWyszukiwanie = new ArrayList<Konto>();
        
        for(int i = 0; i < temp.size(); i++)
        {
            if(parametr.equals("login") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getLogin().equals(wartoscParametru) == true)
                    kontaNiepotwierdzoneWyszukiwanie.add(temp.get(i));
            }
            else if(parametr.equals("email") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getEmail().equals(wartoscParametru) == true)
                    kontaNiepotwierdzoneWyszukiwanie.add(temp.get(i));
            }
            else if(parametr.equals("telefon") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getTelefon().equals(wartoscParametru) == true)
                    kontaNiepotwierdzoneWyszukiwanie.add(temp.get(i));
            }
            else if(parametr.equals("nazwisko") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getNazwisko().equals(wartoscParametru) == true)
                    kontaNiepotwierdzoneWyszukiwanie.add(temp.get(i));
            }
            else if(parametr.equals("id") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getId().toString().equals(wartoscParametru) == true)
                    kontaNiepotwierdzoneWyszukiwanie.add(temp.get(i));
            }
        }
    }
    
    public void potwierdzKontoWyszukiwanie(Konto k) {
        uzytkownikSession.potwierdzKonto(k);
        initWyszukiwanie();
    }
}
