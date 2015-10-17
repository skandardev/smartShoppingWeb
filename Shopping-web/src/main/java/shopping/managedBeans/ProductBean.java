package shopping.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import shopping.model.Category;
import shopping.model.Product;
import shopping.service.CatalogServiceLocal;



@ManagedBean
@ViewScoped
public class ProductBean {

	private Category category = new Category();
	private Product product = new Product();

	@EJB
	private CatalogServiceLocal catalogServiceLocal;

	private List<Category> categories;
	private List<Product> produits;

	private boolean formDisplay = false;

	@PostConstruct
	public void init() {
		categories = catalogServiceLocal.findAllCategories();
		produits = catalogServiceLocal.findAllProducts();
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
		product.setCategory(category);
		catalogServiceLocal.saveOrUpdateProduct(product);
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
		product = new Product();
		return null;
	}

	public String doCancel() {
		formDisplay = false;
		return null;
	}

	public String doDelete() {
		catalogServiceLocal.removeProduct(product);
		init();
		formDisplay = false;
		return null;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProduits() {
		return produits;
	}

	public void setProduits(List<Product> produits) {
		this.produits = produits;
	}

}
