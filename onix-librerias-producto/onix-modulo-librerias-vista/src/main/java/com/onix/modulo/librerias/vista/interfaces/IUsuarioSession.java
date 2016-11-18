package com.onix.modulo.librerias.vista.interfaces;

import java.io.Serializable;


public interface IUsuarioSession<T extends Serializable>
{

	public void setUsuarioSession(T usuario);
	
	public T getUsuarioSession();
	
}
