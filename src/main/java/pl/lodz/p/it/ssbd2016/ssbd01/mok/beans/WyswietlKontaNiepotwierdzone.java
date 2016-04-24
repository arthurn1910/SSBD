package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.beans.UzytkownikSession;
import javax.faces.model.ListDataModel;

@Named
@RequestScoped
public class WyswietlKontaNiepotwierdzone implements Serializable {
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private DataModel<Konto> kontaNiepotwierdzone;

    public DataModel<Konto> getKontaNiepotwierdzone() {
        return kontaNiepotwierdzone;
    }
    
    @PostConstruct
    public void init() {
        List<Konto> temp = uzytkownikSession.pobierzWszystkieKonta();
        List<Konto> konta = new ArrayList<Konto>();
        for(int i = 0; i < temp.size(); i++)
        {
            if(temp.get(i).getPotwierdzone() == false)
                konta.add(temp.get(i));
        }
        kontaNiepotwierdzone = new ListDataModel<Konto>(konta);
    }
    
    public void potwierdzKonto() {
        uzytkownikSession.potwierdzKonto(kontaNiepotwierdzone.getRowData());
        init();
    }
}
