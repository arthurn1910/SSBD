package pl.lodz.p.it.ssbd2016.ssbd01.mosPU;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.endpoints.MOKEndpointLocal;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import java.util.List;
import java.util.ArrayList;

@ManagedBean(name = "mos4")
@RequestScoped
public class Mos4 implements Serializable {
    @Inject
    private MOKEndpointLocal mokEndpoint;
    
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
        konto = mokEndpoint.pobierzPierwszeKonto();
        spotkania = new ArrayList(konto.getSpotkanieCollection());
    }
    
    public List<Spotkanie> getSpotkania() {
        return spotkania;
    }
}