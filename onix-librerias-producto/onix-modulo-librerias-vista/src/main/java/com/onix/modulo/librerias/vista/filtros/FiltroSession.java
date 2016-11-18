package com.onix.modulo.librerias.vista.filtros;

import java.io.IOException;
import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onix.modulo.librerias.vista.anotaciones.ITestGuardiaSession;
import com.onix.modulo.librerias.vista.anotaciones.ITestServicioAutorizacion;
import com.onix.modulo.librerias.vista.interfaces.IGuardiaUsuarioSession;
import com.onix.modulo.librerias.vista.interfaces.IServicioAutorizacion;

@WebFilter(dispatcherTypes = { DispatcherType.ERROR, 
DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE }, 
		urlPatterns = { "/privadas/*" })
public class FiltroSession implements Filter {

	@Inject
	@ITestGuardiaSession
	private IGuardiaUsuarioSession guardiaUsuarioSession;

	@Inject
	@ITestServicioAutorizacion
	private IServicioAutorizacion serviciosMantenimientoSeguridad;

	public FiltroSession() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		res.setDateHeader("Expires", 0);
		res.setHeader("Pragma", "No-cache");

		String dato = null;
		if (req.getSession(false) != null) {

			System.out.println("***************************************************");
			System.out.println("EN SESION");
			System.out.println("***************************************************");
			dato = (String) req.getSession(false).getAttribute(IGuardiaUsuarioSession.SEMILLA);
		}

		System.out.println("***************************************************");
		System.out.println("USARIO EN SESION: " + guardiaUsuarioSession.usuarioEnSession());
		System.out.println("URL: " + req.getRequestURI());
		System.out.println("En sesion" + req.getRequestURI().endsWith(IGuardiaUsuarioSession.PAGINA_PRINCIPAL));
		System.out.println("METODO " + req.getMethod().toUpperCase());
		System.out.println("ATRIBUTO " + dato);
		System.out.println("***************************************************");

		HttpSession session = req.getSession(false);

		Principal usuarioPrincipal = req.getUserPrincipal();
		System.out.println("Filtro principal " + usuarioPrincipal);
		if (usuarioPrincipal == null) 
		{
			System.out.println("Nadie autenticado");
			res.sendRedirect(req.getContextPath() + IGuardiaUsuarioSession.PAGINA_LOGIN_RED);
		} else {

			if (session == null
					|| !guardiaUsuarioSession.usuarioEnSession()
					|| (req.getMethod().toUpperCase().equals("GET")
							&& req.getRequestURI().endsWith(IGuardiaUsuarioSession.PAGINA_PRINCIPAL) && (dato == null && !guardiaUsuarioSession
							.validarSemilla(dato)))

			) {
				System.out.println("***************************************************");
				System.out.println("SE REDIRECCIONARA A LOGIN");
				System.out.println("***************************************************");
				res.sendRedirect(req.getContextPath() + IGuardiaUsuarioSession.PAGINA_LOGIN_RED);
			} else {
				System.out.println(req.getUserPrincipal());
				String usuario = serviciosMantenimientoSeguridad.obtenerUsuarioAutenticado();
				System.out.println("usuario en la sesion " + usuario);
				if (usuario == null) {
					chain.doFilter(request, response);
				} else {
					
					System.out.println(req.getRequestURI());
					if (!req.getRequestURI().equals(req.getContextPath() + IGuardiaUsuarioSession.PAGINA_PRINCIPAL)) {

						if (!(validaUsuarioOpcion(req.getRequestURI(), usuario, req.getContextPath()))) {

							res.sendRedirect(req.getContextPath() + IGuardiaUsuarioSession.PAGINA_NO_PERMITIDO);

						} else {

							chain.doFilter(request, response);

						}
					} else {
						chain.doFilter(request, response);

					}
				}
			}
		}
	}

	// public void goInicio() throws Exception {
	// FacesContext ctx = FacesContext.getCurrentInstance();
	// ExternalContext extContext = ctx.getExternalContext();
	// String url =
	// extContext.encodeActionURL(ctx.getApplication().getViewHandler()
	// .getActionURL(ctx, IGuardiaUsuarioSession.PAGINA_PRINCIPAL_RED));
	// extContext.redirect(url);
	// }

	public boolean validaUsuarioOpcion(String url, String usuario, String contextPath) {

		return serviciosMantenimientoSeguridad.validarOpcionUsuario(url, usuario, contextPath);

		// List<PriOpcione> listaOpciones =
		// serviciosMantenimientoSeguridad.obtenerOpcionesPorUsuario(usuario);
		// for (PriOpcione cfOpciones : listaOpciones) {
		// if ((contextPath + cfOpciones.getAccion()).equals(url)) {
		// return true;
		// }
		// }
		//
		// return false;
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("Inicio de filtro");
	}

}
