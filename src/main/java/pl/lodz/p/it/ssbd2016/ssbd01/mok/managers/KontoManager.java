package pl.lodz.p.it.ssbd2016.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.PoziomDostepu;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.KontoFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.fasady.PoziomDostepuFacadeLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

/**
 * Klasa pośrednicząca miedzy MOKEndpoint a fasadami. Przetwarza niezbędne dane.
 * Implementuje interfejs KontoManagaerLocal
 */
@Stateless
public class KontoManager implements KontoManagerLocal {

    @EJB
    private KontoFacadeLocal kontoFacade;
    
    @EJB
    private PoziomDostepuFacadeLocal poziomDostepuFacade;
    
    @Override
    public List<Konto> znajdzPodobne(Konto konto) {
        Konto kontoWyszukaj = new Konto();
        if (konto.getImie() == null) {
            kontoWyszukaj.setImie("%%");
        } else {
            kontoWyszukaj.setImie("%" + konto.getImie() + "%");
        }
        
        if (konto.getNazwisko()== null) {
            kontoWyszukaj.setNazwisko("%%");
        } else {
            kontoWyszukaj.setNazwisko("%" + konto.getNazwisko()+ "%");
        }
        
        if (konto.getEmail()== null) {
            kontoWyszukaj.setEmail("%%");
        } else {
            kontoWyszukaj.setEmail("%" + konto.getEmail()+ "%");
        }
        
        if (konto.getTelefon()== null) {
            kontoWyszukaj.setTelefon("%%");
        } else {
            kontoWyszukaj.setTelefon("%" + konto.getTelefon()+ "%");
        }
        kontoWyszukaj.setAktywne(konto.getAktywne());
        kontoWyszukaj.setPotwierdzone(konto.getPotwierdzone());
        
        return kontoFacade.znajdzPodobne(kontoWyszukaj);
    }

    @Override
    public boolean dodajPoziomDostepu(Konto konto, String poziom) {
        if (PoziomDostepuManager.czyPosiadaPoziomDostepu(konto, poziom)) {
            // Posiadamy dany poziom
            PoziomDostepu aktualnyPoziom = PoziomDostepuManager.pobierzPoziomDostepu(konto, poziom);
            // Sprawdzamy czy poziom jest aktywny i czy możemy dołączyć dany poziom
            if (!aktualnyPoziom.getAktywny() && PoziomDostepuManager.czyMoznaDodacPoziom(konto, poziom)) {
                // Jeśli tak aktywujemy posiadany już poziom
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(true);
                return true;
            } else {
                // Jeśli nie zwracamy błąd
                return false;
            }
        } else {
            // Nie posiadamy danego poziomu dostępu
            // Sprawdzamy czy możemy taki poziom dodać
            if (PoziomDostepuManager.czyMoznaDodacPoziom(konto, poziom)) {
                Konto aktualneKonto = kontoFacade.znajdzPoLoginie(konto.getLogin());
                //Tworzymy i dodajemy nowy poziom dostępu
                PoziomDostepu nowyPoziom = PoziomDostepuManager.stwórzPoziomDostepu(poziom);
                poziomDostepuFacade.create(nowyPoziom);

                aktualneKonto.getPoziomDostepuCollection().add(nowyPoziom);
                nowyPoziom.setKontoId(aktualneKonto);
                nowyPoziom.setAktywny(true);
                return true;
            }
        }
        // Jeśli nie udało się dodać poziom dostępu zwracamy błąd
        return false;
    }

    @Override
    public boolean odlaczPoziomDostepu(Konto konto, String poziom) {
        if (PoziomDostepuManager.czyPosiadaAktywnyPoziomDostepu(konto, poziom)) {
            
            PoziomDostepu aktualnyPoziom = PoziomDostepuManager.pobierzPoziomDostepu(konto, poziom);
            
            if (aktualnyPoziom.getAktywny()) {
                // Jeśli poziom jest aktywny to go dezaktywujemy
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(false);
                return true;
            } else {
                // Jeśli poziom jest nieaktywny zwracamy błąd
                return false;
            }
        }
        // Jeśli nie udało się odłączyć poziom dostępu zwracamy błąd
        return false;
    }
    
}
