package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BrakDostepu;

/**
 * Klasa ta jest wykorzystywana do wyświetlania informacji o obecnie zalogowanym
 * uzytkowniku, oraz umożliwia edycję hasła i danych. 
 */
@Named
@RequestScoped
public class WyswietlSzczegolySwojegoKontaBean {
    
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto;
        
    /**
    * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
    * konto przez konto użytkownika obecnie zalogowanego
    */
    @PostConstruct
    public void initModel() throws BrakDostepu{
        konto = uzytkownikSession.getSwojeKonto();
    }
    
    /***
     * Metoda tworząca string złożony z nazw poziomów dostępu danego użytkownika
     * @return  string zawierający poziomy dostępu oddzielone przecinkiem
     */
    public String pobierzPoziomy(){
        String poziomy = "";
        
        for (PoziomDostepu poziom:konto.getPoziomDostepuCollection()) {
            poziomy += poziom.getPoziom() + ", ";
        }
        
        return poziomy;
    }
    
    /**
     * Handler przyciksu edytuj dane w widoku. Ustawia konto obecnie zalogowanego
     * użytkownika do edycji i przechodzi do odpowiendiej strony z edycją
     * @return      przekierowanie do strony z edycją
     */
    public String edytujSwojeKonto(){
        uzytkownikSession.pobierzKontoDoEdycji(konto);
        return "edytujDane";
    }
    
    /**
     * Handler przyciksu edytuj hasło w widoku. Ustawia konto obecnie zalogowanego
     * użytkownika do edycji i przechodzi do odpowiendiej strony z edycją
     * @return      przekierowanie do strony z edycją
     */
    public String edytujSwojeHasloKonta(){
        uzytkownikSession.pobierzKontoDoEdycji(konto);
        return "edytujHaslo";
    }
    
    // Gettery i Settery
    
    public Konto getKonto() {
        return konto;
    }
}
