package com.onix.modulo.librerias.vista;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.Collator;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.onix.modulo.librerias.dominio.entidades.base.EntidadBaseAuditable;

public final class JsfUtil {

	public static final String REFERENCIA_SESION = "REFERENCIA_SESION";

	public static final String MENSAJE_ERROR_OPERACION = "Ocurrió un error al completar la operación.";

	public static final String CLAVE_INICIAL_DEFAULT = "123456789";

	public static final String MENSAJE_INFO_OPERACION = "La operación se realiza satisfactoriamente.";

	public static final String MENSAJE_INFO_SINRESULTADO = "No se encontraron resultados para la búsqueda.";

	public static final String NAVEGACION_A_HOME = "/aplicacion/generales/pag_principal.jsf";

	public static final String STRING_SEPARATOR = ";;";
	public static final String ERROR_ACTUALIZAR_REGISTRO = "Error al actualizar el registro";
	public static final String REGISTRO_ACTUALIZADO = "Registro actualizado.";
	public static final String REGISTRO_GUARDADO = "La operaci�n se realiza satisfactoriamente.";
	public static final String ERROR_GUARDAR_REGISTRO = "Error, la operaci�n no se realiza satisfactoriamente. Por favor intentelo m�s tarde.";
	public static final String ERROR_INICIALIZAR_DATOS = "Ocurrio un error al inicializar los datos. Por favor intentelo m�s tarde.";
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(JsfUtil.class);
	private static final Integer TAMANIO_PASSWORD = 10;

	private JsfUtil() {
	}

	public static String getBundleString(String str) {
		try {
			return getBundle().getString(str);
		} catch (Exception a) {
			return "";
		}
	}

	private static Random random = new Random();

	public static boolean isDummySelectItem(UIComponent component, String value) {
		for (UIComponent children : component.getChildren()) {
			if (children instanceof UISelectItem) {
				UISelectItem item = (UISelectItem) children;
				if (item.getItemValue() == null && item.getItemLabel().equals(value)) {
					return true;
				}
				break;
			}
		}
		return false;
	}

