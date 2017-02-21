package com.onix.microservicios.api.vista;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomizadorContenedor implements EmbeddedServletContainerCustomizer {

	@Inject
	private IMimeTypes lMimeTypes;

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);

		HashMap<String, String> lMapaMimes = lMimeTypes.getMimeTypes();

		for (Entry<String, String> lRegistro : lMapaMimes.entrySet()) {
			{
				String lNombreMime = lRegistro.getKey();
				String lValorMime = lRegistro.getValue();
				mappings.add(lNombreMime, lValorMime);
			}
			container.setMimeMappings(mappings);
		}
	}

}
