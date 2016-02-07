package Dao;

import ConexaoPU.PersistenceManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Model.Entidade;
import Model.Usuario;

/**
 * @author lucas
 */
public class EntidadesDaoImp implements EntidadesDao {

    private String nome;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @param entidade
     */
    @Override
    public void adicionar(Entidade entidade) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    /**
     * @param entidade
     */
    @Override
    public void remover(Entidade entidade) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Entidade.class, entidade.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Entidade pesquisarId(int id) {
        try {
            EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            Query query = em.createQuery("SELECT e FROM Entidade AS e WHERE e.id = :id");
            query.setParameter("id", id);
            return (Entidade) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Entidade> listarTodos() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        List<Usuario> usuariologado = usuarioLogado(getNome(), getSenha());
        Query query = em.createQuery("SELECT e FROM Entidade AS e WHERE e.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome()+getSenha());
        return query.getResultList();
    }

    /**
     * @param entidade
     */
    @Override
    public void atualizar(Entidade entidade) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<String> clientesQueContem(String cliente) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();

        TypedQuery<String> query;
        query = em.createQuery("SELECT DISTINCT e.nome FROM Entidade AS e "
                + "WHERE e.nome LIKE :cliente", String.class);
        query.setParameter("cliente", "%" + cliente + "%");
        return query.getResultList();
    }

    public List<Usuario> usuarioLogado(String nome, String senha) {
        setNome(nome);
        setSenha(senha);
        
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario AS u WHERE u.isLogado = 1 and u.nome = :nome and u.senha = :senha");
        query.setParameter("nome", nome);
        query.setParameter("senha", senha);
        return (List<Usuario>) query.getResultList();
    }
}
