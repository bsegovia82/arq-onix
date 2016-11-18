package com.onix.modulo.librerias.seguridad.loginmodule;

import java.security.acl.Group;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import com.onix.modulo.librerias.generales.excepciones.ExcepcionLogin;
import com.onix.modulo.librerias.seguridad.loginmodule.servicios.IServiciosLoginModule;
import com.onix.modulo.librerias.seguridad.loginmodule.servicios.jdbc.ServicioLoginModuleJDBC;
  


public class LoginModuleWildFly extends UsernamePasswordLoginModule
{
	protected ServicioLoginModuleJDBC serviciosLoginModuleJDBC = new ServicioLoginModuleJDBC();
	
	
	public IServiciosLoginModule getServiciosLoginModule() {
		return serviciosLoginModuleJDBC;
	}
	
	protected String getUsersPassword() throws LoginException {
		//Aqui debo sacar el usuario del REALM ldap, dataource archivos o lo que sea
		return getServiciosLoginModule().getClaveUsuario(getUsername());
	}
	
	protected Group[] getRoleSets() throws LoginException {
		//sacar los roles del REALM, ldap, datasource archivos o lo que sea
		List<String> lNombresRol=getServiciosLoginModule().getNombreRoles(getUsername());
		if(lNombresRol!=null)
		{
			Group[] groups = {new SimpleGroup("Roles")};
			for(String lRol:lNombresRol){
				SimplePrincipal role = new SimplePrincipal(lRol);
				groups[0].addMember(role);
			}
			return groups;
		}
		else
			return null;
	}
	protected void guadarMensajeErrorRequest(String pMensajaErrorAutenticacion){
		try {
			HttpServletRequest request = (HttpServletRequest) PolicyContext.getContext("javax.servlet.http.HttpServletRequest");
			request.setAttribute("mensajaErrorAutenticacion", pMensajaErrorAutenticacion);
		} catch (PolicyContextException e) {
			e.printStackTrace();
		}   
	}
	
	protected void guadarMensajeErrorRequest(ExcepcionLogin pExcepcionLogin){
		guadarMensajeErrorRequest(pExcepcionLogin.getMensajeUsuario());
	}
	
	protected boolean validatePassword(String inputPassword,
			String expectedPassword) {
		try {
			boolean lbClaveValida=getServiciosLoginModule().validarUsuarioPassword(getUsername(), inputPassword);
			//....
			
			if(!lbClaveValida)
				guadarMensajeErrorRequest("Credenciales invalidas");
			return lbClaveValida;
		} catch (ExcepcionLogin e) {
			guadarMensajeErrorRequest(e);
			return false;
		}
	}

}
