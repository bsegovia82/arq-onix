package com.onix.modulo.librerias.dominio.entidades.generales;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;


/**
 * The persistent class for the GEN_TIPOS_QUERYS database table.
 * 
 */
@Entity
@Table(name="GEN_TIPOS_QUERYS")
@NamedQuery(name="GenTiposQuery.findAll", query="SELECT g FROM GenTiposQuery g")
public class GenTiposQuery 
extends EntidadBaseAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descripcion;
	private String nombre;
	private List<GenQuery> genQuerys;

	public GenTiposQuery() {
	}

	@GeneratedValue(generator="secGenTipoQuery", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name="secGenTipoQuery", allocationSize=1, initialValue=1, sequenceName="SEC_TIPO_GENQUERY")
	@Id
	@Column(name="ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="DESCRIPCION")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name="NOMBRE")
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(mappedBy="genTiposQuery")
	public List<GenQuery> getGenQuerys() {
		return this.genQuerys;
	}

	public void setGenQuerys(List<GenQuery> genQuerys) {
		this.genQuerys = genQuerys;
	}
}