package com.onix.modulo.librerias.vista.interfaces;

import java.io.Serializable;

public interface IServiciosAutenticacion <T extends Serializable, F extends Serializable> 
{
	public T autenticar (F datosAutenticar) throws Exception;
	public boolean autenticarDominio (F datosAutenticar);
}
