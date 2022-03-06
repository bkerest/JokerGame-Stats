/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokergamestats;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

@Entity
@Table(catalog = "", schema = "JOKER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Draws.findAll", query = "SELECT d FROM Draws d")
    , @NamedQuery(name = "Draws.findByDrawid", query = "SELECT d FROM Draws d WHERE d.drawid = :drawid")
    , @NamedQuery(name = "Draws.findByWinningno1", query = "SELECT d FROM Draws d WHERE d.winningno1 = :winningno1")
    , @NamedQuery(name = "Draws.findByWinningno2", query = "SELECT d FROM Draws d WHERE d.winningno2 = :winningno2")
    , @NamedQuery(name = "Draws.findByWinningno3", query = "SELECT d FROM Draws d WHERE d.winningno3 = :winningno3")
    , @NamedQuery(name = "Draws.findByWinningno4", query = "SELECT d FROM Draws d WHERE d.winningno4 = :winningno4")
    , @NamedQuery(name = "Draws.findByWinningno5", query = "SELECT d FROM Draws d WHERE d.winningno5 = :winningno5")
    , @NamedQuery(name = "Draws.findByWinningbonus", query = "SELECT d FROM Draws d WHERE d.winningbonus = :winningbonus")
    , @NamedQuery(name = "Draws.deleteByDrawid", query = "DELETE FROM Draws d WHERE d.drawid = :drawid")})

public class Draws implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer drawid;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String winningno1;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String winningno2;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String winningno3;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String winningno4;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String winningno5;
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String winningbonus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drawidFk")
    private Collection<Prizecategories> prizecategoriesCollection;

    public Draws() {
    }

    public Draws(Integer drawid) {
        this.drawid = drawid;
    }

    public Draws(Integer drawid, String winningno1, String winningno2, String winningno3, String winningno4, String winningno5, String winningbonus) {
        this.drawid = drawid;
        this.winningno1 = winningno1;
        this.winningno2 = winningno2;
        this.winningno3 = winningno3;
        this.winningno4 = winningno4;
        this.winningno5 = winningno5;
        this.winningbonus = winningbonus;
    }

    public Integer getDrawid() {
        return drawid;
    }

    public void setDrawid(Integer drawid) {
        this.drawid = drawid;
    }

    public String getWinningno1() {
        return winningno1;
    }

    public void setWinningno1(String winningno1) {
        this.winningno1 = winningno1;
    }

    public String getWinningno2() {
        return winningno2;
    }

    public void setWinningno2(String winningno2) {
        this.winningno2 = winningno2;
    }

    public String getWinningno3() {
        return winningno3;
    }

    public void setWinningno3(String winningno3) {
        this.winningno3 = winningno3;
    }

    public String getWinningno4() {
        return winningno4;
    }

    public void setWinningno4(String winningno4) {
        this.winningno4 = winningno4;
    }

    public String getWinningno5() {
        return winningno5;
    }

    public void setWinningno5(String winningno5) {
        this.winningno5 = winningno5;
    }

    public String getWinningbonus() {
        return winningbonus;
    }

    public void setWinningbonus(String winningbonus) {
        this.winningbonus = winningbonus;
    }

    @XmlTransient
    public Collection<Prizecategories> getPrizecategoriesCollection() {
        return prizecategoriesCollection;
    }

    public void setPrizecategoriesCollection(Collection<Prizecategories> prizecategoriesCollection) {
        this.prizecategoriesCollection = prizecategoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (drawid != null ? drawid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Draws)) {
            return false;
        }
        Draws other = (Draws) object;
        if ((this.drawid == null && other.drawid != null) || (this.drawid != null && !this.drawid.equals(other.drawid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jokergamestats.Draws[ drawid=" + drawid + " ]";
    }
    
}
