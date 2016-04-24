package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

@Named
@RequestScoped
public class WyszukajKontaNiepotwierdzone {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private DataModel<Konto> kontaNiepotwierdzoneWyszukiwanie;
    private String parametr;
    private String wartoscParametru;
    
    public DataModel<Konto> getKontaNiepotwierdzoneWyszukiwanie() {
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
        this.kontaNiepotwierdzoneWyszukiwanie = new ListDataModel<Konto>();
    }
    
    public void initWyszukiwanie() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        List<Konto> konta = new ArrayList<Konto>();
        for(int i = 0; i < temp.size(); i++)
        {
            if(parametr.equals("login") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getLogin().equals(wartoscParametru) == true)
                    konta.add(temp.get(i));
            }
            else if(parametr.equals("email") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getEmail().equals(wartoscParametru) == true)
                    konta.add(temp.get(i));
            }
            else if(parametr.equals("telefon") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getTelefon().equals(wartoscParametru) == true)
                    konta.add(temp.get(i));
            }
            else if(parametr.equals("nazwisko") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getNazwisko().equals(wartoscParametru) == true)
                    konta.add(temp.get(i));
            }
            else if(parametr.equals("id") == true)
            {
                if(temp.get(i).getPotwierdzone() == false && temp.get(i).getId().toString().equals(wartoscParametru) == true)
                    konta.add(temp.get(i));
            }

        }
        kontaNiepotwierdzoneWyszukiwanie = new ListDataModel<Konto>(konta);
    }
    
    public void potwierdzKontoWyszukiwanie() {
        uzytkownikSession.potwierdzKonto(kontaNiepotwierdzoneWyszukiwanie.getRowData());
        System.out.println(kontaNiepotwierdzoneWyszukiwanie.getRowData());
        initWyszukiwanie();
    }
}
