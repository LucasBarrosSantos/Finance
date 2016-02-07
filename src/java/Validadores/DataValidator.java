/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validadores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

@FacesValidator("custom.dataValidator")
public class DataValidator implements Validator, ClientValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        if (value == null) {
            return;
        }

        if (value.toString() != null) {
            
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  // Tipo de data          
            Date d = new Date();
            Date d2 = (Date) value;
            
            Date emissao = new Date(formatter.format(d));
            Date parametro = new Date(formatter.format(d2));
            
            if (emissao.after(parametro)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, " Não pode ser inferior a data de Emissão! ",
                        value + " Este não é um e-mail válido"));
            }

        }

    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "custom.dataValidator";
    }

}
