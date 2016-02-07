package Dao;

import ConexaoPU.PersistenceManager;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import Model.Usuario;

/**
 * @author lucas
 */
public class UsuarioDaoImp implements UsuarioDao {

    @Override
    public void adicionar(Usuario usuario) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(usuario.getSenha().getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            String senha = hexString.toString();

            usuario.setSenha(senha);
            em.persist(usuario);

            em.getTransaction().commit();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void remover(Usuario usuario) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Usuario.class, usuario.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario pesquisarId(int id) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuario AS u WHERE u.id = :id");
            query.setParameter("id", id);
            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuario AS u");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Usuario> login() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuario AS u");
            return (List<Usuario>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Usuario> usuariosLogados() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("select u from Usuario as u where u.isLogado = TRUE");
            return (List<Usuario>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario usuarioCadastrado(String nome, String senha) throws Exception {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {

            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            String senhaMD5 = hexString.toString();

            Query query = em.createQuery("SELECT u FROM Usuario AS u WHERE u.nome = :nome AND u.senha = :senha");
            query.setParameter("nome", nome.trim());
            query.setParameter("senha", senhaMD5.trim());
            return (Usuario) query.getSingleResult();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }

    public List<Usuario> usuariosCadastrados(String nome, String senha) throws UnsupportedEncodingException {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {

            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            String senhaMD5 = hexString.toString();

            Query query = em.createQuery("SELECT u FROM Usuario AS u WHERE u.nome = :nome AND u.senha = :senha");
            query.setParameter("nome", nome.trim());
            query.setParameter("senha", senhaMD5.trim());
            return (List<Usuario>) query.getResultList();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String usuarioLogado(String nome, String senha) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("SELECT u.nome FROM Usuario AS u WHERE u.isLogado = TRUE and u.nome = :nome and u.senha = :senha");
        query.setParameter("nome", nome);
        query.setParameter("senha", senha);
        return (String) query.getSingleResult();
    }
}
