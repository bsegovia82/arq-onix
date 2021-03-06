package com.onix.modulo.librerias.notificacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class NotificadorMail {

	protected abstract Session getSession();

	public void enviarEmailTextoSimple(String pDireccionesDestino, String pAsunto, String pMensaje) throws Throwable {

		Message message = new MimeMessage(getSession());
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pDireccionesDestino));
		message.setSubject(pAsunto);
		message.setText(pMensaje);
		Transport.send(message);

	}

	public HashMap<String, List<String>> enviarEmailTextoMultiple(List<String> pDireccionesDestino, String pAsunto,
			String pMensaje) {
		HashMap<String, List<String>> lMapaResultante = new HashMap<>();
		List<String> lListaEmailsEnviados = new ArrayList<>();
		List<String> lListaEmailsFallidos = new ArrayList<>();
		for (String lDestino : pDireccionesDestino) {
			try {
				enviarEmailTextoSimple(lDestino, pAsunto, pMensaje);
				lListaEmailsEnviados.add(lDestino);
			} catch (Throwable lError) {
				lListaEmailsFallidos.add(lDestino);
			}
		}
		lMapaResultante.put("ENVIADOS", lListaEmailsEnviados);
		lMapaResultante.put("FALLIDOS", lListaEmailsFallidos);
		return lMapaResultante;
	}

}
