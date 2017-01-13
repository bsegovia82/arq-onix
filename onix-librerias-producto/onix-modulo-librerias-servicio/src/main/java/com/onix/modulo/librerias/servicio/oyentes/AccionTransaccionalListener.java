package com.onix.modulo.librerias.servicio.oyentes;

import java.io.Serializable;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;
import com.onix.modulo.librerias.exceptions.ErrorServicioNegocio;
import com.onix.modulo.librerias.exceptions.ErrorValidacionGeneral;

@FunctionalInterface
public interface AccionTransaccionalListener<ENTIDAD extends EntidadBaseAuditable<Id>, Id extends Serializable> {

	public void controlTransaccional(ENTIDAD entidad) throws ErrorServicioNegocio, ErrorValidacionGeneral;
}
