package com.onix.modulo.librerias.generales.excepciones;

public class ExcepcionEliminacionNoPermitida extends ExcepcionAplicacion {

	private static final long serialVersionUID = 7851256895777422729L;

	private final static String CODIGO_ERROR_ELIMINACION_NO_PERMITIDA="eliminacion";
	
	public ExcepcionEliminacionNoPermitida(String mensajeUsuario) {
		super(CODIGO_ERROR_ELIMINACION_NO_PERMITIDA, mensajeUsuario);
	}

	public ExcepcionEliminacionNoPermitida(String codigoExcepcion,
			String mensajeUsuario, String mensajeTecnico) {
		super(CODIGO_ERROR_ELIMINACION_NO_PERMITIDA, mensajeUsuario, codigoExcepcion + mensajeTecnico);
	}

}
