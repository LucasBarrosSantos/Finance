package Convert;

import Dao.EntidadesDaoImp;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import Model.Entidade;


@FacesConverter("EntidadeConverter")
public class EntidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !"".equals(value) && !(value.isEmpty())) {
            try {
                EntidadesDaoImp daoImp = new EntidadesDaoImp();
                Entidade entidade = new Entidade();

                entidade = daoImp.pesquisarId(Integer.parseInt(value));

                return entidade;
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
            return String.valueOf(((Entidade) object).getId());
        }
        return null;

    }
}
