package com.onix.modulo.librerias.generales.excepciones;

import java.util.HashMap;
import java.util.Map;



public class ExcepcionAplicacion extends Exception {

	private static final long serialVersionUID = 1719127745756254092L;
	private String codigoExcepcion;
	private String mensajeUsuario;
	private String mensajeTecnico;
	private Map<String,Object> datosError=new HashMap<String, Object>();
	
	public ExcepcionAplicacion(Throwable cause)
	{
		super(cause);
	}
	public ExcepcionAplicacion(String codigoExcepcion, String mensajeUsuario) {
		super();
		this.codigoExcepcion = codigoExcepcion;
		this.mensajeUsuario = mensajeUsuario;
	}
	public ExcepcionAplicacion(String codigoExcepcion, String mensajeUsuario,Map<String,Object> datosError) {
		this(codigoExcepcion,mensajeUsuario);
		this.datosError=datosError;		
	}
	public ExcepcionAplicacion(String codigoExcepcion, String mensajeUsuario,
			String mensajeTecnico) {
		super();
		this.codigoExcepcion = codigoExcepcion;
		this.mensajeUsuario = mensajeUsuario;
		this.mensajeTecnico = mensajeTecnico;
	}
	public ExcepcionAplicacion(String codigoExcepcion, String mensajeUsuario,
			String mensajeTecnico,Map<String,Object> datosError) {
		this(codigoExcepcion,mensajeUsuario,mensajeTecnico);
		this.datosError=datosError;
	}
	public ExcepcionAplicacion(String codigoExcepcion, String mensajeUsuario,
			String mensajeTecnico,Throwable cause) {
		super(cause);
		this.codigoExcepcion = codigoExcepcion;
		this.mensajeUsuario = mensajeUsuario;
		this.mensajeTecnico = mensajeTecnico;
	}
	public String getCodigoExcepcion() {
		return codigoExcepcion;
	}
	
	public String getMensajeUsuario() {
		return mensajeUsuario;
	}
	protected void setMensajeUsuario(String pMensajeUsuario){
		mensajeUsuario=pMensajeUsuario;
	}
	
	public String getMensajeTecnico() {
		return mensajeTecnico;
	}
	protected void setMensajeTecnico(String pMensajeTecnico) {
		mensajeTecnico=pMensajeTecnico;
	}
	
	public Map<String, Object> getDatosError() {
		return datosError;
	}
	protected void serDatosError(Map<String, Object> pDatosError){
		datosError=pDatosError;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(" [codigoExcepcion=");
		builder.append(codigoExcepcion);
		builder.append(", mensajeUsuario=");
		builder.append(mensajeUsuario);
		builder.append(", mensajeTecnico=");
		builder.append(mensajeTecnico);
		builder.append("]");
		return builder.toString();
	}
	@Override
	public String getMessage() {
		return getMensajeUsuario();
	}

}