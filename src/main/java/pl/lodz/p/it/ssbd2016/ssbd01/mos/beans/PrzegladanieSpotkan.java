package pl.lodz.p.it.ssbd2016.ssbd01.mos.beans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import java.util.List;
import java.util.ArrayList;

@Named
@RequestScoped
public class PrzegladanieSpotkan implements Serializable {
    @Inject
    private SpotkanieSession spotkanieSession;
    
    private List<Spotkanie> spotkania;
    private Konto konto;
    public Konto getKonto() {
        return konto;
    }
    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    @PostConstruct
    public void init() {
    }
    
    public List<Spotkanie> getSpotkania() {
        return spotkania;
    }
}