package shopping.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import shopping.model.Category;
import shopping.service.CatalogServiceLocal;



//@FacesConverter("cc")
@ManagedBean
public class CategoryConverter implements Converter {

	@EJB
	private CatalogServiceLocal catalogServiceLocal;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return catalogServiceLocal.findCategoryByName(value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((Category) arg2).getName();
	}

}
