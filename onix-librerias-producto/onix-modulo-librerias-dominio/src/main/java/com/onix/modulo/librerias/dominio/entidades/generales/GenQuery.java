package com.onix.modulo.librerias.dominio.entidades.generales;

/**
 * @author Byron Segovia
 * @version 1.0
 * 
 * <p>Entidad que sirve para crear una tabla en base de datos sirve para oracle y posgresql
 * 
 * 
 * */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;


@Entity
@Table(name = "GEN_QUERYS")
@NamedQuery(name = "GenQuery.findAll", query = "SELECT g FROM GenQuery g")
public class GenQuery 
extends EntidadBaseAuditable<Long> {

	private static final long serialVersionUID = 1L;

	private String descripcion;
	private String query;
	private GenTiposQuery genTiposQuery;

	public GenQuery() {
	}

	@GeneratedValue(generator = "secGenQuery", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "secGenQuery", allocationSize = 1, initialValue = 1, sequenceName = "SEC_GENQUERY")
	@Id
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DESCRIPCION")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Lob
	@Column(name = "QUERY")
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@ManyToOne
	@JoinColumn(name = "ID_TIPO")
	public GenTiposQuery getGenTiposQuery() {
		return this.genTiposQuery;
	}

	public void setGenTiposQuery(GenTiposQuery genTiposQuery) {
		this.genTiposQuery = genTiposQuery;
	}

}