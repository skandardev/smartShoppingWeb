package shopping.managedBeans;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import shopping.model.Category;
import shopping.model.Product;
import shopping.service.CatalogServiceLocal;



@ManagedBean
@ViewScoped
public class ProductBean {
	
	private Part file1;
	String realPath;
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
		
		try {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext();
			realPath = ctx.getRealPath("/");
			System.out.println("#############"+ctx.getContextPath());
			file1.write(realPath + "\\upload\\" + getFilename(file1));
			product.setImage("http://localhost:8383/"+ctx.getContextPath()+"/upload/" + getFilename(file1));	
		} catch (IOException e) {
		}
		product.setCategory(category);
	    catalogServiceLocal.saveOrUpdateProduct(product);
		init();
		formDisplay = false;
		return null;
	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
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

	public Part getFile1() {
		try {
			return file1;
		} catch (Exception e) {
			return null;
		}
		
	}

	public void setFile1(Part file1) {
		try {
			this.file1 = file1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	

}
