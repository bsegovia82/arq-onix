package com.onix.modulo.librerias.vista.beans.oyentes;

import com.onix.modulo.librerias.vista.exceptions.ErrorAccionesPreTransaccion;

@FunctionalInterface
public interface PreTransaccionListener 
{

	public void accionPreTransaccion() throws ErrorAccionesPreTransaccion;
	
}
