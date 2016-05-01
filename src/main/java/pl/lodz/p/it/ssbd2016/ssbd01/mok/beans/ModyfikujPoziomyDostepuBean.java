package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;

/**
 * Klasa ta jest wykorzystywana do modyfikacji poziomów dostępu dla wybranego konta
 */
@Named
@RequestScoped
public class ModyfikujPoziomyDostepuBean {
    
    @Inject
    private UzytkownikSession uzytkownikSession;
    
    private Konto konto;
    
    private DataModel<String> poziomyDostepuDataModel;

    /**
     * Metoda inizjalizująca dostępne poziomy dostępu i konta, dla którego
     * będą modyfikowane poziomy dostępu
     */
    @PostConstruct
    private void initModel() {
        poziomyDostepuDataModel = new ListDataModel<String>(PoziomDostepuManager.getPoziomyDostepu());
        konto = uzytkownikSession.getWybraneKonto();
    }
    
    /**
     * Handler dla przycisku dołącz. Metoda dołącza poziom dostępu do konta
     * @throws Exception 
     */
    public void dodajPoziomDostepu() throws Exception {
        uzytkownikSession.dodajPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        initModel();
    }
    
    /**
     * Handler dla przycisku odłącz. Metoda odłączająca poziom dostępu do konta
     * @throws Exception 
     */
    public void odlaczPoziomDostepu() throws Exception {
        uzytkownikSession.odlaczPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());      
        initModel();
    }
    
    /**
     * Metoda dla widoku definiująca czy konto posiada dany poziom dostępu. 
     * Zmienia dostępne klawisze między dołącz i odłącz
     * @return  decyzcja czy konto posiada aktywny poziom dostępu
     */
    public boolean czyPosiadaAktywnyPoziomDostepu() {
        return PoziomDostepuManager.czyPosiadaAktywnyPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
    }
    
    // Gettery i Settery    
    
    public DataModel<String> getPoziomyDostepuDataModel() {
        return poziomyDostepuDataModel;
    }    
}
