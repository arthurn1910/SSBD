package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Spotkanie spotkanie = new Spotkanie();
    
    private Date date;
    private String dlugosc;
    private Date godzina;
    
    /**
     * Metoda tworząca spotkanie i przesyłająca je do metody rezerwujSPotkanie w SpotkanieSession
     */
    public String rezerwujSpotkanie() throws WyjatekSystemu {
        Logger lg=Logger.getLogger("javax.enterprice.system.conteiner.web.faces");
        lg.log(Level.INFO, "1!!!");
        this.date.setHours(godzina.getHours());
        lg.log(Level.INFO, "2!!!");
        this.date.setMinutes(godzina.getMinutes());
        lg.log(Level.INFO, "3!!!");
        this.date.setSeconds(godzina.getSeconds());
        lg.log(Level.INFO, "4!!!");
        spotkanie.setDlugoscSpotkania(Integer.parseInt(dlugosc));
        lg.log(Level.INFO, "5!!!");
        spotkanie.setDataSpotkania(date);
        lg.log(Level.INFO, "6!!!");
        spotkanie.setIdOgloszenia(ogloszenieSession.getOgloszenieDoWyswietlenia());
        lg.log(Level.INFO, "7!!!");
        spotkanieSession.rezerwujSpotkanie(spotkanie);
        lg.log(Level.INFO, "1!!!");
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