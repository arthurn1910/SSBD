package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakAlgorytmuKodowania;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NieobslugiwaneKodowanie;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje;

/**
 * Ziarno umożliwiające tworzenie nowych kont dla klientów
 */
@Named
@RequestScoped  
public class RejestracjaKontaKlientaBean {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto = new Konto();
    private String potorzoneHaslo;
    
    /**
     * Handler dla przycisku rejestruj. Metoda tworzy nowe konto klienta 
     */
    public String rejestrujKontoKlienta(){
        if (checkPasswordMatching()) {
            try {
                uzytkownikSession.rejestrujKontoKlienta(konto);
            } catch (PoziomDostepuNieIstnieje ex) {
                Logger.getLogger(RejestracjaKontaKlientaBean.class.getName()).log(Level.SEVERE, null, ex);
                return "PoziomDostepuNieIstnieje";
            } catch (NieobslugiwaneKodowanie ex) {
                Logger.getLogger(RejestracjaKontaKlientaBean.class.getName()).log(Level.SEVERE, null, ex);
                return "NieobslugiwaneKodowanie";
            } catch (BrakAlgorytmuKodowania ex) {
                Logger.getLogger(RejestracjaKontaKlientaBean.class.getName()).log(Level.SEVERE, null, ex);
                return "BrakAlgorytmuKodowania";
            }
        }
        return "sukcess";
    }
    
    /**
     * Metoda sprawdzająca czy podane hasła są identyczne
     * @return  decyzja czy hasła są identyczne
     */
    public boolean checkPasswordMatching(){
        if (!(konto.getHaslo().equals(potorzoneHaslo))) {
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

    public String getPotorzoneHaslo() {
        return potorzoneHaslo;
    }

    public void setPotorzoneHaslo(String potorzoneHaslo) {
        this.potorzoneHaslo = potorzoneHaslo;
    }
}
