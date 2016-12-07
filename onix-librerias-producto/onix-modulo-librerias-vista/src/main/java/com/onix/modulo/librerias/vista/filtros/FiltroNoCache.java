package com.onix.modulo.librerias.vista.filtros;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(dispatcherTypes = { DispatcherType.ERROR, DispatcherType.REQUEST }, urlPatterns = { "/*" }) // Must match <servlet-name> of your FacesServlet.
public class FiltroNoCache implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
        	System.out.println("***************************************************");
    		System.out.println("LIMPIEZA DE CACHE");
    		System.out.println("***************************************************");
        	res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            res.setDateHeader("Expires", 0); // Proxies.
        }
        else
        {
        	System.out.println("***************************************************");
    		System.out.println("NO SE REALIZA LA LIMPIEZA POR QUE ES UN RECURSO DE JSF");
    		System.out.println("***************************************************");
        }
       
        chain.doFilter(request, response);
    }

	public void destroy() {
		System.out.println("Destruccion");
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Destruccion");
	}

    
}