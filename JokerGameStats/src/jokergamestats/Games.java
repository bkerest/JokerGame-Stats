/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jokergamestats;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 *
 * @author vker
 */
@Entity
@Table(name = "GAMES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Games.findAll", query = "SELECT g FROM Games g")
    , @NamedQuery(name = "Games.findByGameid", query = "SELECT g FROM Games g WHERE g.gameid = :gameid")
    , @NamedQuery(name = "Games.findByGamename", query = "SELECT g FROM Games g WHERE g.gamename = :gamename")})
public class Games implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GAMEID")
    private Integer gameid;
    @Column(name = "GAMENAME")
    private String gamename;
    @OneToMany(mappedBy = "gameid")
    private List<Draws> drawsList;

    public Games() {
    }

    public Games(Integer gameid) {
        this.gameid = gameid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    @XmlTransient
    public List<Draws> getDrawsList() {
        return drawsList;
    }

    public void setDrawsList(List<Draws> drawsList) {
        this.drawsList = drawsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameid != null ? gameid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Games)) {
            return false;
        }
        Games other = (Games) object;
        if ((this.gameid == null && other.gameid != null) || (this.gameid != null && !this.gameid.equals(other.gameid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.Games[ gameid=" + gameid + " ]";
    }
    
}
