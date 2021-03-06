package com.onix.microservicios.api.vista;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.webapp.FacesServlet;
import javax.inject.Inject;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.onix.microservicios.api.vista.scope.ViewScope;
import com.sun.faces.config.ConfigureListener;
import com.sun.faces.config.FacesInitializer;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class BaseAppJsf {

	@Inject
	private IParametroInicializacion lParametroInicializacion;

	@Bean
	public static ViewScope viewScope() {
		return new ViewScope();
	}	
	
	
	
	@Bean
	public static CustomScopeConfigurer scopeConfigurer() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("view", viewScope());
		configurer.setScopes(hashMap);
		return configurer;
	}

	@Bean
	public FacesServlet facesServlet() {
		return new FacesServlet();
	}

	public class JsfServletRegistrationBean extends ServletRegistrationBean {

		public JsfServletRegistrationBean(Servlet arg0, String arg1, String arg2) {
			super(arg0, arg1, arg2);
		}

		public JsfServletRegistrationBean() {
			super();
		}

		@Override
		public void onStartup(ServletContext servletContext) throws ServletException {
			FacesInitializer facesInitializer = new FacesInitializer();
			Set<Class<?>> clazz = new HashSet<Class<?>>();
			clazz.add(BaseAppJsf.class);
			facesInitializer.onStartup(clazz, servletContext);
		}
	}

	@Bean
	public ServletRegistrationBean facesServletRegistration() {
		ServletRegistrationBean registration = new JsfServletRegistrationBean(facesServlet(), "*.jsf", "*.xhtml");
		registration.setName("FacesServlet");
		registration.addUrlMappings("*.jsf");
		registration.addUrlMappings("*.xhtml");
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}
	
	

	@Bean
	public ServletContextInitializer initializer() {
		return new ServletContextInitializer() {

			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {

				HashMap<String, String> lParametros = lParametroInicializacion.getParametrosInicializacionJSF();
				for (Entry<String, String> lRegistro : lParametros.entrySet()) {
					String lNombreParametro = lRegistro.getKey();
					String lValorParametro = lRegistro.getValue();
					servletContext.setInitParameter(lNombreParametro, lValorParametro);
				}

			}
		};
	}
}
