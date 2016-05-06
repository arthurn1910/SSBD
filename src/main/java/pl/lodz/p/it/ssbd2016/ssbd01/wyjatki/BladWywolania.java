/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

/**
 *
 * @author java
 */
public class BladWywolania extends Exception {
    private String blad;

    public BladWywolania(String blad, String message) {
        super(message);
        this.blad = blad;
    }

    public BladWywolania(String blad, String message, Throwable cause) {
        super(message, cause);
        this.blad = blad;
    }

    public BladWywolania(String blad, Throwable cause) {
        super(cause);
        this.blad = blad;
    }

    
    
}
