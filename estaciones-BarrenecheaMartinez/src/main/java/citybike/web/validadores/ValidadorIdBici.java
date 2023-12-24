package citybike.web.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.mysql.cj.x.protobuf.Mysqlx.Error.Severity;

import estaciones.servicio.IServicioEstaciones;
import estaciones.servicio.ServicioEstaciones;
import servicio.FactoriaServicios;

@FacesValidator(value = "biciIdValidador",managed = true)
public class ValidadorIdBici implements Validator {
	
	private IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try {
			servicioEstaciones.recuperarBici((String)value);
		}
		catch(Exception e) {
			FacesMessage msg= new FacesMessage("La bici con código " + value+" no existe");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}

}
