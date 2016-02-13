/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import ConexaoPU.PersistenceManager;
import java.util.Date;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import Model.Titulo;
import Model.Usuario;

/**
 * @author lucas
 */
public class TitulosDaoImp implements TitulosDao {

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
     * @param titulo
     */
    @Override
    public void adicionar(Titulo titulo) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(titulo);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    /**
     * @param titulo
     */
    @Override
    public void remover(Titulo titulo) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Titulo.class, titulo.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Titulo pesquisarId(int id) {
        try {
            EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            Query query = em.createQuery("SELECT t FROM Titulo AS t WHERE t.id = :id");
            query.setParameter("id", id);
            return (Titulo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Titulo> listarTodos() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("SELECT t FROM Titulo AS t WHERE t.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }

    /**
     * @param titulo
     */
    @Override
    public void atualizar(Titulo titulo) {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(titulo);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<Titulo> mesesGraficos() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select cast(t.datadoPagamento as date) from Titulo as t where t.tipo = 0 and t.datadoPagamento is not null AND t.valorPago IS NOT NULL AND t.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }

    public List<Titulo> valores() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select t.valorPago from Titulo as t where t.tipo = 0 and t.datadoPagamento is not null AND t.valorPago IS NOT NULL AND t.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }

    public List<Titulo> mesesGraficos_Saida() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select cast(t.datadoPagamento as date) from Titulo as t where t.tipo = 1 and t.datadoPagamento is not null AND t.valorPago IS NOT NULL AND t.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }

    public List<Titulo> valores_Saida() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select t.valorPago from Titulo as t where t.tipo = 1 and t.datadoPagamento is not null AND t.valorPago IS NOT NULL AND t.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }

    public Double saldo() throws Exception {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        List<Usuario> usuariologado = usuarioLogado(getNome(), getSenha());

        if (usuariologado != null) {

            Query query = em.createQuery("SELECT SUM(t.valorPago) FROM Titulo AS t WHERE t.tipo = 0 AND "
                    + "t.situacao = 1 and t.criadoPor =:usuariologado or "
                    + "(t.situacao = 0 and t.datadoPagamento is not null "
                    + "and t.criadoPor = :usuariologado) AND t.tipo = 0", Double.class);

            query.setParameter("usuariologado", getNome() + getSenha());
            if (query.getSingleResult() != null) {
                return (double) Double.parseDouble(query.getSingleResult().toString());
            } else {
                return (double) 0;
            }
        } else {
            return (double) 0;
        }

    }

    public Double saldoDevedor() throws Exception {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        List<Usuario> usuariologado = usuarioLogado(getNome(), getSenha());

        if (usuariologado != null) {
            Query query = em.createQuery("SELECT sum(t.valorPago) FROM Titulo as t "
                    + "where t.tipo = 1 and t.situacao = 1 AND t.criadoPor = :usuariologado OR "
                    + "(t.tipo = 1 and t.situacao = 0 AND t.criadoPor = :usuariologado)", Double.class);
            query.setParameter("usuariologado", getNome() + getSenha());

            if (query.getSingleResult() != null) {
                return Double.parseDouble(query.getSingleResult().toString());
            } else {
                return (double) 0;
            }
        } else {
            return (double) 0;
        }

    }

    public List<Date> meses() throws Exception {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select t.datadeEmissao from Titulo as t "
                + "WHERE t.valor >= 700 AND t.criadoPor = :usuariologado");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }

    public List<Usuario> usuarioLogado(String nome, String senha) {

        setNome(nome);
        setSenha(senha);

        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario AS u WHERE u.isLogado = TRUE AND u.nome = :nome and u.senha = :senha");

        query.setParameter("nome", nome);
        query.setParameter("senha", senha);

        return (List<Usuario>) query.getResultList();
    }

    public Object usuariosLogado() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario AS u WHERE u.isLogado = TRUE");
        return query.getResultList();
    }

    public List<Titulo> graficoDeDespessas() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select t from Titulo as t where t.criadoPor = :usuariologado group by t.entidadeId");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }

    public List<Titulo> valoresGraficoDeDespessas() {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select sum(t.valor) from Titulo as t where t.criadoPor = :usuariologado group by t.entidadeId");
        query.setParameter("usuariologado", getNome() + getSenha());
        return query.getResultList();
    }
}
