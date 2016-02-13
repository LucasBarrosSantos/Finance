package Controller;

import Dao.ContatosDaoImp;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.InternetAddress;
import Model.Contato;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

@ManagedBean
@SessionScoped
public class ContatoMB {

    private Contato contato;
    private List<Contato> contatos;
    private ContatosDaoImp dao;

    public ContatoMB() {
        contato = new Contato();
        contatos = new ArrayList();
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public List<Contato> getContatos() {
        if (contato != null) {
            dao = new ContatosDaoImp();
            contatos = dao.listarTodos();
        }
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public void preCadastro() {
        contatos = getContatos();
    }

    public void sendEmail() throws EmailException, UnsupportedEncodingException {

        try {
            SimpleEmail email = new SimpleEmail();
            //Utilize o hostname do seu provedor de email
            email.setHostName("smtp.gmail.com");
            //Quando a porta utilizada não é a padrão (gmail = 465)
            email.setSmtpPort(465);
            //Adicione os destinatários
            email.addTo("nomedominio@hotmail.com", "Lucas Barros");
            email.addCc(contato.getEmail());
            //Configure o seu email do qual enviará
            email.setFrom(new InternetAddress(contato.getEmail(), contato.getNome()).toString());
            //Adicione um assunto
            email.setSubject(contato.getAssunto());
            //Adicione a mensagem do email
             if (!contato.getTelefone().isEmpty()) {
                email.setMsg(contato.getMensagem()
                        + "\n\n Telefone para contato " + contato.getTelefone());
            } else {
                email.setMsg(contato.getMensagem());
            }
            //Para autenticar no servidor é necessário chamar os dois métodos abaixo
            email.setSSL(true);
            email.setAuthentication("nomedominio@gmail.com", "senha");
            email.send();
            System.out.println("Email enviado!");

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            " Email enviado! ", null));

            contato = new Contato();

        } catch (EmailException | UnsupportedEncodingException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            " Não foi possível enviar o E-mail! ", null));
        }

    }

}
