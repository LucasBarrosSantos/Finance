/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lucas
 */
@Entity
@Table(name = "Eventos", catalog = "ControlesFinanceiros", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findById", query = "SELECT e FROM Evento e WHERE e.id = :id"),
    @NamedQuery(name = "Evento.findByDescricao", query = "SELECT e FROM Evento e WHERE e.descricao = :descricao"),
    @NamedQuery(name = "Evento.findByDatainicial", query = "SELECT e FROM Evento e WHERE e.datainicial = :datainicial"),
    @NamedQuery(name = "Evento.findByDatafinal", query = "SELECT e FROM Evento e WHERE e.datafinal = :datafinal"),
    @NamedQuery(name = "Evento.findByCriadoEm", query = "SELECT e FROM Evento e WHERE e.criadoEm = :criadoEm"),
    @NamedQuery(name = "Evento.findByCriadoPor", query = "SELECT e FROM Evento e WHERE e.criadoPor = :criadoPor"),
    @NamedQuery(name = "Evento.findByTitulo", query = "SELECT e FROM Evento e WHERE e.titulo = :titulo")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false)
    private Integer id;
    @Column(length = 45)
    private String descricao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainicial;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datafinal;
    @Temporal(TemporalType.TIMESTAMP)
    private Date criadoEm;
    @Column(length = 45)
    private String criadoPor;
    @Column(length = 45)
    private String titulo;

    public Evento() {
    }

    public Evento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(Date datainicial) {
        this.datainicial = datainicial;
    }

    public Date getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(Date datafinal) {
        this.datafinal = datafinal;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Evento{" + "id=" + id + ", descricao=" + descricao + ", datainicial=" + datainicial + ", datafinal=" + datafinal + ", criadoEm=" + criadoEm + ", criadoPor=" + criadoPor + ", titulo=" + titulo + '}';
    }
}
