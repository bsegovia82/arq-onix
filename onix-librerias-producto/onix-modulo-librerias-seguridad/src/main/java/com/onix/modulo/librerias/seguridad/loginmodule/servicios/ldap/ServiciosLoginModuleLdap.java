package com.onix.modulo.librerias.seguridad.loginmodule.servicios.ldap;

import java.util.ArrayList;
import java.util.List;

import com.onix.modulo.librerias.generales.excepciones.ExcepcionLogin;

//Por ahora no se usara
public class ServiciosLoginModuleLdap{
   
	public String getClaveUsuario(String pUsuario) 
	{
		String clave = null;   
//		try {
//			clave = LdapServicios.getClaveUsuario(pUsuario);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (LDAPException e) {
//			e.printStackTrace();
//		}
		return clave;
	}

	public List<String> getNombreRoles(String pUsuario) {
		// Por ahora solo tendran un rol, que es el de conexion
		List<String> listaRoles = new ArrayList<String>();
		listaRoles.add("CONEXION");
		return listaRoles;
	}

	public boolean validarUsuarioPassword(String pUsuario, String pPassword)
			throws ExcepcionLogin {
//		try {
//			return LdapServicios.validarCredenciales(pUsuario, pPassword);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (LDAPException e) {
//			e.printStackTrace();
//		}
		return false;
	}

}
