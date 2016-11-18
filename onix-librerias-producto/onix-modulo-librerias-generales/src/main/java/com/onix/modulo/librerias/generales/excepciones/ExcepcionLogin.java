package com.onix.modulo.librerias.generales.excepciones;


public class ExcepcionLogin extends ExcepcionAplicacion{

	private static final long serialVersionUID = -2178031421135350494L;

	public ExcepcionLogin(String codigoExcepcion, String mensajeUsuario,
			String mensajeTecnico) {
		super(codigoExcepcion, mensajeUsuario, mensajeTecnico);
	}

	public ExcepcionLogin(String codigoExcepcion, String mensajeUsuario) {
		super(codigoExcepcion, mensajeUsuario);
	}
}
