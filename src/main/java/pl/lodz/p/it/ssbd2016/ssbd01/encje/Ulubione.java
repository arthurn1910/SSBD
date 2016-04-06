/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.encje;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author java
 */
@Entity
@Table(name = "ulubione")
@NamedQueries({
    @NamedQuery(name = "Ulubione.findAll", query = "SELECT u FROM Ulubione u"),
    @NamedQuery(name = "Ulubione.findById", query = "SELECT u FROM Ulubione u WHERE u.id = :id")})
public class Ulubione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "id_konta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Konto idKonta;
    @JoinColumn(name = "id_ogloszenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ogloszenie idOgloszenia;

    public Ulubione() {
    }

    public Ulubione(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Konto getIdKonta() {
        return idKonta;
    }

    public void setIdKonta(Konto idKonta) {
        this.idKonta = idKonta;
    }

    public Ogloszenie getIdOgloszenia() {
        return idOgloszenia;
    }

    public void setIdOgloszenia(Ogloszenie idOgloszenia) {
        this.idOgloszenia = idOgloszenia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ulubione)) {
            return false;
        }
        Ulubione other = (Ulubione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.Ulubione[ id=" + id + " ]";
    }
    
}
