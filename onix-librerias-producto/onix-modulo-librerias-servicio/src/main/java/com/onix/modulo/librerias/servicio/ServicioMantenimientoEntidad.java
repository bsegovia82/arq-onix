package com.onix.modulo.librerias.servicio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;
import com.onix.modulo.librerias.eao.GenericEAO;
import com.onix.modulo.librerias.exceptions.ErrorServicioNegocio;
import com.onix.modulo.librerias.exceptions.ErrorValidacionGeneral;
public abstract class ServicioMantenimientoEntidad
<EAO extends GenericEAO<ENTIDAD, Id>, ENTIDAD extends EntidadBaseAuditable<Id>, Id extends Serializable> 
{
	protected static final String OBSERVACION_APLICACION = "SIN DESCRIPCION";
	protected static final String REFERENCIA = "SIN USUARIO RESPONSABLE";
	protected static final String ES_NUEVO = "S";
	protected abstract EAO getCrud();

	public void guardarActualizar(ENTIDAD entidad) throws ErrorServicioNegocio, ErrorValidacionGeneral {
		if (entidad.getId() == null) {
			preLlenadoRegistro(entidad);
			validacionesBasicasGuardar(entidad);
			validacionesConBaseGuardar(entidad);
		}
		else
		{
			preLlenadoActualizacion(entidad);
			validacionesBasicasActualizar(entidad);
			validacionesConBaseActualizar(entidad);
		}
		try {
			if (entidad.getId() == null) {
				//metodoprevio a guadardar dentro de la misma transaccion
				//O algun seteo previo de campos por ejemplo
				prePersistencia(entidad);
				getCrud().insertar(entidad);
				postPersistencia(entidad);
			} else {
				preActualizacion(entidad);
				getCrud().actualizar(entidad);
				postActualizacion(entidad);
			}

		} catch (Throwable e) {
			e.printStackTrace();
			throw new ErrorServicioNegocio(e);
		}
	}

	private void preLlenadoRegistro(ENTIDAD entidad) {
		entidad.setEstado(GenericEAO.ESTADO_ACTIVO);
		entidad.setFechaRegistro(new Date());
		entidad.setObservacion(entidad.getObservacion() == null || entidad.getObservacion().length() < 2 ? OBSERVACION_APLICACION
				: entidad.getObservacion());
		entidad.setAuditoria(entidad.getAuditoria() == null ? REFERENCIA : entidad.getAuditoria());
	}

	private void preLlenadoActualizacion(ENTIDAD entidad)
	{
		entidad.setFechaActualizacion(new Date());
		entidad.setObservacion(entidad.getObservacion() == null || entidad.getObservacion().length() < 2 ? OBSERVACION_APLICACION
				: entidad.getObservacion());
		entidad.setAuditoria(entidad.getAuditoria() == null || entidad.getAuditoria().length() < 2 ? REFERENCIA
				: entidad.getAuditoria());
	}
	
	protected  void postActualizacion(ENTIDAD entidad)throws ErrorServicioNegocio, ErrorValidacionGeneral 
	{
		System.out.println("Sin postActualizacion " + entidad.getClass().getName());
	}
	protected  void postPersistencia(ENTIDAD entidad) throws ErrorServicioNegocio, ErrorValidacionGeneral
	{
		System.out.println("Sin postPersistencia " + entidad.getClass().getName());
	}

	protected  void preActualizacion(ENTIDAD entidad) throws ErrorServicioNegocio, ErrorValidacionGeneral
	{
		System.out.println("Sin preActualizacion " + entidad.getClass().getName());
	}
	protected  void prePersistencia(ENTIDAD entidad)throws ErrorServicioNegocio, ErrorValidacionGeneral
	{
		System.out.println("Sin prePersistencia " + entidad.getClass().getName());
	}

	protected abstract void validacionesConBaseActualizar(ENTIDAD entidad)throws ErrorServicioNegocio;

	protected  void validacionesBasicasActualizar(ENTIDAD entidad) throws ErrorValidacionGeneral
	{
		System.out.println("Sin validacionesBasicasActualizar " + entidad.getClass().getName());
	}

	protected abstract void validacionesConBaseGuardar(ENTIDAD entidad) throws ErrorServicioNegocio;

	protected  void validacionesBasicasGuardar(ENTIDAD entidad)  throws ErrorValidacionGeneral
	{
		System.out.println("Sin validacionesBasicasGuardar " + entidad.getClass().getName());
	}

	public List<ENTIDAD> listaObjetos(Class<ENTIDAD> clase) {

		return getCrud().obtenerTodaListaObjetos(clase);
	}
	
	public List<ENTIDAD> listaObjetos(Class<ENTIDAD> clase, Boolean orderFecha) {

		return getCrud().obtenerTodaListaObjetos(clase, orderFecha);
	}

	public List<ENTIDAD> listaObjetosActivos(Class<ENTIDAD> clase) {

		return getCrud().obtenerListaObjetosPorEstadoActivo(clase);
	}

	public List<ENTIDAD> listaObjetosInActivos(Class<ENTIDAD> clase) {

		return getCrud().obtenerListaObjetosPorEstadoInactivo(clase);
	}

	public ENTIDAD obtenerObjtoPK(Id clave, Class<ENTIDAD> clase) {
		return getCrud().obtenerObjetoPorPk(clave, clase);
	}
	
	public ENTIDAD obtenerObjetoPropiedad(String clave, Object valor, Class<ENTIDAD> clase) 
	{
		return getCrud().obtenerObjetoPorCampoGenerico(clave, valor, clase);
	}
	
	public void servicioCambioEstado(ENTIDAD entidad)
	throws ErrorServicioNegocio
	{
		getCrud().actualizar(entidad);
	}

	protected boolean existeEntidadPropiedad(String clave, Object valor, Class<ENTIDAD> clase)
	{
		ENTIDAD entidad = getCrud().obtenerObjetoPorCampoGenerico(clave, valor, clase);
		return entidad!=null;
	}
}
