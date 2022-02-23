/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokergamestats;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gkiop
 */
@Entity
@Table(name = "DRAWS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Draws.findAll", query = "SELECT d FROM Draws d")
    , @NamedQuery(name = "Draws.findByDrawid", query = "SELECT d FROM Draws d WHERE d.drawid = :drawid")
    , @NamedQuery(name = "Draws.findByDrawtime", query = "SELECT d FROM Draws d WHERE d.drawtime = :drawtime")
    , @NamedQuery(name = "Draws.findByStatus", query = "SELECT d FROM Draws d WHERE d.status = :status")
    , @NamedQuery(name = "Draws.findByDrawbreak", query = "SELECT d FROM Draws d WHERE d.drawbreak = :drawbreak")
    , @NamedQuery(name = "Draws.findByVisualdraw", query = "SELECT d FROM Draws d WHERE d.visualdraw = :visualdraw")
    , @NamedQuery(name = "Draws.findByPricepoints", query = "SELECT d FROM Draws d WHERE d.pricepoints = :pricepoints")
    , @NamedQuery(name = "Draws.findByWinninglist", query = "SELECT d FROM Draws d WHERE d.winninglist = :winninglist")
    , @NamedQuery(name = "Draws.findByWinningbonus", query = "SELECT d FROM Draws d WHERE d.winningbonus = :winningbonus")
    , @NamedQuery(name = "Draws.findByColumns", query = "SELECT d FROM Draws d WHERE d.columns = :columns")
    , @NamedQuery(name = "Draws.findByWagers", query = "SELECT d FROM Draws d WHERE d.wagers = :wagers")
    , @NamedQuery(name = "Draws.findByAddon", query = "SELECT d FROM Draws d WHERE d.addon = :addon")})
public class Draws implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DRAWID")
    private Integer drawid;
    @Column(name = "DRAWTIME")
    private Integer drawtime;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "DRAWBREAK")
    private String drawbreak;
    @Column(name = "VISUALDRAW")
    private Integer visualdraw;
    @Column(name = "PRICEPOINTS")
    private String pricepoints;
    @Column(name = "WINNINGLIST")
    private String winninglist;
    @Column(name = "WINNINGBONUS")
    private String winningbonus;
    @Column(name = "COLUMNS")
    private Integer columns;
    @Column(name = "WAGERS")
    private Integer wagers;
    @Column(name = "ADDON")
    private String addon;
    @JoinColumn(name = "GAMEID", referencedColumnName = "GAMEID")
    @ManyToOne
    private Games gameid;
    @OneToMany(mappedBy = "drawid")
    private Collection<Prizecategories> prizecategoriesCollection;

    public Draws() {
    }

    public Draws(Integer drawid) {
        this.drawid = drawid;
    }

    public Integer getDrawid() {
        return drawid;
    }

    public void setDrawid(Integer drawid) {
        this.drawid = drawid;
    }

    public Integer getDrawtime() {
        return drawtime;
    }

    public void setDrawtime(Integer drawtime) {
        this.drawtime = drawtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDrawbreak() {
        return drawbreak;
    }

    public void setDrawbreak(String drawbreak) {
        this.drawbreak = drawbreak;
    }

    public Integer getVisualdraw() {
        return visualdraw;
    }

    public void setVisualdraw(Integer visualdraw) {
        this.visualdraw = visualdraw;
    }

    public String getPricepoints() {
        return pricepoints;
    }

    public void setPricepoints(String pricepoints) {
        this.pricepoints = pricepoints;
    }

    public String getWinninglist() {
        return winninglist;
    }

    public void setWinninglist(String winninglist) {
        this.winninglist = winninglist;
    }

    public String getWinningbonus() {
        return winningbonus;
    }

    public void setWinningbonus(String winningbonus) {
        this.winningbonus = winningbonus;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getWagers() {
        return wagers;
    }

    public void setWagers(Integer wagers) {
        this.wagers = wagers;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public Games getGameid() {
        return gameid;
    }

    public void setGameid(Games gameid) {
        this.gameid = gameid;
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
