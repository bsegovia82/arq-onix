package com.onix.modulo.librerias.vista.interfaces;

public interface IGuardiaUsuarioSession 
{
	public static final String TIPO_MENU="V";
	public static final String PAGINA_PRINCIPAL_OUTCOME="/privadas/generales/pag_principal";
	public static final String PAGINA_LOGIN="/publicas/login.jsf";
	public static final String PAGINA_LOGIN_RED="/publicas/login.jsf?faces-redirect=true";
	public static final String PAGINA_PRINCIPAL="/privadas/generales/pag_principal.jsf";
	public static final String PAGINA_PRINCIPAL_RED="/privadas/generales/pag_principal.jsf?faces-redirect=true";
	public static final String PAGINA_NO_PERMITIDO="/publicas/error/access-denied.jsf?faces-redirect=true";
	public static final String SEMILLA="SEMILLA";
	
	public boolean usuarioEnSession ();	
	
	public boolean validarSemilla(String semilla);
}
