package TestesAll;

import ConexaoPU.PersistenceManager;
import Dao.EventosDaoImp;
import Dao.TitulosDaoImp;
import Dao.UsuarioDaoImp;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import Model.Evento;
import Model.Titulo;
import Model.Usuario;

/**
 * @author lucas
 */
public class TestConexao {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        EntityManager em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        
        TitulosDaoImp dao = new TitulosDaoImp();
        
        
        
        em.getTransaction().commit();
        em.close();
    }

    public static <T> Object getObjectSession(String attribute) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession(true);
        return session.getAttribute(attribute);
    }
}
