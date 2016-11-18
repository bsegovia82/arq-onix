package com.onix.modulo.librerias.exceptions;

import javax.ejb.ApplicationException;


@ApplicationException(rollback = false)
public class ErrorValidacionGeneral 
extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto
	 */
	public ErrorValidacionGeneral() {
		super();
	}

	/**
	 * Constructor que construye una excepcion a partir de un mensaje de error
	 * 
	 * @param msg
	 *            : mensaje de error
	 */
	public ErrorValidacionGeneral(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor que construye una excepcion a partir de un mensaje de error y la causa
	 * 
	 * @param msg
	 *            : mensaje de error
	 */
	public ErrorValidacionGeneral(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Constructor que construye una excepcion a partir de otra excepcion
	 * 
	 * @param throwable
	 *            : excepcion
	 */
	public ErrorValidacionGeneral(Throwable throwable) {
		super(throwable);
	}

}
