package com.onix.modulo.librerias.generales.excepciones;

public class ExcepcionFalloTecnico extends RuntimeException {
	private static final long serialVersionUID = 1719127745756254092L;
	private String codigoExcepcion;
	private String mensajeUsuario;
	private String mensajeTecnico;
	
	public ExcepcionFalloTecnico(Throwable cause)
	{
		super(cause);
	}
	public ExcepcionFalloTecnico(String codigoExcepcion, String mensajeUsuario) {
		super();
		this.codigoExcepcion = codigoExcepcion;
		this.mensajeUsuario = mensajeUsuario;
	}
	public ExcepcionFalloTecnico(String codigoExcepcion, String mensajeUsuario,
			String mensajeTecnico) {
		super();
		this.codigoExcepcion = codigoExcepcion;
		this.mensajeUsuario = mensajeUsuario;
		this.mensajeTecnico = mensajeTecnico;
	}
	public ExcepcionFalloTecnico(String codigoExcepcion, String mensajeUsuario,
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
	public String getMensajeTecnico() {
		return mensajeTecnico;
	}
}