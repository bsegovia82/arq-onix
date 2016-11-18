package com.onix.modulo.librerias.generales.excepciones;

import javax.naming.NamingException;



public class ExcepcionLocalizacionServicio extends ExcepcionFalloTecnico {

	private static final long serialVersionUID = 105161927679073577L;
	
	private final static String CODIGO_ERROR_LOCALIZACION_SERVICIO="servicio";
	
	public ExcepcionLocalizacionServicio(NamingException pCausa) {
		super(CODIGO_ERROR_LOCALIZACION_SERVICIO, 
				"Error Tecnico de Localizacion", 
				pCausa.getMessage(),pCausa);
	}

}
