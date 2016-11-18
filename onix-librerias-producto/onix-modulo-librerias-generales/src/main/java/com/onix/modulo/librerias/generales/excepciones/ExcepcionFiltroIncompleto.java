package com.onix.modulo.librerias.generales.excepciones;

public class ExcepcionFiltroIncompleto extends ExcepcionAplicacion {

	private static final long serialVersionUID = 2157244292508975598L;

	private final static String CODIGO_ERROR_FILTRO_INCOMPLETO="filtro";
	
	public ExcepcionFiltroIncompleto(String mensajeUsuario, String mensajeTecnico) {
		super(CODIGO_ERROR_FILTRO_INCOMPLETO, mensajeUsuario, mensajeTecnico);
	}

	public ExcepcionFiltroIncompleto(String mensajeUsuario) {
		super(CODIGO_ERROR_FILTRO_INCOMPLETO, mensajeUsuario);
	}
	
}
