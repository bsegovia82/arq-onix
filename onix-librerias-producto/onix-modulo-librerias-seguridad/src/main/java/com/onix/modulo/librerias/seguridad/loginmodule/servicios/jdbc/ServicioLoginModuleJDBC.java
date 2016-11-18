package com.onix.modulo.librerias.seguridad.loginmodule.servicios.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.security.jacc.PolicyContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.onix.modulo.librerias.generales.GestorQuerysJDBC;
import com.onix.modulo.librerias.generales.excepciones.ExcepcionLogin;
import com.onix.modulo.librerias.seguridad.loginmodule.servicios.IServiciosLoginModule;

public class ServicioLoginModuleJDBC implements IServiciosLoginModule {
	private DataSource dataSource;

	public ServicioLoginModuleJDBC() {
		try {
			// Debe estar en una clase padre
			HttpServletRequest request = (HttpServletRequest) PolicyContext
					.getContext("javax.servlet.http.HttpServletRequest");
			String nombreDS = request.getServletContext().getInitParameter(
					"DATA_SOURCE_JAAS");
			Context contexto = new InitialContext();
			dataSource = (DataSource) contexto.lookup(nombreDS);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	
	
	//la misma conexion es la que se debe utilizar para el query y la tabla de querys
	private boolean validarUsuarioJDBC(Connection conn, String pUsuario,
			String pPassword) throws Throwable {
		
		
		
		
				
//				" select f.USUARIO, f.clave " + " from cls_usuario f "
//				+ " where "
////				+ "f.estado = 'A' " + " and "
//						+ " upper(f.usuario) = upper(?)";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		try {
			String query = GestorQuerysJDBC.obtenerQueryNombre("AUTENTICACION", conn);
			sentencia = conn.prepareStatement(query);
			sentencia.setString(1, pUsuario);
			resultado = sentencia.executeQuery();
			if (resultado.next()) 
			{
				String clave = resultado.getString(2);
				if (clave.equals(pPassword)) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} finally {
			if (resultado != null) {
				resultado.close();
			}
			if (sentencia != null) {
				sentencia.close();
			}

			if (conn != null) {
				conn.close();
			}
		}

	}


	private String ejecutarSentenciaClave(Connection conn, String usuario)
			throws Throwable {

//		String query = "select f.clave from cls_usuario f where "
////				+ "f.estado = 'A' and "
//				+ " upper(f.usuario) = upper(?)";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		try {
			String query = GestorQuerysJDBC.obtenerQueryNombre("AUTENTICACION", conn);
			sentencia = conn.prepareStatement(query);
			sentencia.setString(1, usuario);
			resultado = sentencia.executeQuery();
			if (resultado.next()) {
				return resultado.getString(1);
			} else {
				System.out
						.println("No encontro el usuario en la base de datos");
				return null;
			}
		} finally {
			if (resultado != null) {
				resultado.close();
			}
			if (sentencia != null) {
				sentencia.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
	}

	public String getClaveUsuario(String pUsuario) {
		try {
			return ejecutarSentenciaClave(dataSource.getConnection(), pUsuario);
		} catch (Throwable e) {
			System.out
					.println("Error al ejecutar la sentencia de base de datos");
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getNombreRoles(String pUsuario) {
		List<String> listaRoles = new ArrayList<String>();
		listaRoles.add("CONEXION");
		return listaRoles;
	}

	public boolean validarUsuarioPassword(String pUsuario, String pPassword)
			throws ExcepcionLogin {
		try {
			return validarUsuarioJDBC(dataSource.getConnection(), pUsuario,
					pPassword);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

}
