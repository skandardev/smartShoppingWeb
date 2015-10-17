package shopping.filtre;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.managedBeans.AuthentificationBean;


/**
 * Servlet Filter implementation class AdminFiltre
 */
@WebFilter("/pages/admin/*")
public class AdminFiltre implements Filter {

    /**
     * Default constructor. 
     */
    public AdminFiltre() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			AuthentificationBean authB = (AuthentificationBean) req
					.getSession().getAttribute("authBean");
			if (authB != null && authB.isLoggedIn() && authB.getUserType().equals("admin")) {
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect(req.getContextPath() + "/pages/login.jsf");
			}
		} catch (NullPointerException e) {
			resp.sendRedirect(req.getContextPath() + "/pages/login.jsf");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
