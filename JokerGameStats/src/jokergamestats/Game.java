/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokergamestats;

import java.io.Serializable;
import java.util.List;
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
 *
 * @author gkiop
 */
@Entity
@Table(name = "GAME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
    , @NamedQuery(name = "Game.findByDrawid", query = "SELECT g FROM Game g WHERE g.drawid = :drawid")
    , @NamedQuery(name = "Game.findByNumber1", query = "SELECT g FROM Game g WHERE g.number1 = :number1")
    , @NamedQuery(name = "Game.findByNumber2", query = "SELECT g FROM Game g WHERE g.number2 = :number2")
    , @NamedQuery(name = "Game.findByNumber3", query = "SELECT g FROM Game g WHERE g.number3 = :number3")
    , @NamedQuery(name = "Game.findByNumber4", query = "SELECT g FROM Game g WHERE g.number4 = :number4")
    , @NamedQuery(name = "Game.findByNumber5", query = "SELECT g FROM Game g WHERE g.number5 = :number5")
    , @NamedQuery(name = "Game.findByJoker", query = "SELECT g FROM Game g WHERE g.joker = :joker")})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DRAWID")
    private Integer drawid;
    @Basic(optional = false)
    @Column(name = "NUMBER1")
    private int number1;
    @Basic(optional = false)
    @Column(name = "NUMBER2")
    private int number2;
    @Basic(optional = false)
    @Column(name = "NUMBER3")
    private int number3;
    @Basic(optional = false)
    @Column(name = "NUMBER4")
    private int number4;
    @Basic(optional = false)
    @Column(name = "NUMBER5")
    private int number5;
    @Basic(optional = false)
    @Column(name = "JOKER")
    private int joker;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gamedrawid")
    private List<Prizecategories> prizecategoriesList;

    public Game() {
    }

    public Game(Integer drawid) {
        this.drawid = drawid;
    }

    public Game(Integer drawid, int number1, int number2, int number3, int number4, int number5, int joker) {
        this.drawid = drawid;
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
        this.number5 = number5;
        this.joker = joker;
    }

    public Integer getDrawid() {
        return drawid;
    }

    public void setDrawid(Integer drawid) {
        this.drawid = drawid;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }

    public int getNumber4() {
        return number4;
    }

    public void setNumber4(int number4) {
        this.number4 = number4;
    }

    public int getNumber5() {
        return number5;
    }

    public void setNumber5(int number5) {
        this.number5 = number5;
    }

    public int getJoker() {
        return joker;
    }

    public void setJoker(int joker) {
        this.joker = joker;
    }

    @XmlTransient
    public List<Prizecategories> getPrizecategoriesList() {
        return prizecategoriesList;
    }

    public void setPrizecategoriesList(List<Prizecategories> prizecategoriesList) {
        this.prizecategoriesList = prizecategoriesList;
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.drawid == null && other.drawid != null) || (this.drawid != null && !this.drawid.equals(other.drawid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jokergamestats.Game[ drawid=" + drawid + " ]";
    }
    
}
