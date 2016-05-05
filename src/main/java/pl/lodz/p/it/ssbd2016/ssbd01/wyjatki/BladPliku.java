/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.wyjatki;

import java.io.IOException;

/**
 *
 * @author java
 */
public class BladPliku extends IOException {

    private String miejsce, plik;
    public BladPliku(String miejsce, String plik) {
        super();
        this.miejsce=miejsce;
        this.plik=plik;
    }
    
    public String getMiejsce(){
        return miejsce;
    }
    
    public String getPlik(){
        return plik;
    } 
}