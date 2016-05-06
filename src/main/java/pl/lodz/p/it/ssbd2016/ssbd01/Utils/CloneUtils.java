package pl.lodz.p.it.ssbd2016.ssbd01.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladDeSerializacjiObiektu;

/**
 * Klasa użytkowa umożliwia wykonanie głębokiej kopii obiektów
 */
public class CloneUtils {
    
    /**
     * Metoda wykonuje głęboką kopie obiektu wykorzystując serializację
     * @param source obiekt do wykonania kopii
     * @return kopia obiektu
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladPliku
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.BladSerializacjiObiektu 
     */
    public static Object deepCloneThroughSerialization(Serializable source) throws BladPliku, BladDeSerializacjiObiektu {
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(source);
 
            //De-serialization of object
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return in.readObject();
        } catch(IOException ex){
            throw new BladPliku(ex);
        } catch(ClassNotFoundException ex){
            throw new BladDeSerializacjiObiektu("blad pliku", ex);
        }
    }    
}
