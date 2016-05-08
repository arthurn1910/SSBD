package pl.lodz.p.it.ssbd2016.ssbd01.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu;

/**
 * Klasa użytkowa umożliwia wykonanie głębokiej kopii obiektów
 */
public class CloneUtils {
    
    /**
     * Metoda wykonuje głęboką kopie obiektu wykorzystując serializację
     * @param source obiekt do wykonania kopii
     * @return kopia obiektu 
     * @throws pl.lodz.p.it.ssbd2016.ssbd01.wyjatki.WyjatekSystemu 
     */
    public static Object deepCloneThroughSerialization(Serializable source) throws WyjatekSystemu{
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(source);
 
            //De-serialization of object
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return in.readObject();
        } catch(IOException ex){
            throw new WyjatekSystemu("bladPliku",ex);
        } catch(ClassNotFoundException ex){
            throw new WyjatekSystemu("bladDeSerializacjiObiektu", ex);
        }
    }    
}
