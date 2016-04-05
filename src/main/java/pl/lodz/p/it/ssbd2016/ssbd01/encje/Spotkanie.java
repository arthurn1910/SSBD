/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2016.ssbd01.encje;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author java
 */
@Entity
@Table(name = "spotkanie")
@NamedQueries({
    @NamedQuery(name = "Spotkanie.findAll", query = "SELECT s FROM Spotkanie s"),
    @NamedQuery(name = "Spotkanie.findById", query = "SELECT s FROM Spotkanie s WHERE s.id = :id"),
    @NamedQuery(name = "Spotkanie.findByDataSpotkania", query = "SELECT s FROM Spotkanie s WHERE s.dataSpotkania = :dataSpotkania"),
    @NamedQuery(name = "Spotkanie.findByVersion", query = "SELECT s FROM Spotkanie s WHERE s.version = :version")})
public class Spotkanie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_spotkania")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSpotkania;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "id_uzytkownika", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Konto idUzytkownika;
    @JoinColumn(name = "id_ogloszenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ogloszenie idOgloszenia;

    public Spotkanie() {
    }

    public Spotkanie(Long id) {
        this.id = id;
    }

    public Spotkanie(Long id, Date dataSpotkania, long version) {
        this.id = id;
        this.dataSpotkania = dataSpotkania;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataSpotkania() {
        return dataSpotkania;
    }

    public void setDataSpotkania(Date dataSpotkania) {
        this.dataSpotkania = dataSpotkania;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Konto getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(Konto idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
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
        if (!(object instanceof Spotkanie)) {
            return false;
        }
        Spotkanie other = (Spotkanie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2016.ssbd01.encje.Spotkanie[ id=" + id + " ]";
    }
    
}