	public static HttpServletRequest getRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request;
	}

	public static String getStartPage() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
		String url = req.getRequestURL().toString();
		return url.substring(0, url.indexOf(req.getContextPath()) + req.getContextPath().length());
	}

	public static void addMessageInfo(String message) {
		addMessage(message, FacesMessage.SEVERITY_INFO);
	}

	public static void addMessageWarning(String message) {
		addMessage(message, FacesMessage.SEVERITY_WARN);
	}

	public static void addMessageError(String message) {
		addMessage(message, FacesMessage.SEVERITY_ERROR);
	}

	public static void addMessageError(final String message, long delay) {
		try {
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					addMessageError(message);
				}
			}, delay);
		} catch (Exception e) {
		}
	}

	public static void addMessageError(List<String> messages) {
		for (String message : messages) {
			addMessage(message, FacesMessage.SEVERITY_ERROR);
		}
	}

	private static void addMessage(String message, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
	}

	public static void addMessageErrorForComponent(String component, String message) {
		FacesContext.getCurrentInstance().addMessage(component,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public static String addMessageFromBundle(String bundleName, Severity severity, String key, Object... params) {
		String messageBundleName = bundleName;
		if (messageBundleName == null) {
			messageBundleName = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
		}
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
		try {
			return MessageFormat.format(bundle.getString(key), params);
		} catch (Exception e) {
			return '!' + key + '!';
		}
	}

	public static String actionNavigateTo(String url) {
		return url + "?faces-redirect=true";
	}

	public static String actionNavigateTo(String url, String... params) {
		String add = "";
		if (params != null) {
			for (String string : params) {
				add += string + "&";
			}
			add = add.substring(0, add.length() - 1);
		}
		return url + "?faces-redirect=true" + (add.isEmpty() ? add : "&" + add);
	}

	public static String getRequestParameter(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key) == null ? ""
				: FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}

	public static String actionNavegacionAHome() {
		return actionNavigateTo(NAVEGACION_A_HOME);
	}

	public static void redirectTo(String url) {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(externalContext.getRequestContextPath() + url);
		} catch (IOException e) {
			Logger.getLogger("").log(Level.SEVERE, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> beanType) {
		LOG.info("OBTENCION DE BEAN " + beanType.getCanonicalName());
		String standardBeanName = (beanType.getSimpleName().charAt(0) + "").toLowerCase()
				+ beanType.getSimpleName().substring(1);
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		return (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null,
				standardBeanName);
	}

	public static String getStringAsHtmlUL(String value, boolean sort) {
		String[] arr = value.split(JsfUtil.STRING_SEPARATOR);
		if (sort) {
			Arrays.sort(arr);
		}
		int size = 0;
		String list = "<ul>";
		for (String string : arr) {
			if (!string.trim().isEmpty()) {
				size++;
				list += "<li>" + string.trim() + "</li>";
			}
		}
		list += "</ul>";
		if (size <= 1) {
			list = list.replaceAll("\\<.*?>", "");
		}
		return list.trim();
	}

	public static Map<String, String> generateEmptyMapString(final Class<?> enumClass) {
		final Object constants[] = enumClass.getEnumConstants();
		final Map<String, String> map = new ConcurrentHashMap<String, String>();
		for (final Object object : constants) {
			map.put(((Enum<?>) object).name(), "");
		}
		return map;
	}

	@Deprecated
	public static Object seleccionar(ActionEvent evt, String llave) {
		Map<String, Object> id = evt.getComponent().getAttributes();
		return id.get(llave);
	}

	public static String claveEncriptadaMd5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String claveEncriptadaSHA1(String password) {
		try {
			byte[] buffer = password.getBytes();
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(buffer);
			byte[] digest = md.digest();
			String valorHash = "";
			for (byte aux : digest) {
				int b = aux & 0xff;
				if (Integer.toHexString(b).length() == 1) {
					valorHash += "0";
				}
				valorHash += Integer.toHexString(b);
			}
			return valorHash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized String generatePassword(boolean... onlyChars) {
		String passwd = "";
		boolean isChar = onlyChars == null ? false : onlyChars.length > 0 ? onlyChars[0] : false;
		for (char c : complete("" + (int) (random.nextDouble() * 99999999), TAMANIO_PASSWORD, '0', true)
				.toCharArray()) {
			int value = (int) (Integer.parseInt("" + c) + Math.round(Math.random() * 120));
			char cc = (char) value;
			if (Character.isLetter(cc) & Character.isDefined(cc) & !Character.isWhitespace(cc)) {
				passwd += cc;
			} else {
				value = (int) (isChar ? Math.round(Math.random() * 25) + 65 : value);
				passwd += isChar ? (char) value : c;
			}
		}
		return passwd;
	}

	public static synchronized String complete(String data, final int length, final char complete,
			final boolean reverse) {
		final int size = data.length();
		StringBuilder build = new StringBuilder();
		if (reverse) {
			for (int i = size; i < length; i++) {
				build.append(complete);
			}
			build.append(data);
		} else {
			build.append(data);
			for (int i = size; i < length; i++) {
				build.append(complete);
			}
		}
		return build.toString();
	}

	public static void cargarObjetoSession(final String nombre, final Object object) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(nombre, object);
	}

	public static Object devolverObjetoSession(final String nombre) {
		HttpServletRequest request = getRequest();
		return request.getSession().getAttribute(nombre);
	}

	public static Object devolverEliminarObjetoSession(final String nombre) {
		HttpServletRequest request = getRequest();
		Object object = request.getSession().getAttribute(nombre);
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove(nombre);
		return object;
	}

	public static void eliminarObjetoSession(final String... nombre) {
		FacesContext context = FacesContext.getCurrentInstance();
		for (String s : nombre) {
			context.getExternalContext().getSessionMap().remove(s);
		}
	}

	public static HttpSession getsession() {

		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static void validarPagina(final String pagina) {
		@SuppressWarnings("unchecked")
		List<String> listaPermisos = (List<String>) devolverObjetoSession("listaPermisos");
		if (listaPermisos != null && !listaPermisos.isEmpty()) {
			if (!listaPermisos.contains(pagina)) {
				// redirectTo("/errors/permisos.jsf");
			}
		}
	}

	public static boolean comparePrimaryStrings(String s1, String s2) {
		Collator collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
		return collator.equals(s1, s2);
	}

	public static boolean isStringInPrimaryStrings(String string, String[] strings) {
		for (String s : strings) {
			if (comparePrimaryStrings(string, s)) {
				return true;
			}
		}
		return false;
	}

	public static String getStringAsAnyPrimaryStrings(String string, String[] strings) {
		for (String s : strings) {
			if (comparePrimaryStrings(string, s)) {
				return s;
			}
		}
		return string;
	}

	@SafeVarargs
	public static <T> List<T> getAsList(T... objects) {
		return Arrays.asList(objects);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void ordenarLista(List lista, final String propiedad) {
		Collections.sort(lista, new Comparator() {

			public int compare(Object obj1, Object obj2) {

				Class clase = obj1.getClass();
				String getter = "get" + Character.toUpperCase(propiedad.charAt(0)) + propiedad.substring(1);
				try {

					Method getPropiedad = clase.getMethod(getter);

					Object propiedad1 = getPropiedad.invoke(obj1);
					Object propiedad2 = getPropiedad.invoke(obj2);

					if (propiedad1 instanceof Comparable && propiedad2 instanceof Comparable) {
						Comparable prop1 = (Comparable) propiedad1;
						Comparable prop2 = (Comparable) propiedad2;
						return prop1.compareTo(prop2);
					} else {
						if (propiedad1.equals(propiedad2)) {
							return 0;
						} else {
							return 1;
						}

					}

				} catch (NoSuchMethodException e) {
					Logger.getLogger("").log(Level.SEVERE, e.getMessage());
				} catch (SecurityException e) {
					Logger.getLogger("").log(Level.SEVERE, e.getMessage());
				} catch (IllegalAccessException e) {
					Logger.getLogger("").log(Level.SEVERE, e.getMessage());
				} catch (IllegalArgumentException e) {
					Logger.getLogger("").log(Level.SEVERE, e.getMessage());
				} catch (InvocationTargetException e) {
					Logger.getLogger("").log(Level.SEVERE, e.getMessage());
				}
				return 0;
			}
		});
	}

	public static String transformaVector(String[] datos) {
		int i = 1;
		String concatena = "";
		for (String l : datos) {
			if (i != datos.length) {
				concatena = concatena.concat(l.concat(","));
			} else {
				concatena = concatena.concat(l + "");
			}
			i++;
		}
		return concatena;
	}

	public static String[] devuelveVector(String datos) {
		try {
			StringTokenizer tokens = new StringTokenizer(datos, ",");
			int numeroTokens = tokens.countTokens();
			String[] devuelve = new String[numeroTokens];
			int i = 0;
			while (tokens.hasMoreTokens()) {
				devuelve[i] = tokens.nextToken();
				i++;
			}
			return devuelve;
		} catch (Exception e) {
			return new String[0];
		}
	}

	public static String devuelveExtension(String archivo) {
		StringTokenizer st = new StringTokenizer(archivo, ".");
		String extension = "";
		while (st.hasMoreTokens()) {
			extension = st.nextToken();
		}
		return extension;
	}

	public static String getDateFormat(Integer day, Integer month, Integer year) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));
		Calendar calendar = new GregorianCalendar(year, month, day);
		return formateador.format(calendar.getTime());
	}

	public static String getDateFormat(Date date) {
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(date);
		return devuelveDiaSemana(fecha.get(Calendar.DAY_OF_WEEK)) + fecha.get(Calendar.DAY_OF_MONTH) + " de "
				+ devuelveMes(fecha.get(Calendar.MONTH)) + " " + fecha.get(Calendar.YEAR);
	}

	public static String getFromBundle(String bundlePath, String key) {
		return ResourceBundle.getBundle(bundlePath, new Locale("es")).getString(key);
	}

	public static FacesContext getContextoJSF() {
		return FacesContext.getCurrentInstance();
	}

	public static void descargarPdf(final byte[] bytes, String nombrePdf) throws IOException {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.setHeader("Content-Type", "application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + nombrePdf + ".pdf");
		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.close();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public static void descargarMimeType(final byte[] bytes, String nombreArchivo, String extension, String mime)
			throws IOException {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.setHeader("Content-Type", mime);
		response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + "." + extension);
		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.close();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public static String devolverPathReportesHtml(final String reporteHtml) {
		return getRequest().getSession().getServletContext().getRealPath("/reportesHtml/" + reporteHtml);
	}

	public static String devolverContexto(final String pathArchivo) {
		return getRequest().getContextPath() + pathArchivo;
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) getContextoJSF().getExternalContext().getResponse();
	}

	public static boolean isAutenticado() {

		try {
			return ((HttpServletRequest) getContextoJSF().getExternalContext().getRequest()).getUserPrincipal() != null;
		} catch (Throwable e) {
			System.err.println("Se esta consultando por una autenticacion que no se ha creado");
			return false;
		}
	}

	public static boolean isUserInRole(String pRol) {
		return ((HttpServletRequest) getContextoJSF().getExternalContext().getRequest()).isUserInRole(pRol);
	}

	public static Principal getUsuarioAutenticado() {
		return ((HttpServletRequest) getContextoJSF().getExternalContext().getRequest()).getUserPrincipal();
	}

	public static String getNombreUsuarioAutenticado() {
		Principal lPrincipal = getUsuarioAutenticado();
		String lNombreUsuario = null;
		if (lPrincipal != null)
			lNombreUsuario = lPrincipal.getName();
		return lNombreUsuario;
	}

	public static String devuelveFechaEnLetrasSinHora(Date fechaParametro) {
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(fechaParametro);
		return devuelveDiaSemana(fecha.get(Calendar.DAY_OF_WEEK)) + fecha.get(Calendar.DAY_OF_MONTH) + " de "
				+ devuelveMes(fecha.get(Calendar.MONTH)) + " " + fecha.get(Calendar.YEAR);
	}

	private static String devuelveDiaSemana(int dia) {
		switch (dia) {
		case 1:
			return "domingo ";
		case 2:
			return "lunes ";
		case 3:
			return "martes ";
		case 4:
			return "mi�rcoles ";
		case 5:
			return "jueves ";
		case 6:
			return "viernes ";
		case 7:
			return "sabado ";
		default:
			return "";

		}
	}

	public static String devuelveMes(int mes) {
		switch (mes) {
		case 0:
			return "enero";
		case 1:
			return "febrero";
		case 2:
			return "marzo";
		case 3:
			return "abril";
		case 4:
			return "mayo";
		case 5:
			return "junio";
		case 6:
			return "julio";
		case 7:
			return "agosto";
		case 8:
			return "septiembre";
		case 9:
			return "octubre";
		case 10:
			return "noviembre";
		case 11:
			return "diciembre";
		default:
			return "";
		}
	}

	public static boolean validarMail(String email) {
		boolean valido = false;
		Pattern patronEmail = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mEmail = patronEmail.matcher(email);
		if (mEmail.matches()) {
			valido = true;
		}
		return valido;
	}

	public static long devuelveFechaTruncadaMilisegundos(Date fecha, int valorTruncado) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, valorTruncado);
		cal.set(Calendar.MINUTE, valorTruncado);
		cal.set(Calendar.SECOND, valorTruncado);
		cal.set(Calendar.MILLISECOND, valorTruncado);
		return cal.getTime().getTime();
	}

	public static boolean validarCedulaORUC(String numero) {
		int suma = 0;
		int residuo = 0;
		boolean privada = false;
		boolean publica = false;
		boolean natural = false;
		int numeroProvincias = 24;
		int digitoVerificador = 0;
		int modulo = 11;

		int d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
		int p1, p2, p3, p4, p5, p6, p7, p8, p9;

		d1 = d2 = d3 = d4 = d5 = d6 = d7 = d8 = d9 = d10 = 0;
		p1 = p2 = p3 = p4 = p5 = p6 = p7 = p8 = p9 = 0;

		if (numero.length() < 10) {
			return false;
		}

		// Los primeros dos digitos corresponden al codigo de la provincia
		int provincia = Integer.parseInt(numero.substring(0, 2));

		if (provincia <= 0 || provincia > numeroProvincias) {
			return false;
		}

		// Almacena los digitos de la cedula en variables.
		d1 = Integer.parseInt(numero.substring(0, 1));
		d2 = Integer.parseInt(numero.substring(1, 2));
		d3 = Integer.parseInt(numero.substring(2, 3));
		d4 = Integer.parseInt(numero.substring(3, 4));
		d5 = Integer.parseInt(numero.substring(4, 5));
		d6 = Integer.parseInt(numero.substring(5, 6));
		d7 = Integer.parseInt(numero.substring(6, 7));
		d8 = Integer.parseInt(numero.substring(7, 8));
		d9 = Integer.parseInt(numero.substring(8, 9));
		d10 = Integer.parseInt(numero.substring(9, 10));

		// El tercer digito es:
		// 9 para sociedades privadas y extranjeros
		// 6 para sociedades publicas
		// menor que 6 (0,1,2,3,4,5) para personas naturales
		if (d3 == 7 || d3 == 8) {
			return false;
		}

		// Solo para personas naturales (modulo 10)
		if (d3 < 6) {
			natural = true;
			modulo = 10;
			p1 = d1 * 2;
			if (p1 >= 10) {
				p1 -= 9;
			}
			p2 = d2 * 1;
			if (p2 >= 10) {
				p2 -= 9;
			}
			p3 = d3 * 2;
			if (p3 >= 10) {
				p3 -= 9;
			}
			p4 = d4 * 1;
			if (p4 >= 10) {
				p4 -= 9;
			}
			p5 = d5 * 2;
			if (p5 >= 10) {
				p5 -= 9;
			}
			p6 = d6 * 1;
			if (p6 >= 10) {
				p6 -= 9;
			}
			p7 = d7 * 2;
			if (p7 >= 10) {
				p7 -= 9;
			}
			p8 = d8 * 1;
			if (p8 >= 10) {
				p8 -= 9;
			}
			p9 = d9 * 2;
			if (p9 >= 10) {
				p9 -= 9;
			}
		}

		// Solo para sociedades publicas (modulo 11)
		// Aqui el digito verficador esta en la posicion 9, en las otras 2
		// en la pos. 10
		if (d3 == 6) {
			publica = true;
			p1 = d1 * 3;
			p2 = d2 * 2;
			p3 = d3 * 7;
			p4 = d4 * 6;
			p5 = d5 * 5;
			p6 = d6 * 4;
			p7 = d7 * 3;
			p8 = d8 * 2;
			p9 = 0;
		}

		/*
		 * Solo para entidades privadas (modulo 11)
		 */
		if (d3 == 9) {
			privada = true;
			p1 = d1 * 4;
			p2 = d2 * 3;
			p3 = d3 * 2;
			p4 = d4 * 7;
			p5 = d5 * 6;
			p6 = d6 * 5;
			p7 = d7 * 4;
			p8 = d8 * 3;
			p9 = d9 * 2;
		}

		suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
		residuo = suma % modulo;

		// Si residuo=0, dig.ver.=0, caso contrario 10 - residuo
		digitoVerificador = residuo == 0 ? 0 : modulo - residuo;
		int longitud = numero.length();
		// ahora comparamos el elemento de la posicion 10 con el dig. ver.
		if (publica) {
			if (digitoVerificador != d9) {
				return false;
			}
			/*
			 * El ruc de las empresas del sector publico terminan con 0001
			 */
			if (!numero.substring(9, longitud).equals("0001")) {
				return false;
			}
		}

		if (privada) {
			if (digitoVerificador != d10) {
				return false;
			}
			if (!numero.substring(10, longitud).equals("001")) {
				return false;
			}
		}

		if (natural) {
			if (digitoVerificador != d10) {
				return false;
			}
			if (numero.length() > 10 && !numero.substring(10, longitud).equals("001")) {
				return false;
			}
		}
		return true;
	}

	public static String devolverPathImagen(String imagen) {
		return getRequest().getSession().getServletContext().getRealPath(imagen);
	}

	public static String rellenarCeros(String cadena, int tamanio) {
		for (int i = cadena.length(); i < tamanio; i++) {
			cadena = "0" + cadena;
		}
		return cadena;
	}

	public static int getCurrentYear() {
		return Calendar.getInstance().get(1);
	}

	public static int getDayFromDate(Date fecha) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		return c.get(7);
	}

	public static int getMonthFromDate(Date fecha) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		return c.get(2);
	}

	public static int getYearFromDate(Date fecha) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		return c.get(1);
	}

	public static String getRelativeCurrentPage() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
		String url = req.getRequestURL().toString();
		return url.substring(url.indexOf(req.getContextPath()));
	}

	public static UIComponent createComponent(String name) {
		return FacesContext.getCurrentInstance().getApplication().createComponent(name);
	}

	public static MethodExpression createMethodExpression(String expression, Class<?> returnType) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(),
				expression, returnType, new Class[0]);
	}

	public static String getComponentMessages(String clientComponent, String defaultMessage) {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent component = UIComponent.getCurrentComponent(fc).findComponent(clientComponent);
		if (component instanceof UIInput) {
			UIInput inputComponent = (UIInput) component;
			if (inputComponent.isValid()) {
				return defaultMessage;
			} else {
				Iterator<FacesMessage> iter = fc.getMessages(inputComponent.getClientId());
				if (iter.hasNext()) {
					return iter.next().getDetail();
				}
			}
		}
		return "";
	}

	public static Throwable getRootCause(Throwable cause) {
		if (cause != null) {
			Throwable source = cause.getCause();
			if (source != null) {
				return getRootCause(source);
			} else {
				return cause;
			}
		}
		return null;
	}

	public static boolean isValidationFailed() {
		return FacesContext.getCurrentInstance().isValidationFailed();
	}

	public static boolean isNumeric(String str) {
		int digits = 0;
		if (str == null || str.isEmpty()) {
			return false;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			} else {
				digits++;
			}
		}

		return digits <= 9;
	}

	public static Double round(Double value) {

		if (value != null) {
			System.out.println("value: " + value);
			System.out.println("value to String: " + value.toString());
			String[] splitter = value.toString().split("\\.");
			System.out.println("decimals: " + splitter[1].length());
			if (splitter[1].length() <= 2) {
				return value;
			} else {
				return (long) (value * 1e2) / 1e2;
			}

		} else {
			return 1d;
		}
	}

	public static Double round(Double value, Integer decimals) {
		if (value != null) {

			System.out.println("value: " + value);
			System.out.println("value to String: " + value.toString());
			String[] splitter = value.toString().split("\\.");
			System.out.println("decimals: " + splitter[1].length());

			if (splitter[1].length() <= decimals) {
				return value;
			}
			if (decimals == 3) {
				return (long) (value * 1e3) / 1e3;
			}
			if (decimals == 2) {
				return (long) (value * 1e2) / 1e2;
			}
			if (decimals == 1) {
				return (long) (value * 1e1) / 1e1;
			} else {
				return (long) (value * 1e0) / 1e0;
			}

		} else {
			return null;
		}
	}

	public static void completarDatosAutoria(EntidadBaseAuditable lEntidad) {
		lEntidad.setIdReferencia(obtenerObjetoSesion(REFERENCIA_SESION));
		lEntidad.setAuditoria(getUsuarioAutenticado().getName());
	}

	public static <T extends Serializable> T obtenerObjetoSesion(String pNombreParametro) {
		return (T) getsession().getAttribute(pNombreParametro);
	}

	public static ResourceBundle getBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("Messages", context.getViewRoot().getLocale());
		return bundle;
	}

	public static void throwWarningValidatorException(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
		throw new ValidatorException(facesMsg);
	}

	public static void addErrorMessage(Exception ex, String defaultMsg) {
		String msg = ex.getLocalizedMessage();
		if (msg != null && msg.length() > 0) {
			addErrorMessage(msg);
		} else {
			addErrorMessage(defaultMsg);
		}
	}

	public static void addErrorMessages(List<String> messages) {
		for (String message : messages) {
			addErrorMessage(message);
		}
	}

	public static void addErrorMessage(String msg) {
		ResourceBundle bundle = getBundle();
		String m = bundle.getString(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, m);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void addSuccessMessage(String msg) {
		ResourceBundle bundle = getBundle();
		String m = bundle.getString(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, m, m);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void addWarningMessage(String msg) {
		ResourceBundle bundle = getBundle();
		String m = bundle.getString(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, m, m);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void addWarningMessageDialog(String msgHeader, String msg) {
		ResourceBundle bundle = getBundle();
		String h = bundle.getString(msgHeader);
		String m = bundle.getString(msg);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, h, m);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public static void addSuccessMessageDialog(String msgHeader, String msg) {
		ResourceBundle bundle = getBundle();
		String h = bundle.getString(msgHeader);
		String m = bundle.getString(msg);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, h, m);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public static void addWarningCustomMessageDialog(String msgHeader, String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msgHeader, msg);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

}
