package br.org.venturus.tambor.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet Filter implementation class AppFilter
 */
@WebFilter("/AppFilter")
public class CoreFilter implements Serializable, Filter {
   
	private static final long serialVersionUID = 1L;

	/**
     * Default constructor. 
     */
    public CoreFilter() {
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
		
		Boolean isloggedIn = (Boolean) ((HttpServletRequest) request).getSession(true).getAttribute("isLoggedIn");
		
		String contextPath = ((HttpServletRequest) request).getContextPath();
		
		if (isloggedIn == null) {	
			//Redirecionamos o usuário imediatamente para a página de logout.xhtml            
			((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml"); 
        } else if (!isloggedIn) {
        	((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");         
		} else {
			chain.doFilter(request, response);
		}//fim do if-else		
		
	}//fim do método

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
