package com.onix.modulo.librerias.vista.beans;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;
import com.onix.modulo.librerias.eao.GenericEAO;
import com.onix.modulo.librerias.exceptions.ErrorServicioNegocio;
import com.onix.modulo.librerias.exceptions.ErrorValidacionGeneral;
import com.onix.modulo.librerias.servicio.ServicioMantenimientoEntidad;
import com.onix.modulo.librerias.vista.JsfUtil;
import com.onix.modulo.librerias.vista.beans.oyentes.PostSeleccionEntidadListener;
import com.onix.modulo.librerias.vista.beans.oyentes.PostConstructListener;
import com.onix.modulo.librerias.vista.beans.oyentes.PostErrorTransaccionListener;
import com.onix.modulo.librerias.vista.beans.oyentes.PreTransaccionListener;
import com.onix.modulo.librerias.vista.beans.oyentes.ValidadorIngresoDatosListener;
import com.onix.modulo.librerias.vista.exceptions.ErrorAccionesPreTransaccion;
import com.onix.modulo.librerias.vista.exceptions.ErrorNoAutenticacion;
import com.onix.modulo.librerias.vista.exceptions.ErrorValidacionVisual;

//La herencia de este componente debe ser un managed bean
public abstract class BeanMantenedorGenerico<SERVICIO extends ServicioMantenimientoEntidad<EAO, ENTIDAD, Id>, Id extends Serializable, ENTIDAD extends EntidadBaseAuditable<Id>, EAO extends GenericEAO<ENTIDAD, Id>>
		implements Serializable {

	protected ENTIDAD entidadRegistrar;

	protected ENTIDAD entidadSelecionable;

	protected HashMap<String, String> listaEtiquetasPantalla;

	private PostSeleccionEntidadListener<ENTIDAD, Id> lPostSeleccionEntidad;
	private PostErrorTransaccionListener lPostErrorTransaccion;
	private PreTransaccionListener lPreTransaccionLister;
	private ValidadorIngresoDatosListener lValidadorIngreso;
	private PostConstructListener lPostConstruct;
	
	private List<ENTIDAD> listaEntidades;

	private Class<ENTIDAD> clase;

	private String nombreAtributoCambioEstado;

	public static final String SERVLET_PDF = "/servlets/report/PDF";

	public static final String PRESENTAR_DIALOGO = "show";

	public static final String OCULTAR_DIALOGO = "hide";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected BeanMantenedorGenerico(ENTIDAD entidad, Class<ENTIDAD> entidadClase) {
		entidadRegistrar = entidad;
		clase = entidadClase;
	}
	
	public ENTIDAD getEntidadRegistrar() {
		return entidadRegistrar;
	}

	public void setEntidadRegistrar(ENTIDAD entidadRegistrar) {
		this.entidadRegistrar = entidadRegistrar;
	}

	public List<ENTIDAD> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<ENTIDAD> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	

	@PostConstruct
	protected void init() {
		try {
			listaEntidades = getServicioMantenedor().listaObjetos(clase, true);
			nombreAtributoCambioEstado = "ENTIDAD_CAMBIAR";
			listaEtiquetasPantalla = new HashMap<>();
			metodoPostConstruct();
			cargarListaEtiquetas();
		} catch (Throwable e) {
			listaEntidades = new ArrayList<ENTIDAD>();
			e.printStackTrace();
		}
	}

	protected FacesContext getContext() {
		return JsfUtil.getContextoJSF();
	}

	protected HttpSession getsession() {

		return JsfUtil.getsession();
	}

	protected HttpServletRequest getRequest() {
		return JsfUtil.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return JsfUtil.getResponse();
	}

	public void addMensaje(String msg) {
		JsfUtil.addMessageInfo(msg);
	}

	public void addMensajeAdvertencia(String msg) {
		JsfUtil.addMessageWarning(msg);
	}

	public void addError(String msg) {
		JsfUtil.addMessageError(msg);
	}

	public String getMensaje(String key) {
		return (String) getExpresion("etiqueta['" + key + "']");
	}

	public boolean isAutenticado() {
		return JsfUtil.isAutenticado();
	}

	public String usuarioAutenticado() {
		return JsfUtil.getUsuarioAutenticado().getName();
	}

	private Object getExpresion(String expression) {
		FacesContext ctx = getContext();
		ExpressionFactory factory = ctx.getApplication().getExpressionFactory();
		ValueExpression ex = factory.createValueExpression(ctx.getELContext(), "#{" + expression + "}", Object.class);
		return ex.getValue(ctx.getELContext());

	}

	protected HashMap<String, Object> getAtributosVisibles() {
		@SuppressWarnings("rawtypes")
		Class clase = this.getClass();
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		try {

			Method[] metodos = clase.getMethods();
			for (Method metodo : metodos) {
				String nombreMetodo = metodo.getName();
				if (nombreMetodo.toUpperCase().indexOf("GET") != -1) {
					Object obj = metodo.invoke(this);
					if (obj != null) {
						parametros.put(nombreMetodo.toUpperCase(), obj);
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return parametros;
	}

	protected void redireccionarPagina(String urlPagina) throws IOException {
		FacesContext ctx = getContext();
		ExternalContext extContext = ctx.getExternalContext();
		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, urlPagina));
		System.out.println("url a redireccionar " + url);
		extContext.redirect(url);
	}

	public String[] ajaxErrorHandler(Exception e2) {
		System.out.println("Error Handler");
		e2.printStackTrace();
		String errorNameAjax = e2.getMessage();
		String errorMsgAjax = "" + e2;
		for (StackTraceElement err : e2.getStackTrace())
			errorMsgAjax = errorMsgAjax + "\n" + err.toString();
		String[] resultado = new String[3];
		resultado[0] = errorNameAjax;
		resultado[1] = errorMsgAjax;
		return resultado;
	}

	public String getCurrentTimeZone() {
		return Calendar.getInstance().getTimeZone().getID();
	}

	public void seleccionVisual() {
		entidadRegistrar = entidadSelecionable;
	}

	public ENTIDAD getEntidadSelecionable() {
		return entidadSelecionable;
	}

	public void setEntidadSelecionable(ENTIDAD entidadSelecionable) {
		this.entidadSelecionable = entidadSelecionable;
	}

	public void guardarOActualizar(boolean presentarMensaje) {
		try {
			validacionesIngreso();
			try {
				this.entidadRegistrar.setAuditoria(JsfUtil.getUsuarioAutenticado().getName());
			} catch (Throwable e1) {
				e1.printStackTrace();
				throw new ErrorNoAutenticacion("No realizar esta operación, si ningun usuario está autenticado");
			}
			accionesPreTransaccionServicio();
			getServicioMantenedor().guardarActualizar(entidadRegistrar);
			metodoPostTransaccion();
			if (presentarMensaje)
				addMensaje(JsfUtil.MENSAJE_INFO_OPERACION);
		} catch (ErrorValidacionVisual e1) {
			e1.printStackTrace();
			if (presentarMensaje)
				addError(e1.getMensajeUsuario());
			System.out.println("Error validaciones visuales, no borrar nada");
		} catch (ErrorNoAutenticacion e1) {
			e1.printStackTrace();
			if (presentarMensaje)
				addError(e1.getMensajeUsuario());
			System.out.println("Error usuario no autenticado, hay que hacer algo");
		} catch (ErrorAccionesPreTransaccion e1) {
			e1.printStackTrace();
			if (presentarMensaje)
				addError(e1.getMensajeUsuario());
			System.out.println("Error acciones pre transaccion");

		} catch (ErrorServicioNegocio e1) {
			e1.printStackTrace();
			metodoPostErrorTransaccion();
			if (presentarMensaje)
				addError(e1.getMessage());
		} catch (ErrorValidacionGeneral e2) {
			e2.printStackTrace();
			metodoPostErrorTransaccion();
			if (presentarMensaje)
				addError(e2.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			metodoPostErrorTransaccion();
			if (presentarMensaje)
				addError(JsfUtil.MENSAJE_ERROR_OPERACION);
		}

	}

	public void guardarOActualizar() {
		guardarOActualizar(true);
	}

	protected void metodoPostTransaccion() {
		listaEntidades = getServicioMantenedor().listaObjetos(clase, true);

	}

	public String getEstadoActivo() {
		return GenericEAO.ESTADO_ACTIVO;
	}

	public String getEstadoInactivo() {
		return GenericEAO.ESTADO_INACTIVO;
	}

	@SuppressWarnings("unchecked")
	public void actualizarEntidad(ActionEvent evento) {
		entidadRegistrar = (ENTIDAD) evento.getComponent().getAttributes().get(nombreAtributoCambioEstado);
		postSeleccionRegistro(entidadRegistrar);
		actualizarComponenteVisual("dialogoMantenimiento");
		presentarDialogo("dialogoMantenimiento");
	}
	
	@SuppressWarnings("unchecked")
	public void actualizarEntidadFormulario(ActionEvent evento) {
		entidadRegistrar = (ENTIDAD) evento.getComponent().getAttributes().get(nombreAtributoCambioEstado);
		postSeleccionRegistro(entidadRegistrar);

	}

	@SuppressWarnings("unchecked")
	public void cambiarEstado(ActionEvent evento) {
		entidadSelecionable = (ENTIDAD) evento.getComponent().getAttributes().get(nombreAtributoCambioEstado);
		try {
			entidadSelecionable.setEstado(!entidadSelecionable.getEstado().equals(GenericEAO.ESTADO_ACTIVO) ? GenericEAO.ESTADO_ACTIVO
					: GenericEAO.ESTADO_INACTIVO);
		} catch (Exception e) {
			entidadSelecionable.setEstado(GenericEAO.ESTADO_ACTIVO);
			addMensajeAdvertencia("No se encontró un estado registrado, se procede a activar el registro");
		}
		try {
			getServicioMantenedor().servicioCambioEstado(entidadSelecionable);
			addMensaje(JsfUtil.MENSAJE_INFO_OPERACION);
		} catch (ErrorServicioNegocio error) {
			error.printStackTrace();
			addError(error.getMessage());
		} catch (Throwable error) {
			error.printStackTrace();
			addError(JsfUtil.MENSAJE_ERROR_OPERACION);
		}
		metodoPostTransaccion();
	}

	public String getNombreAtributoCambioEstado() {
		return nombreAtributoCambioEstado;
	}

	private void ejecutarComponenteVisual(String funcionEjecutar) {
		RequestContext.getCurrentInstance().execute(funcionEjecutar);
	}

	private String prepararComandoDialogo(String nombreDialogo, String accion) {
		return "PF('" + nombreDialogo + "')." + accion + "()";
	}

	public void presentarDialogo(String nombreDialogo) {
		ejecutarComponenteVisual(prepararComandoDialogo(nombreDialogo, PRESENTAR_DIALOGO));
	}

	public void ocultarDialogo(String nombreDialogo) {
		ejecutarComponenteVisual(prepararComandoDialogo(nombreDialogo, OCULTAR_DIALOGO));
	}

	@SuppressWarnings("el-syntax")
	private String prepararComandoActualizarComponente(String idComponente) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(":#{p:component('");
		stringBuilder.append(idComponente);
		stringBuilder.append("')}");
		return stringBuilder.toString();
	}

	public void actualizarComponenteVisual(String idComponente) {
		RequestContext.getCurrentInstance().update(prepararComandoActualizarComponente(idComponente));
	}

	// ********************************
	// Desde aqui se consultan las etieutas
	// ********************************
	public String getTituloPagina() {
		String lTitulo = listaEtiquetasPantalla.get(NombresEtiquetas.TITULOPAGINA);
		return lTitulo == null ? "" : lTitulo;
	}

	public String getMensajeTablaVacia() {
		String lTablaVacia = listaEtiquetasPantalla.get(NombresEtiquetas.TABLAVACIA);
		return lTablaVacia == null ? "" : lTablaVacia;
	}

	public String getDescripcionPagina() {
		String lDescripcionPagina = listaEtiquetasPantalla.get(NombresEtiquetas.DESCRIPCIONPAGINA);
		return lDescripcionPagina == null ? "" : lDescripcionPagina;
	}

	public String getAyudaPagina() {
		String lAyudaPagina = listaEtiquetasPantalla.get(NombresEtiquetas.AYUDAPAGINA);
		return lAyudaPagina == null ? "" : lAyudaPagina;
	}

	public String getTab() {
		String lTab = listaEtiquetasPantalla.get(NombresEtiquetas.TAB);
		return lTab == null ? "" : lTab;
	}

	public String getCabeceraTabla() {
		String lCabeceraTabla = listaEtiquetasPantalla.get(NombresEtiquetas.CABECERATABLA);
		return lCabeceraTabla == null ? "" : lCabeceraTabla;
	}

	public String getCabeceraDialogo() {
		String lCabeceraDialogo = listaEtiquetasPantalla.get(NombresEtiquetas.CABECERADIALOGO);
		return lCabeceraDialogo == null ? "" : lCabeceraDialogo;
	}

	public String getCabeceraPanelDialogo() {
		String lCabeceraPanelDialogo = listaEtiquetasPantalla.get(NombresEtiquetas.CABECERAPANELDIALOGO);
		return lCabeceraPanelDialogo == null ? "" : lCabeceraPanelDialogo;
	}

	// ***********************************************
	// Metodos abtractos para la inicializacion
	// ***********************************************
	protected abstract void cargarListaEtiquetas();

	protected void metodoPostConstruct()
	{
		if (lPostConstruct != null)
			lPostConstruct.metodoPostConstruct();
		else
			System.out.println("NO EXISTE LISTENER REGISTRADO PARA EL METODO POST CONSTRUCT "
					+ this.getClass().getCanonicalName());
	}

	// **********************************
	// Metodos para las transacciones
	// **********************************
	// Validacion de ingreso se debe
	protected abstract SERVICIO getServicioMantenedor();

	protected void validacionesIngreso() throws ErrorValidacionVisual {
		if (lValidadorIngreso != null)
			lValidadorIngreso.validarDatosIngreso();
		else
			System.out.println("NO EXISTE LISTENER REGISTRADO PARA LA VALIDACION DE INGRESO "
					+ this.getClass().getCanonicalName());
	}
	
	public void addValidacionListener(ValidadorIngresoDatosListener pValidadorListener)
	{
		lValidadorIngreso = pValidadorListener;
	}
	
	

	protected void accionesPreTransaccionServicio() throws ErrorAccionesPreTransaccion {
		if (lPreTransaccionLister != null)
			lPreTransaccionLister.accionPreTransaccion();
		else
			System.out.println(
					"NO EXISTE LISTENER REGISTRADO PARA LA PRE TRANSACCION " + this.getClass().getCanonicalName());
	}
	
	public void addPreTransaccionServicioListener(PreTransaccionListener pPreTransaccionListener)
	{
		lPreTransaccionLister = pPreTransaccionListener;
	}

	protected void metodoPostErrorTransaccion() {
		if (lPostErrorTransaccion != null)
			lPostErrorTransaccion.accionPostTransaccion();
		else
			System.out.println("NO EXISTE LISTENER REGISTRADO PARA LA POST ERROR TRANSACCION "
					+ this.getClass().getCanonicalName());
	}

	public void addPostErrorTransaccionListener(PostErrorTransaccionListener pPostErrorTransaccionListener)
	{
		lPostErrorTransaccion = pPostErrorTransaccionListener;
	}

	protected void postSeleccionRegistro(ENTIDAD pEntidadSeleccionada) {
		if (lPostSeleccionEntidad != null)
			lPostSeleccionEntidad.postSeleccionRegistro(pEntidadSeleccionada);
		else
			System.out.println("NO EXISTE LISTENER REGISTRADO PARA LA POST SELECCION REGISTRO "
					+ this.getClass().getCanonicalName());
	}
	
	public void addPostSeleccionRegistroListener(PostSeleccionEntidadListener<ENTIDAD, Id> pPostSeleccionEntidadListener)
	{
		lPostSeleccionEntidad = pPostSeleccionEntidadListener;
	}

}
