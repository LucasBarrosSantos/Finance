package Dao;

import java.util.List;
import Model.Contato;


public interface ContatosDao {

    public void adicionar(Contato contato);

    public void remover(Contato contato);

    public void atualizar(Contato contato);

    public Contato pesquisarId(int id);

    public List<Contato> listarTodos();
}
