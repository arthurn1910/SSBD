package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NaruszenieUniq;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiewykonanaOperacja;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje;

/**
 * Ziarno umożliwiające tworzenie nowych kont o dowolnym poziomie dostępu
 */
@Named
@RequestScoped  
public class UtworzKontoBean {
    
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto = new Konto();
    
    private String powtorzoneHaslo;
    
    private String[] wybranePoziomy;
    
    /**
     * Handler dla przycisku utwórz. Metoda tworzy nowe konto o zadanych poziomach dostępu 
     */
    public void utworzKonto(){
        if (checkPasswordMatching() && sprawdzPoziomyDostepu()) {
            uzytkownikSession.utworzKonto(konto, Arrays.asList(wybranePoziomy));
        }
    }
    
    /**
     * Metoda sprawdzająca poprawność wybranych poziomów dostępu:
     * conajmniej 1, czy poziomy się nie wykluczają
     * @return  decyzja czy można nadać dane poziomy dostępu
     */
    public boolean sprawdzPoziomyDostepu(){
        PoziomDostepuManager tmp;
        try {
            tmp = new PoziomDostepuManager();
            if (wybranePoziomy.length == 0) {
            FacesMessage message = new FacesMessage("Wybierz conajmniej 1");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:poziomy", message);
            return false;
        } else if (wybranePoziomy.length > 0 && !tmp.czyPoprawnaKombinacjaPoziomowDostepu(Arrays.asList(wybranePoziomy))) {
            FacesMessage message = new FacesMessage("Poziomy sie wykluczaja");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:poziomy", message);
            return false;
        }
            return true;
        } catch (NiewykonanaOperacja ex) {
            uzytkownikSession.setNiewykonanaOperacja(ex);
            uzytkownikSession.obslugaWyjatkow(ex, "../wyjatki/niewykonanaOperacja()");
        }
        return false;
    }
    
    /**
     * Metoda sprawdzająca czy podane hasła są identyczne
     * @return  decyzja czy hasła są identyczne
     */
    public boolean checkPasswordMatching() {
        if (!(konto.getHaslo().equals(powtorzoneHaslo))) {
            FacesMessage message = new FacesMessage("passwords dont match");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:hasloPotworzone", message);
            return false;
        }
        return true;
    }
    
    // Gettery i Settery
    
    public Konto getKonto() {
        return konto;
    }

    public String[] getWybranePoziomy() {
        return wybranePoziomy;
    }

    public void setWybranePoziomy(String[] wybranePoziomy) {
        this.wybranePoziomy = wybranePoziomy;
    }
    
    
    public String getPotorzoneHaslo() {
        return powtorzoneHaslo;
    }

    public void setPotorzoneHaslo(String powtorzoneHaslo) {
        this.powtorzoneHaslo = powtorzoneHaslo;
    }
    
    public String[] pobierzPoziomyDostepu() {
        try {
            PoziomDostepuManager tmp=new PoziomDostepuManager();
            return tmp.getPoziomyDostepu().toArray(new String[0]);
        } catch (NiewykonanaOperacja ex) {
            uzytkownikSession.setNiewykonanaOperacja(ex);
            uzytkownikSession.obslugaWyjatkow(ex, "../wyjatki/niewykonanaOperacja()");
        }
        return null;
    }            
}
