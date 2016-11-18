package com.onix.modulo.librerias.generales.excepciones;

public class ExcepcionParametrosQuerys extends ExcepcionFalloTecnico {

	private static final long serialVersionUID = 8778858125060358313L;

	public ExcepcionParametrosQuerys(Throwable cause)
	{
		super(cause);
	}
	public ExcepcionParametrosQuerys(String codigoExcepcion, String mensajeUsuario) {
		super(codigoExcepcion, mensajeUsuario);
	}
	public ExcepcionParametrosQuerys(String codigoExcepcion, String mensajeUsuario,
			String mensajeTecnico) {
		super(codigoExcepcion, mensajeUsuario, mensajeTecnico);
	}
	public ExcepcionParametrosQuerys(String codigoExcepcion, String mensajeUsuario,
			String mensajeTecnico,Throwable cause) {
		super(codigoExcepcion, mensajeUsuario, mensajeTecnico, cause);
	}
}
