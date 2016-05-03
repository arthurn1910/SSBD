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
public class PoziomDostepuNieIstnieje extends Exception{
    private String miejsce;
    private String poziom;

    public PoziomDostepuNieIstnieje(String miejsce, String poziom) {
        this.miejsce = miejsce;
        this.poziom=poziom;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public String getPoziom() {
        return poziom;
    }
    
    
}
