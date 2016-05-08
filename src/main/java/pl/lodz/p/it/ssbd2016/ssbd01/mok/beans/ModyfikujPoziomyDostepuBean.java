package pl.lodz.p.it.ssbd2016.ssbd01.mok.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2016.ssbd01.encje.Konto;
import pl.lodz.p.it.ssbd2016.ssbd01.mok.utils.PoziomDostepuManager;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

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
    private void initModel(){
        try {
            PoziomDostepuManager tmp=new PoziomDostepuManager();
            poziomyDostepuDataModel = new ListDataModel<String>(tmp.getPoziomyDostepu());
            konto = uzytkownikSession.getWybraneKonto();
        } catch (WyjatekSystemu ex) {
            uzytkownikSession.setException(ex);
        }
    }
    
    /**
     * Handler dla przycisku dołącz. Metoda dołącza poziom dostępu do konta 
     * @return 
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.PoziomDostepuNieIstnieje
     */
    public String dodajPoziomDostepu() throws WyjatekSystemu{
        uzytkownikSession.dodajPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        initModel();
        return "modyfikujPoziomyDostepu";
    }
    
    /**
     * Handler dla przycisku odłącz. Metoda odłączająca poziom dostępu do konta 
     * @return 
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPoziomDostepu 
     */
    public String odlaczPoziomDostepu() throws WyjatekSystemu, Exception{
        uzytkownikSession.odlaczPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        initModel();
        return "modyfikujPoziomyDostepu";
    }
    
    /**
     * Metoda dla widoku definiująca czy konto posiada dany poziom dostępu. 
     * Zmienia dostępne klawisze między dołącz i odłącz
     * @return  decyzcja czy konto posiada aktywny poziom dostępu
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.NiewykonanaOperacja
     */
    public boolean czyPosiadaAktywnyPoziomDostepu() throws WyjatekSystemu {
        try {
            PoziomDostepuManager tmp=new PoziomDostepuManager();
            return tmp.czyPosiadaAktywnyPoziomDostepu(konto, poziomyDostepuDataModel.getRowData());
        } catch (WyjatekSystemu ex) {
            uzytkownikSession.setException(ex);
            throw ex;
        }
    }
    
    // Gettery i Settery    
    
    public DataModel<String> getPoziomyDostepuDataModel() {
        return poziomyDostepuDataModel;
    }    
}
