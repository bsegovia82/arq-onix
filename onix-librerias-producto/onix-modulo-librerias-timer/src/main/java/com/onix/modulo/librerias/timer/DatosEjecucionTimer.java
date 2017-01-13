package com.onix.modulo.librerias.timer;

import java.io.Serializable;

public class DatosEjecucionTimer implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long lIntervaloEjecucion;
	private String lNombreTimer;
	private Boolean lPersistente;
	
	public DatosEjecucionTimer(Long lIntervaloEjecucion, String lNombreTimer, Boolean lPersistente) {
		super();
		this.lIntervaloEjecucion = lIntervaloEjecucion;
		this.lNombreTimer = lNombreTimer;
		this.lPersistente = lPersistente;
	}

	public Long getlIntervaloEjecucion() {
		return lIntervaloEjecucion;
	}

	public String getlNombreTimer() {
		return lNombreTimer;
	}

	public Boolean getlPersistente() {
		return lPersistente;
	}
	
	
}
