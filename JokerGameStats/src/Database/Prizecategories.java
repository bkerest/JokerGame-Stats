/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
 * @author vker
 */
@Entity
@Table(name = "PRIZECATEGORIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prizecategories.findAll", query = "SELECT p FROM Prizecategories p")
    , @NamedQuery(name = "Prizecategories.findByPid", query = "SELECT p FROM Prizecategories p WHERE p.pid = :pid")
    , @NamedQuery(name = "Prizecategories.findByCategoryid", query = "SELECT p FROM Prizecategories p WHERE p.categoryid = :categoryid")
    , @NamedQuery(name = "Prizecategories.findByDivident", query = "SELECT p FROM Prizecategories p WHERE p.divident = :divident")
    , @NamedQuery(name = "Prizecategories.findByWinners", query = "SELECT p FROM Prizecategories p WHERE p.winners = :winners")
    , @NamedQuery(name = "Prizecategories.findByDistribut", query = "SELECT p FROM Prizecategories p WHERE p.distribut = :distribut")
    , @NamedQuery(name = "Prizecategories.findByJackpot", query = "SELECT p FROM Prizecategories p WHERE p.jackpot = :jackpot")
    , @NamedQuery(name = "Prizecategories.findByFixed", query = "SELECT p FROM Prizecategories p WHERE p.fixed = :fixed")
    , @NamedQuery(name = "Prizecategories.findByCategorytype", query = "SELECT p FROM Prizecategories p WHERE p.categorytype = :categorytype")
    , @NamedQuery(name = "Prizecategories.findByGametype", query = "SELECT p FROM Prizecategories p WHERE p.gametype = :gametype")})
public class Prizecategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PID")
    private Integer pid;
    @Column(name = "CATEGORYID")
    private Integer categoryid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIVIDENT")
    private Double divident;
    @Column(name = "WINNERS")
    private Integer winners;
    @Column(name = "DISTRIBUT")
    private Double distribut;
    @Column(name = "JACKPOT")
    private Double jackpot;
    @Column(name = "FIXED")
    private Double fixed;
    @Column(name = "CATEGORYTYPE")
    private Integer categorytype;
    @Column(name = "GAMETYPE")
    private String gametype;
    @JoinColumn(name = "DRAWID", referencedColumnName = "DRAWID")
    @ManyToOne
    private Draws drawid;

    public Prizecategories() {
    }

    public Prizecategories(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Double getDivident() {
        return divident;
    }

    public void setDivident(Double divident) {
        this.divident = divident;
    }

    public Integer getWinners() {
        return winners;
    }

    public void setWinners(Integer winners) {
        this.winners = winners;
    }

    public Double getDistribut() {
        return distribut;
    }

    public void setDistribut(Double distribut) {
        this.distribut = distribut;
    }

    public Double getJackpot() {
        return jackpot;
    }

    public void setJackpot(Double jackpot) {
        this.jackpot = jackpot;
    }

    public Double getFixed() {
        return fixed;
    }

    public void setFixed(Double fixed) {
        this.fixed = fixed;
    }

    public Integer getCategorytype() {
        return categorytype;
    }

    public void setCategorytype(Integer categorytype) {
        this.categorytype = categorytype;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public Draws getDrawid() {
        return drawid;
    }

    public void setDrawid(Draws drawid) {
        this.drawid = drawid;
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
        return "Database.Prizecategories[ pid=" + pid + " ]";
    }
    
}
