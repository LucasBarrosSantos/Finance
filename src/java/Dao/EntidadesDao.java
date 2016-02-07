package Dao;

import java.util.List;

import Model.Entidade;

/**
 * @author lucas
 */
public interface EntidadesDao {

    public void adicionar(Entidade entidade);

    public void remover(Entidade entidade);

    public void atualizar(Entidade entidade);

    public Entidade pesquisarId(int id);

    public List<Entidade> listarTodos();
}
