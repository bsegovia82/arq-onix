package com.onix.modulo.librerias.dominio.entidades.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Byron Segovia
 * @version 1.0
 * 
 * <p>Si se necesita crear una entidad con campos de auditoria, debemos extender de esta clase
 * 
 * */

@MappedSuperclass
public abstract class EntidadBaseAuditable<Id extends Serializable> 
extends EntidadBase<Id> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected String observacion;
	protected Date fechaRegistro;
	protected Date fechaActualizacion;
	protected String referenciaIngreso;
	
	@Column(name="OBSERVACION")
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_REGISTRO")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_ACTUALIZACION")
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Column(name="CAMPO_AUDITORIA")
	public String getAuditoria() {
		return referenciaIngreso;
	}

	public void setAuditoria(String auditoria) {
		this.referenciaIngreso = auditoria;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EntidadBaseAuditable)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		EntidadBaseAuditable<Id> other = (EntidadBaseAuditable<Id>) obj;
		if (this.getId() == null) {
			return false;
		} else if (!this.getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
