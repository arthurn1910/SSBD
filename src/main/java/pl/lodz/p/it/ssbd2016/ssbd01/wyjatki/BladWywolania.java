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
    private String miejsce;
    private String metoda;
    public BladWywolania(String miejsce, String metoda) {
        super();
        this.metoda=metoda;
        this.miejsce=miejsce;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public String getMetoda() {
        return metoda;
    }
    
    
}
