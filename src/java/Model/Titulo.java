package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author lucas
 */
@Entity
@Table(name = "Titulos", catalog = "ControlesFinanceiros", schema = "")
public class Titulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date criadoEm;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datadeEmissao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datadeValidade;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datadoPagamento;
    @Lob
    @Column(length = 2147483647)
    private String descricao;
    @Column(length = 45)
    private String modificadoEm;
    @Column(length = 45)
    private String criadoPor;
    private Integer situacao;
    private Integer tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 10, scale = 2)
    private Double valor;
    @Column(precision = 10, scale = 2)
    private Double valorPago;
    @JoinColumn(name = "entidadeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Entidade entidadeId;
    
    public Titulo() {
    }

    public Titulo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Date getDatadeEmissao() {
        return datadeEmissao;
    }

    public void setDatadeEmissao(Date datadeEmissao) {
        this.datadeEmissao = datadeEmissao;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }
    
    public Date getDatadeValidade() {
        return datadeValidade;
    }

    public void setDatadeValidade(Date datadeValidade) {
        this.datadeValidade = datadeValidade;
    }

    public Date getDatadoPagamento() {
        return datadoPagamento;
    }

    public void setDatadoPagamento(Date datadoPagamento) {
        this.datadoPagamento = datadoPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getModificadoEm() {
        return modificadoEm;
    }

    public void setModificadoEm(String modificadoEm) {
        this.modificadoEm = modificadoEm;
    }

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Entidade getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(Entidade entidadeId) {
        this.entidadeId = entidadeId;
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
        if (!(object instanceof Titulo)) {
            return false;
        }
        Titulo other = (Titulo) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "model.Titulo[ id=" + id + " ]";
    }

}
