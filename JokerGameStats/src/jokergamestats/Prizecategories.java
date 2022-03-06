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
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

@Entity
@Table(catalog = "", schema = "JOKER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prizecategories.findAll", query = "SELECT p FROM Prizecategories p")
    , @NamedQuery(name = "Prizecategories.findByPid", query = "SELECT p FROM Prizecategories p WHERE p.pid = :pid")
    , @NamedQuery(name = "Prizecategories.findByCategoryid", query = "SELECT p FROM Prizecategories p WHERE p.categoryid = :categoryid")
    , @NamedQuery(name = "Prizecategories.findByDivedent", query = "SELECT p FROM Prizecategories p WHERE p.divedent = :divedent")
    , @NamedQuery(name = "Prizecategories.findByWinners", query = "SELECT p FROM Prizecategories p WHERE p.winners = :winners")
    , @NamedQuery(name = "Prizecategories.deleteByDrawID", query = "DELETE FROM Prizecategories p WHERE p.drawidFk = :drawidFk")})

public class Prizecategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer pid;
    @Basic(optional = false)
    @Column(nullable = false)
    private int categoryid;
    @Basic(optional = false)
    @Column(nullable = false)
    private float divedent;
    @Basic(optional = false)
    @Column(nullable = false)
    private int winners;
    @JoinColumn(name = "DRAWID_FK", referencedColumnName = "DRAWID", nullable = false)
    @ManyToOne(optional = false)
    private Draws drawidFk;

    public Prizecategories() {
    }

    public Prizecategories(Integer pid) {
        this.pid = pid;
    }

    public Prizecategories(Integer pid, int categoryid, float divedent, int winners) {
        this.pid = pid;
        this.categoryid = categoryid;
        this.divedent = divedent;
        this.winners = winners;
    }
    
        public Prizecategories(int categoryid, float divedent, int winners, Draws drawId) {
        this.categoryid = categoryid;
        this.divedent = divedent;
        this.winners = winners;
        this.drawidFk = drawId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public float getDivedent() {
        return divedent;
    }

    public void setDivedent(float divedent) {
        this.divedent = divedent;
    }

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public Draws getDrawidFk() {
        return drawidFk;
    }

    public void setDrawidFk(Draws drawidFk) {
        this.drawidFk = drawidFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prizecategories)) {
            return false;
        }
        Prizecategories other = (Prizecategories) object;
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jokergamestats.Prizecategories[ pid=" + pid + " ]";
    }
    
}
