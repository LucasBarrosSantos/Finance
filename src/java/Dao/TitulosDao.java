package Dao;

import java.util.List;

import Model.Titulo;

/**
 * @author lucas
 */
public interface TitulosDao {

    public void adicionar(Titulo titulo);

    public void remover(Titulo titulo);

    public void atualizar(Titulo titulo);

    public Titulo pesquisarId(int id);

    public List<Titulo> listarTodos();

}
