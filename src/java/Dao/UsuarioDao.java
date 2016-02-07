package Dao;

import java.util.List;

import Model.Usuario;

public interface UsuarioDao {
    public void adicionar(Usuario usuario);

    public void remover(Usuario usuario);

    public void atualizar(Usuario usuario);

    public Usuario pesquisarId(int id);

    public List<Usuario> listarTodos();
}
