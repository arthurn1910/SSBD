package pl.lodz.p.it.ssbd2016.ssbd01.moo.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Obsługuje widok edycji ogłoszenia
 */
@Named
@RequestScoped
public class EdytujOgloszenieBean {
    
    @Inject
    private OgloszenieSession ogloszenieSession;
    
    /**
     * Metoda ma za zadanie zapisanie ogłoszenia po zakończeniu edycji
     * @return zwraca łańcuch, który przekierowuje do widoku wyświetlającego ogłoszenia
     * @throws Exception 
     */
    public String zapiszOgloszeniePoEdycji() throws Exception{
        ogloszenieSession.zapiszOgloszeniePoEdycji();
        return "wyswietlOgloszenia";
    }
}
