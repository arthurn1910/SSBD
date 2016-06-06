package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.util.Date;
import javax.ejb.SessionContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.moo.beans.OgloszenieSession;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Obsługuje widok rezerwacji spotkania
 */
@ManagedBean
@RequestScoped
public class RezerwacjaSpotkaniaBean{
    @Inject
    private SpotkanieSession spotkanieSession;
    @Inject
    private OgloszenieSession ogloszenieSession;
    @Inject
    private SessionContext sessionContext;
    Spotkanie spotkanie = new Spotkanie();
    
    private Date date;
    private String dlugosc;
    private Date godzina;
    
    /**
     * Metoda tworząca spotkanie i przesyłająca je do metody rezerwujSPotkanie w SpotkanieSession
     */
    public String rezerwujSpotkanie() throws WyjatekSystemu {
        this.date.setHours(godzina.getHours());
        this.date.setMinutes(godzina.getMinutes());
        this.date.setSeconds(godzina.getSeconds());
        spotkanie.setDlugoscSpotkania(Integer.parseInt(dlugosc));
        spotkanie.setDataSpotkania(date);
        spotkanie.setIdOgloszenia(ogloszenieSession.getOgloszenieDoWyswietlenia());
        spotkanieSession.rezerwujSpotkanie(spotkanie, sessionContext.getCallerPrincipal().getName());
        return "wyswietlSzczegolyOgloszenia";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(String dlugosc) {
        this.dlugosc = dlugosc;
    }

    public Date getGodzina() {
        return godzina;
    }

    public void setGodzina(Date godzina) {
        this.godzina = godzina;
    } 
}