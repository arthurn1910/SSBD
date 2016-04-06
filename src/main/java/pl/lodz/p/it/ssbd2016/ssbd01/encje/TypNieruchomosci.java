/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.encje;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author java
 */
@Entity
@Table(name = "typ_nieruchomosci")
@NamedQueries({
    @NamedQuery(name = "TypNieruchomosci.findAll", query = "SELECT t FROM TypNieruchomosci t"),
    @NamedQuery(name = "TypNieruchomosci.findById", query = "SELECT t FROM TypNieruchomosci t WHERE t.id = :id"),
    @NamedQuery(name = "TypNieruchomosci.findByNazwa", query = "SELECT t FROM TypNieruchomosci t WHERE t.nazwa = :nazwa"),
    @NamedQuery(name = "TypNieruchomosci.findByVersion", query = "SELECT t FROM TypNieruchomosci t WHERE t.version = :version")})
public class TypNieruchomosci implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nazwa")
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typNieruchomosci")
    private Collection<Nieruchomosc> nieruchomoscCollection;

    public TypNieruchomosci() {
    }

    public TypNieruchomosci(Integer id) {
        this.id = id;
    }

    public TypNieruchomosci(Integer id, String nazwa, long version) {
        this.id = id;
        this.nazwa = nazwa;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Collection<Nieruchomosc> getNieruchomoscCollection() {
        return nieruchomoscCollection;
    }

    public void setNieruchomoscCollection(Collection<Nieruchomosc> nieruchomoscCollection) {
        this.nieruchomoscCollection = nieruchomoscCollection;
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
        if (!(object instanceof TypNieruchomosci)) {
            return false;
        }
        TypNieruchomosci other = (TypNieruchomosci) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.TypNieruchomosci[ id=" + id + " ]";
    }
    
}
