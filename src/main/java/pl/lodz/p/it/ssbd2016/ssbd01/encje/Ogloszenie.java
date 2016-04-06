/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.encje;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author java
 */
@Entity
@Table(name = "ogloszenie")
@NamedQueries({
    @NamedQuery(name = "Ogloszenie.findAll", query = "SELECT o FROM Ogloszenie o"),
    @NamedQuery(name = "Ogloszenie.findById", query = "SELECT o FROM Ogloszenie o WHERE o.id = :id"),
    @NamedQuery(name = "Ogloszenie.findByTytul", query = "SELECT o FROM Ogloszenie o WHERE o.tytul = :tytul"),
    @NamedQuery(name = "Ogloszenie.findByCena", query = "SELECT o FROM Ogloszenie o WHERE o.cena = :cena"),
    @NamedQuery(name = "Ogloszenie.findByRynekPierwotny", query = "SELECT o FROM Ogloszenie o WHERE o.rynekPierwotny = :rynekPierwotny"),
    @NamedQuery(name = "Ogloszenie.findByDataDodania", query = "SELECT o FROM Ogloszenie o WHERE o.dataDodania = :dataDodania"),
    @NamedQuery(name = "Ogloszenie.findByVersion", query = "SELECT o FROM Ogloszenie o WHERE o.version = :version")})
public class Ogloszenie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "tytul")
    private String tytul;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rynek_pierwotny")
    private boolean rynekPierwotny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_dodania")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDodania;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "id_agenta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Konto idAgenta;
    @JoinColumn(name = "id_wlasciciela", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Konto idWlasciciela;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Nieruchomosc nieruchomosc;
    @JoinColumn(name = "typ_ogloszenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TypOgloszenia typOgloszenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOgloszenia")
    private Collection<Ulubione> ulubioneCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOgloszenia")
    private Collection<Spotkanie> spotkanieCollection;

    public Ogloszenie() {
    }

    public Ogloszenie(Long id) {
        this.id = id;
    }

    public Ogloszenie(Long id, String tytul, int cena, boolean rynekPierwotny, Date dataDodania, long version) {
        this.id = id;
        this.tytul = tytul;
        this.cena = cena;
        this.rynekPierwotny = rynekPierwotny;
        this.dataDodania = dataDodania;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public boolean getRynekPierwotny() {
        return rynekPierwotny;
    }

    public void setRynekPierwotny(boolean rynekPierwotny) {
        this.rynekPierwotny = rynekPierwotny;
    }

    public Date getDataDodania() {
        return dataDodania;
    }

    public void setDataDodania(Date dataDodania) {
        this.dataDodania = dataDodania;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Konto getIdAgenta() {
        return idAgenta;
    }

    public void setIdAgenta(Konto idAgenta) {
        this.idAgenta = idAgenta;
    }

    public Konto getIdWlasciciela() {
        return idWlasciciela;
    }

    public void setIdWlasciciela(Konto idWlasciciela) {
        this.idWlasciciela = idWlasciciela;
    }

    public Nieruchomosc getNieruchomosc() {
        return nieruchomosc;
    }

    public void setNieruchomosc(Nieruchomosc nieruchomosc) {
        this.nieruchomosc = nieruchomosc;
    }

    public TypOgloszenia getTypOgloszenia() {
        return typOgloszenia;
    }

    public void setTypOgloszenia(TypOgloszenia typOgloszenia) {
        this.typOgloszenia = typOgloszenia;
    }

    public Collection<Ulubione> getUlubioneCollection() {
        return ulubioneCollection;
    }

    public void setUlubioneCollection(Collection<Ulubione> ulubioneCollection) {
        this.ulubioneCollection = ulubioneCollection;
    }

    public Collection<Spotkanie> getSpotkanieCollection() {
        return spotkanieCollection;
    }

    public void setSpotkanieCollection(Collection<Spotkanie> spotkanieCollection) {
        this.spotkanieCollection = spotkanieCollection;
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
        if (!(object instanceof Ogloszenie)) {
            return false;
        }
        Ogloszenie other = (Ogloszenie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.Ogloszenie[ id=" + id + " ]";
    }
    
}
