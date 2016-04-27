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
    public void dodajPoziomDostepu(Konto konto, String poziom) throws Exception {
        if (PoziomDostepuManager.czyPosiadaPoziomDostepu(konto, poziom)) {
            // Posiadamy dany poziom
            PoziomDostepu aktualnyPoziom = PoziomDostepuManager.pobierzPoziomDostepu(konto, poziom);
            // Sprawdzamy czy poziom jest aktywny i czy możemy dołączyć dany poziom
            if (!aktualnyPoziom.getAktywny() && PoziomDostepuManager.czyMoznaDodacPoziom(konto, poziom)) {
                // Jeśli tak aktywujemy posiadany już poziom
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(true);
            } else {
                // Jeśli nie zwracamy błąd
                throw new Exception("Nie możemy dodać poziomu dostępu");
            }
        } else {
            // Nie posiadamy danego poziomu dostępu
            // Sprawdzamy czy możemy taki poziom dodać
            if (PoziomDostepuManager.czyMoznaDodacPoziom(konto, poziom)) {
                Konto aktualneKonto = kontoFacade.znajdzPoLoginie(konto.getLogin());
                //Tworzymy i dodajemy nowy poziom dostępu
                PoziomDostepu nowyPoziom = PoziomDostepuManager.stworzPoziomDostepu(poziom);
                poziomDostepuFacade.create(nowyPoziom);

                aktualneKonto.getPoziomDostepuCollection().add(nowyPoziom);
                nowyPoziom.setKontoId(aktualneKonto);
                nowyPoziom.setAktywny(true);
            } else {                
                // Jeśli nie udało się dodać poziom dostępu zwracamy błąd
                throw new Exception("Nie możemy dodać poziomu dostępu");
            }
        }
    }

    @Override
    public void odlaczPoziomDostepu(Konto konto, String poziom) throws Exception {
        if (PoziomDostepuManager.czyPosiadaAktywnyPoziomDostepu(konto, poziom)) {
            
            PoziomDostepu aktualnyPoziom = PoziomDostepuManager.pobierzPoziomDostepu(konto, poziom);
            
            if (aktualnyPoziom.getAktywny()) {
                // Jeśli poziom jest aktywny to go dezaktywujemy
                PoziomDostepu odlaczanyPoziom = poziomDostepuFacade.find(aktualnyPoziom.getId());
                odlaczanyPoziom.setAktywny(false);
            } else {
                // Jeśli poziom jest nieaktywny zwracamy błąd
                throw new Exception("Nie możemy dodać poziomu dostępu");
            }
        } else {            
            // Jeśli nie udało się odłączyć poziom dostępu zwracamy błąd
            throw new Exception("Nie możemy dodać poziomu dostępu");
        }
    }

    @Override
    public void zmienHaslo(Konto konto, String noweHaslo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void zmienMojeHasloJesliPoprawne(String noweHaslo, String stareHaslo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
