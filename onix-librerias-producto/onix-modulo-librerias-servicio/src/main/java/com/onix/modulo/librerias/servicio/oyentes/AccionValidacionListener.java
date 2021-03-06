package com.onix.modulo.librerias.servicio.oyentes;

import java.io.Serializable;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;
import com.onix.modulo.librerias.exceptions.ErrorServicioNegocio;

@FunctionalInterface
public interface AccionValidacionListener<ENTIDAD extends EntidadBaseAuditable<Id>, Id extends Serializable> {

	public void validacionTransaccional(ENTIDAD entidad) throws ErrorServicioNegocio;

}
