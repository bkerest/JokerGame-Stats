/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokergamestats;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gkiop
 */
@Entity
@Table(name = "PRIZECATEGORIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prizecategories.findAll", query = "SELECT p FROM Prizecategories p")
    , @NamedQuery(name = "Prizecategories.findByAa", query = "SELECT p FROM Prizecategories p WHERE p.aa = :aa")
    , @NamedQuery(name = "Prizecategories.findById", query = "SELECT p FROM Prizecategories p WHERE p.id = :id")
    , @NamedQuery(name = "Prizecategories.findByWinners", query = "SELECT p FROM Prizecategories p WHERE p.winners = :winners")
    , @NamedQuery(name = "Prizecategories.findByDivident", query = "SELECT p FROM Prizecategories p WHERE p.divident = :divident")})
public class Prizecategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AA")
    private Integer aa;
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Basic(optional = false)
    @Column(name = "WINNERS")
    private int winners;
    @Basic(optional = false)
    @Column(name = "DIVIDENT")
    private double divident;
    @JoinColumn(name = "GAMEDRAWID", referencedColumnName = "DRAWID")
    @ManyToOne(optional = false)
    private Game gamedrawid;

    public Prizecategories() {
    }

    public Prizecategories(Integer aa) {
        this.aa = aa;
    }

    public Prizecategories(Integer aa, int id, int winners, double divident) {
        this.aa = aa;
        this.id = id;
        this.winners = winners;
        this.divident = divident;
    }

    public Integer getAa() {
        return aa;
    }

    public void setAa(Integer aa) {
        this.aa = aa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public double getDivident() {
        return divident;
    }

    public void setDivident(double divident) {
        this.divident = divident;
    }

    public Game getGamedrawid() {
        return gamedrawid;
    }

    public void setGamedrawid(Game gamedrawid) {
        this.gamedrawid = gamedrawid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aa != null ? aa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prizecategories)) {
            return false;
        }
        Prizecategories other = (Prizecategories) object;
        if ((this.aa == null && other.aa != null) || (this.aa != null && !this.aa.equals(other.aa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jokergamestats.Prizecategories[ aa=" + aa + " ]";
    }
    
}
