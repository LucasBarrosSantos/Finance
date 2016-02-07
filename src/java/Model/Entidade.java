package Model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author lucas
 */
@Entity
@Table(name = "Entidades", catalog = "ControlesFinanceiros", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidade.findAll", query = "SELECT e FROM Entidade e"),
    @NamedQuery(name = "Entidade.findById", query = "SELECT e FROM Entidade e WHERE e.id = :id"),
    @NamedQuery(name = "Entidade.findByBairro", query = "SELECT e FROM Entidade e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "Entidade.findByCep", query = "SELECT e FROM Entidade e WHERE e.cep = :cep"),
    @NamedQuery(name = "Entidade.findByCidade", query = "SELECT e FROM Entidade e WHERE e.cidade = :cidade"),
    @NamedQuery(name = "Entidade.findByCpf", query = "SELECT e FROM Entidade e WHERE e.cpf = :cpf"),
    @NamedQuery(name = "Entidade.findByEndereco", query = "SELECT e FROM Entidade e WHERE e.endereco = :endereco"),
    @NamedQuery(name = "Entidade.findByNome", query = "SELECT e FROM Entidade e WHERE e.nome = :nome"),
    @NamedQuery(name = "Entidade.findByTelefone1", query = "SELECT e FROM Entidade e WHERE e.telefone1 = :telefone1"),
    @NamedQuery(name = "Entidade.findByTelefone2", query = "SELECT e FROM Entidade e WHERE e.telefone2 = :telefone2"),
    @NamedQuery(name = "Entidade.findByUf", query = "SELECT e FROM Entidade e WHERE e.uf = :uf")})
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 45)
    private String bairro;
    @Column(length = 45)
    private String senha;
    @Column(length = 45)
    private String cep;
    @Column(length = 45)
    private String cidade;
    @Column(length = 45)
    private String cpf;
    @Column(length = 45)
    private String endereco;
    @Column(length = 45)
    private String nome;
    @Column(length = 45)
    private String telefone1;
    @Column(length = 45)
    private String telefone2;
    @Column(length = 2)
    private String uf;
    @Column(length = 45)
    private String criadoPor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadeId")
    private List<Titulo> tituloList;

    public Entidade() {
    }

    public Entidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @XmlTransient
    public List<Titulo> getTituloList() {
        return tituloList;
    }

    public void setTituloList(List<Titulo> tituloList) {
        this.tituloList = tituloList;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
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
        if (!(object instanceof Entidade)) {
            return false;
        }
        Entidade other = (Entidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Entidade[ id=" + id + " ]";
    }

}
