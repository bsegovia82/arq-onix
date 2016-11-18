package com.onix.modulo.librerias.seguridad.loginmodule.servicios;

import java.util.List;

import com.onix.modulo.librerias.generales.excepciones.ExcepcionLogin;

public interface IServiciosLoginModule 
{
	String getClaveUsuario(String pUsuario);
	List<String> getNombreRoles(String pUsuario);
	boolean validarUsuarioPassword(String pUsuario,String pPassword) throws ExcepcionLogin;
	
}   
   