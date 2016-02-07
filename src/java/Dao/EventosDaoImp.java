package Dao;

import ConexaoPU.PersistenceManager;
import Model.Evento;
import Model.Usuario;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author lucas
 */
public class EventosDaoImp {
    
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
     * @param evento
     */
    public void adicionar(Evento evento) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(evento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    /**
     * @param evento
     */
    public void remover(Evento evento) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Evento.class, evento.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Evento pesquisarId(int id) {
        try {
            EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            Query query = em.createQuery("SELECT e FROM Evento AS e WHERE e.id = :id");
            query.setParameter("id", id);
            return (Evento) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Evento> listarTodos() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        List<Usuario> usuariologado = (List<Usuario>) usuarioLogado(getNome(), getSenha());
        Query query = em.createQuery("SELECT e FROM Evento AS e WHERE e.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome()+getSenha());
        return query.getResultList();
    }

    /**
     * @param evento
     */
    
    public void atualizar(Evento evento) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(evento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
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

    public List<Evento> pesquisar(String title, Date startDate, Date endDate, String title0, String string) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("SELECT e FROM Evento AS e WHERE e.titulo = :title OR e.datainicial = :startDate OR e.datafinal = :endDate OR e.descricao = :title0 OR e.criadoPor = :string");
        
        query.setParameter("title", title);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("title0", title0);
        query.setParameter("string", string);
        
        return query.getResultList();
    }
    
    
}
