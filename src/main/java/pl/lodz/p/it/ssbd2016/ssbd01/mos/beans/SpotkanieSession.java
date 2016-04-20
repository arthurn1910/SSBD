package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints.MOSEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import java.util.Date;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie;

@SessionScoped
public class SpotkanieSession implements Serializable {
    @Inject
    private MOSEndpointLocal mosEndpoint;
    
    public void dodajSpotkanie(Konto k, Ogloszenie o) {
        Konto konto = mosEndpoint.pobierzPierwszeKonto();
        
        Ogloszenie ogloszenie = mosEndpoint.pobierzPierwszeOgloszenie();
        Spotkanie spotkanie = new Spotkanie();
        spotkanie.setDataSpotkania(new Date(2016, 6, 10));
        spotkanie.setDlugoscSpotkania(30);
        spotkanie.setIdUzytkownika(konto);
        spotkanie.setIdOgloszenia(ogloszenie);
        mosEndpoint.dodajSpotkanie(spotkanie);
        
        konto.getSpotkanieCollection().add(spotkanie);
        ogloszenie.getSpotkanieCollection().add(spotkanie);        
    }
    
    public Konto pobierzPierwszeKonto() {
        Konto konto = mosEndpoint.pobierzPierwszeKonto();
        return konto;
    }
}