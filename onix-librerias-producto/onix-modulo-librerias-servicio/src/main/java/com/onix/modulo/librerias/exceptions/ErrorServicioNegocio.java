package com.onix.modulo.librerias.exceptions;

import javax.ejb.ApplicationException;


@ApplicationException(rollback = true)
public class ErrorServicioNegocio 
extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto
	 */
	public ErrorServicioNegocio() {
		super();
	}

	/**
	 * Constructor que construye una excepcion a partir de un mensaje de error
	 * 
	 * @param msg
	 *            : mensaje de error
	 */
	public ErrorServicioNegocio(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor que construye una excepcion a partir de un mensaje de error y la causa
	 * 
	 * @param msg
	 *            : mensaje de error
	 */
	public ErrorServicioNegocio(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Constructor que construye una excepcion a partir de otra excepcion
	 * 
	 * @param throwable
	 *            : excepcion
	 */
	public ErrorServicioNegocio(Throwable throwable) {
		super(throwable);
	}

}
