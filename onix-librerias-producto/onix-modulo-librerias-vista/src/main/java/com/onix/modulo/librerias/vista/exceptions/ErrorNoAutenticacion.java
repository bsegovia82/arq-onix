package com.onix.modulo.librerias.vista.exceptions;




public class ErrorNoAutenticacion 
extends Exception {

	private static final long serialVersionUID = 1L;

	private String mensajeUsuario;
	
	/**
	 * Constructor por defecto
	 */
	public ErrorNoAutenticacion(String mensajeUsuario) {
		super(mensajeUsuario);
		this.mensajeUsuario = mensajeUsuario;
	}


	/**
	 * Constructor que construye una excepcion a partir de un mensaje de error y la causa
	 * 
	 * @param msg
	 *            : mensaje de error
	 */
	public ErrorNoAutenticacion(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Constructor que construye una excepcion a partir de otra excepcion
	 * 
	 * @param throwable
	 *            : excepcion
	 */
	public ErrorNoAutenticacion(Throwable throwable) {
		super(throwable);
	}
	
	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

}
