package shopping.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import shopping.model.Category;
import shopping.service.CatalogServiceLocal;



@ManagedBean
@ViewScoped
public class CategoriyBean {
	private Category category = new Category();
	@EJB
	private CatalogServiceLocal catalogServiceLocal;
	private List<Category> categories;
	private boolean formDisplay = false;

	@PostConstruct
	public void init() {
		categories = catalogServiceLocal.findAllCategories();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String doSave() {
		catalogServiceLocal.saveOrUpdateCategory(category);
		init();
		formDisplay = false;
		return null;
	}

	public boolean isFormDisplay() {
		return formDisplay;
	}

	public void setFormDisplay(boolean formDisplay) {
		this.formDisplay = formDisplay;
	}

	public String doNew() {
		formDisplay = true;
		category = new Category();
		return null;
	}

	public String doCancel() {
		formDisplay = false;
		return null;
	}

	public String doDelete() {
		catalogServiceLocal.removeCategory(category);
		init();
		formDisplay = false;
		return null;
	}
}
