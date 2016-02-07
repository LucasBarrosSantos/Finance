package Dao;

import ConexaoPU.PersistenceManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import Model.Contato;

/**
 *
 * @author lucas
 */
public class ContatosDaoImp implements ContatosDao{

    @Override
    public void adicionar(Contato contato) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(contato);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void remover(Contato contato) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Contato.class, contato.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void atualizar(Contato contato) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(contato);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Contato pesquisarId(int id) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Contato AS c WHERE c.id = :id");
            query.setParameter("id", id);
            return (Contato) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Contato> listarTodos() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Contato AS c");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
