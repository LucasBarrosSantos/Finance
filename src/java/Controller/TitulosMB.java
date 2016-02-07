package Controller;

import Dao.TitulosDaoImp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import Model.Titulo;

@ManagedBean(name = "TitulosBean")
@SessionScoped
public class TitulosMB {

    @ManagedProperty(value = "#{usuarioMB}")
    private UsuarioMB usuarioMB;

    private Titulo titulo;
    private List<Titulo> titulos;
    private TitulosDaoImp dao;
    
    public TitulosMB() throws Exception {
        titulo = new Titulo();
        titulos = new ArrayList<>();
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public List<Titulo> getTitulos() {
        if (titulo != null) {
            dao = new TitulosDaoImp();
            this.dao.usuarioLogado(getUsuarioMB().getUsuario().getNome(), 
                getUsuarioMB().getUsuario().getSenha());
            titulos = dao.listarTodos();
        }
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    public TitulosDaoImp getDao() {
        return dao;
    }

    public void setDao(TitulosDaoImp dao) {
        this.dao = dao;
    }

    public String add() {
        try {

            dao = new TitulosDaoImp();

            if (titulo.getDatadeEmissao().after(titulo.getDatadeValidade())) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                " Data de Validade não pode ser menor que a data de Emissão! ", null));
            } else {

                if (titulo.getId() != null) {

                    if (titulo.getValor().doubleValue() < 0
                            && titulo.getValorPago().doubleValue() < 0) {
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                        " Valor não pode ser negativo! ", null));
                    } else {

                        if (titulo.getValorPago() == null) {
                            titulo.setSituacao(new Integer(0));
                            dao.atualizar(titulo);
                        }

                        if (titulo.getValorPago() != null
                                && titulo.getValorPago().doubleValue() < titulo.getValor().doubleValue()
                                || titulo.getValorPago() == null) {
                            titulo.setSituacao(new Integer(0));
                            dao.atualizar(titulo);
                        }

                        if (titulo.getValorPago() != null
                                && titulo.getValorPago().doubleValue() >= titulo.getValor().doubleValue()) {
                            titulo.setSituacao(new Integer(1));
                            dao.atualizar(titulo);
                        }

                        Date d = new Date();
                        titulo.setModificadoEm(d.toLocaleString());
                        dao.atualizar(titulo);
                        FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        " Título Atualizado! ", null));
                        titulo = getInstancia();
                        return "/consultar-titulo?faces-redirect=true";
                    }

                } else if (titulo.getId() == null) {
                    
                    titulo.setCriadoPor(usuarioMB.usuarioDaSessao()+
                            usuarioMB.senhaUsuarioDaSessao());
                    
                    titulo.setCriadoEm(new Date());
                    dao.adicionar(titulo); // Adiciona ao Banco

                    if (titulo.getValorPago() == null) {
                        titulo.setSituacao(new Integer(0));
                        dao.atualizar(titulo);
                    }

                    if (titulo.getValorPago() != null
                            && titulo.getValorPago().doubleValue() >= titulo.getValor().doubleValue()) {
                        titulo.setSituacao(new Integer(1));
                        dao.atualizar(titulo);
                    } else if (titulo.getValorPago() != null
                            && titulo.getValorPago().doubleValue() < titulo.getValor().doubleValue()) {
                        titulo.setSituacao(new Integer(0));
                        dao.atualizar(titulo);
                    }

                    titulos.add(titulo); // Adiciona a List<E>
                    titulo = getInstancia();
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    " Título Salvo! ", null));
                    return "/consultar-titulo?faces-redirect=true";
                }

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            " Não foi possível salvar o Título ", null));
            return "/form-novo-titulo?faces-redirect=true";
        }
        return null;
    }

    public void remover() {
        try {
            dao = new TitulosDaoImp();
            dao.remover(titulo);
            titulos.remove(titulo);

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            " Título Excluído! ", null));
            titulo = getInstancia();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            " Não foi possível exluir o Título " + getTitulo().getDescricao(), null));
            titulo = getInstancia();
        }
    }

    public void consultar() {
        titulos = getTitulos();
    }

    public void preCadastro() {
        titulos = getTitulos();
    }

    private Titulo getInstancia() {
        return new Titulo();
    }

    public void prepararCadastro() {
        this.titulos = this.getTitulos();
        if (this.titulo != null) {
            this.titulo = new Titulo();
        }
    }

    public Titulo selecionado() {
        return getTitulo();
    }

    public void clear() {
        titulo = new Titulo();
    }

    public void validaNumero(FacesContext context, UIComponent toValidate, String value) {
        boolean valida = false;
        if (value != null) {
            for (char letra : ((String) value).toCharArray()) {
                if (letra < '0' || letra > '9' || letra == '.') {
                    if (letra != '.') {
                        valida = true;
                        break;
                    }
                }
            }
            if (valida) {
                ((UIInput) toValidate).setValid(!valida);
                FacesMessage message = new FacesMessage("O número deve ser positivo!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                context.addMessage(toValidate.getClientId(context), message);
            }
        }
    }

    public String updateTitulo() {
        return "/form-novo-titulo?faces-redirect=true";
    }

    public String consultarTitulos() {
        return "/consultar-titulo?faces-redirect=true";
    }

    public double saldo() throws Exception {
        return dao.saldo();
    }

    public double saldoDevedor() throws Exception {
        return dao.saldoDevedor();
    }

    public double liquido() throws Exception {
        return saldo() - saldoDevedor();
    }

    public String meses() {
        return "2015-01-01";
    }

    public boolean desabilitarValorPago() {

        Date data = titulo.getDatadoPagamento();

        return data == null;
    }

    public boolean totalSaidas() throws Exception {
        return liquido() < 0;
    }

    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        if (value == null) {
            return;
        }

        if (value.toString() != null) {

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  // Tipo de data          

            if (this.getTitulo().getDatadeEmissao() == null) {
                titulo.setDatadeEmissao(new Date());
            }

            Date d = (Date) this.getTitulo().getDatadeEmissao();
            Date d2 = (Date) value;

            Date emissao = new Date(formatter.format(d));
            Date parametro = new Date(formatter.format(d2));

            if (emissao.after(parametro)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, " Não pode ser inferior a data de Emissão! ", null));
            }

        }

    }

    public void reucuperarUsuario() {
        System.out.println("\n\n Usuário da Sessão Atual : " + getUsuarioMB().usuarioDaSessao() + "\n\n");
    }

    public UsuarioMB getUsuarioMB() {
        return usuarioMB;
    }

    public void setUsuarioMB(UsuarioMB usuarioMB) {
        this.usuarioMB = usuarioMB;
    }

}
