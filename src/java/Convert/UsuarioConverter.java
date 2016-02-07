package Convert;

import Dao.UsuarioDaoImp;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import Model.Usuario;


@FacesConverter("UsuarioConverter")
public class UsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !"".equals(value) && !(value.isEmpty())) {
            try {
                //ThemeService service = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
                UsuarioDaoImp daoImp = new UsuarioDaoImp();
                Usuario usuario = new Usuario();

                usuario = daoImp.pesquisarId(Integer.parseInt(value));

                return usuario;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Conversão", "O Valor não é válido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null && !"".equals(object)) {
            return String.valueOf(((Usuario) object).getId());
        }
        return null;

    }
}
