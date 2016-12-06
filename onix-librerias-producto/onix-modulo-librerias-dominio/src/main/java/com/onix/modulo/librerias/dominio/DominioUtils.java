package com.onix.modulo.librerias.dominio;


/**
 * @author Byron Segovia
 * @version 1.0
 * 
 * <p>Clase que encapsula métodos necesarios y básicos para el dominio en cualquier base 
 * 
 * */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;

public class DominioUtils {

	public static <E extends EntidadBaseAuditable<Id>, Id extends Serializable> Collection<E> getElementosSinId(
			Collection<E> entidadades) {
		Collection<E> lElementosSinId = new ArrayList<E>();
		for (E lEntidad : entidadades) {
			if (lEntidad.getId() == null)
				lElementosSinId.add(lEntidad);
		}
		return lElementosSinId;
	}

	public static <E extends EntidadBaseAuditable<Id>, Id extends Serializable> Collection<E> getElementosConId(
			Collection<E> entidadades) {
		Collection<E> lElementosConId = new ArrayList<E>();
		for (E lEntidad : entidadades) {
			if (lEntidad.getId() != null)
				lElementosConId.add(lEntidad);
		}
		return lElementosConId;
	}

	public static <E extends EntidadBaseAuditable<Id>, Id extends Serializable> void limpiarId(
			E entidad) {
		entidad.setId(null);
	}

	public static <E extends EntidadBaseAuditable<Id>, Id extends Serializable> void limpiarIdCollection(
			Collection<E> entidades) {
		for (E lEntidad : entidades)
			limpiarId(lEntidad);
	}

}
