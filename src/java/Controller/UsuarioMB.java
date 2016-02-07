package Controller;

import Jsf.FacesUtil;
import Sessao.SessionUtil;
import Dao.UsuarioDaoImp;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import Model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioMB {

    private Usuario usuario;
    private List<Usuario> usuarios;
    private UsuarioDaoImp dao;
    private static final long serialVersionUID = 1L;

    public UsuarioMB() {
        usuario = new Usuario();
        usuarios = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        if (usuario != null) {
            dao = new UsuarioDaoImp();
            usuarios = dao.listarTodos();
        }
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String add() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (usuario.getNome().isEmpty()
                    || usuario.getSenha().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                " Erro de Cadastrado! ", null));
                return "/form-novo-usuario?faces-redirect=true";
            } else {
                dao = new UsuarioDaoImp();

                usuarios = dao.usuariosCadastrados(usuario.getNome().trim(), usuario.getSenha().trim());

                if (usuarios.size() > 0) {

                    FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                " Usuário Cadastrado! ", null));

                    return "/form-novo-usuario?faces-redirect=true";
                } else {
                    usuario.setCriadoEm(new Date());
                    usuario.setIsLogado(false);
                    dao.adicionar(usuario);
                    usuarios.add(usuario);

                    FacesUtil.addInfoMessage("Usuário Cadastrado!");
                    usuario = new Usuario();
                    return "/consultar-titulo?faces-redirect=true";
                }

            }
        } catch (Exception e) {
            FacesUtil.addErrorMessage(" Erro de Cadastrado! ");
        }
        return null;
    }

    public String login() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        dao = new UsuarioDaoImp();

        usuario = validarUsuario(usuario.getNome(), usuario.getSenha());

        if (usuario != null) {
            usuario.setIsLogado(true);
            dao.atualizar(usuario);

            SessionUtil.setParam("usuario", usuario); // Adiciona o usuário a sessão
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            " Bem-Vindo " + usuario.getNome() + " !!", null));
            usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            return "/consultar-titulo?faces-redirect=true";
        } else {
            usuario = new Usuario();
            FacesMessage mensagem = new FacesMessage(
                    "Usuário/senha inválido!");
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
            return "/login" + extensao();
        }
    }

    public String user() {
        return getUsuario().toString();
    }

    public void preCadastro() {
        this.usuarios = getUsuarios();
    }

    public String titulos() {
        return "/form-novo-titulo?faces-redirect=true";
    }

    public String novoUsuario() {
        usuario = new Usuario();
        return "/form-novo-usuario?faces-redirect=true";
    }

    public void logout() {
        usuario.setIsLogado(true);
        dao.atualizar(usuario);
    }

    public String usuarioLogado() {
        return new UsuarioDaoImp().usuarioLogado(usuarioDaSessao(), senhaUsuarioDaSessao());
    }

    public boolean isLogado() {
        return new UsuarioDaoImp().usuarioLogado(usuarioDaSessao(), senhaUsuarioDaSessao()) != null;
    }

    public String voltar() {
        usuario = new Usuario();
        return "/login?faces-redirect=true";
    }

    public String extensao() {
        return ".xhtml";
    }

    public Usuario validarUsuario(String nome, String senha) throws UnsupportedEncodingException {
        List<Usuario> users = dao.usuariosCadastrados(nome, senha);
        if (users.isEmpty()) {
            return null;
        } else {
            for (Usuario u : users) {
                usuario = u;
            }
            return usuario.getId() == null ? null : usuario;
        }
    }

    /**
     *
     * @return Usuario da Sessão Atual
     */
    public String usuarioDaSessao() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usuario.getNome();
    }

    /**
     *
     * @return Senha do Usuário da Sessão
     */
    public String senhaUsuarioDaSessao() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usuario.getSenha();
    }

    public String inicio() {
        SessionUtil.remove("usuario"); // Remove o usuário da sessão
        usuario.setIsLogado(false);
        dao.atualizar(usuario);
        usuario = new Usuario();
        return "/login?faces-redirect=true";
    }
}
