package com.onix.modulo.librerias.vista.beans.oyentes;

import com.onix.modulo.librerias.vista.exceptions.ErrorValidacionVisual;

@FunctionalInterface
public interface ValidadorIngresoDatosListener 
{
	public void validarDatosIngreso() throws ErrorValidacionVisual;
}
