package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Klasa ta jest wykorzystywana do wyświetlania informacji o wybranym uzytkowniku 
 * oraz zablokowania, odblokowania i potwierdzenia jego konta
 */
@Named
@RequestScoped
public class WyswietlSzczegolyKontaBean {
    
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto;
        
    /**
    * Metoda wywoływana zaraz po stworzeniu obiektu. Inicjalizuje pole
    * konto przez konto użytkownika którego chcemy wyświetlić
    */
    @PostConstruct
    public void initModel() {
        konto = uzytkownikSession.getWybraneKonto();
    }
    
    /**
     * Metoda tworząca string złożony z nazw poziomów dostępu danego użytkownika
     * @return  string zawierający poziomy dostępu oddzielone przecinkiem
     */
    public String pobierzPoziomy(){
        String poziomy = "";
        
        for (PoziomDostepu poziom:konto.getPoziomDostepuCollection()) {
            if (poziom.getAktywny()) {
                poziomy += poziom.getPoziom() + ", ";
            }
        }
        
        return poziomy;
    }
    
    /**
     * Handler przycisku zablokuj w widoku. Blokuje wybrane konto oraz odświeża widok
     */
    public void zablokuj(){
        uzytkownikSession.zablokujKonto(konto);
        initModel();
    }
    
    /**
     * Handler przycisku odblokuj w widoku. Odblokowuje wybrane konto oraz odświeża widok
     */
    public void odblokuj(){
        uzytkownikSession.odblokujKonto(konto);
        initModel();
    }
    
    /**
     * Handler przycisku potwierdź w widoku. Potwierdza wybrane konto oraz odświeża widok
     */
    public void potwierdz(){
        uzytkownikSession.potwierdzKonto(konto);
        initModel();
    }
    
    /**
     * Handler przycisku modyfkuj poziomy dostępu w widoku. Przekierowuje na widok edycji
     * @return      przekierowanie do widoku edycji
     */
    public String modyfikujPoziomyDostepu() {
        return "modyfikujPoziomyDostepu";
    }
    
    /**
     * Handler przyciksu edytuj dane w widoku. Pobiera wybrane konto do edycji
     * i przechodzi do odpowiendiej strony z edycją
     * @return      przekierowanie do strony z edycją
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public String edytujKonto() throws WyjatekSystemu{
        uzytkownikSession.pobierzKontoDoEdycji(konto);
        return "edytujDaneKonta";
    }
    
    /**
     * Handler przyciksu edytuj hasło w widoku. Pobiera wybrane konto do edycji
     * i przechodzi do odpowiendiej strony z edycją
     * @return      przekierowanie do strony z edycją
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu
     */
    public String edytujHasloKonta() throws WyjatekSystemu{
        uzytkownikSession.pobierzKontoDoEdycji(konto);
        return "edytujHasloKonta";
    }
        
    // Gettery i Settery
    
    public Konto getKonto() {
        return konto;
    }
}
