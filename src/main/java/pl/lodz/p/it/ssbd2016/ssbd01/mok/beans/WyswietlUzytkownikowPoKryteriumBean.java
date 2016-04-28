package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;

/**
 *
 * @author java
 */
@Named
@RequestScoped  
public class WyswietlUzytkownikowPoKryteriumBean {
    @Inject
    private UzytkownikSession sesjaKonta;
    
    private final Konto konto = new Konto();
    
    private String imie;
    private String nazwisko;

    private List<Konto> konta;
    private DataModel<Konto> kontaDataModel;

    public DataModel<Konto> getKontaDataModel() {
        return kontaDataModel;
    }
    
    @PostConstruct
    private void initModel() {
        konto.setPotwierdzone(true);
        konto.setAktywne(true);
        konta = sesjaKonta.pobierzPodobneKonta(konto);
        kontaDataModel = new ListDataModel<Konto>(konta);
    }
    
    public Konto getKonto() {
        return konto;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    public void wyszukajPodobne() {
        if (imie != "") {
            konto.setImie(imie);
        }
        
        if (nazwisko != "") {
            konto.setNazwisko(nazwisko);
        }
        
        initModel();
    }
}
