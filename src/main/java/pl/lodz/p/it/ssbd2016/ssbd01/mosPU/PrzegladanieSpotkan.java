package pl.lodz.p.it.ssbd2016.ssbd01.mosPU;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2016.ssbd01.mos.endpoints.MOSEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import java.util.List;
import java.util.ArrayList;

@ManagedBean(name = "przegladanieSpotkan")
@RequestScoped
public class PrzegladanieSpotkan implements Serializable {
    @Inject
    private MOSEndpointLocal mosEndpoint;
    
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
        konto = mosEndpoint.pobierzPierwszeKonto();
        spotkania = new ArrayList(konto.getSpotkanieCollection());
    }
    
    public List<Spotkanie> getSpotkania() {
        return spotkania;
    }
}