package com.onix.modulo.librerias.dominio.entidades.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.onix.modulo.librerias.dominio.entidades.IEntidadPersistible;


/**
 * @author Byron Segovia
 * @version 1.0
 * 
 * <p>Si se necesita crear una entidad b�sica sin campos de auditoria, se usa esta clase 
 * 
 * */
@MappedSuperclass
public abstract class EntidadBase<Id extends Serializable> implements IEntidadPersistible<Id>, Serializable {
	private static final long serialVersionUID = 1L;

	protected Id id;

	protected String estado;

	@Column(name = "ESTADO", length = 1)
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (getId() != null ? getId().hashCode() : 0);
		return hash;
	}

	

}
