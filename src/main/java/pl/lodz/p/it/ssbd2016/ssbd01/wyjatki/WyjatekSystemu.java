/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

import javax.ejb.ApplicationException;

/**
 * Klasa wyjątku aplikacyjnego który obsługuje wyjątki aplikacyjne i systemowe
 * @author java
 */
@ApplicationException(rollback = true)
public class WyjatekSystemu extends Exception {

    public WyjatekSystemu(String message) {
        super(message);
    }

    public WyjatekSystemu(String message, Throwable cause) {
        super(message, cause);
    }

    public WyjatekSystemu(Throwable cause) {
        super(cause);
    }
}
