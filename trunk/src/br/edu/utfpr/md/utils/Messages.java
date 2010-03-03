package br.edu.utfpr.md.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import br.edu.utfpr.md.enums.Configuracao;

/**
 * Classe para pegar as propriedades presentes no arquivo
 * <code>cfg.properties</code>.
 * 
 * @author Cleber
 * @see {@link Configuracao}
 */
public class Messages {
	private static final String BUNDLE_NAME = "br.edu.utfpr.md.cfg"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(Configuracao cfg) {
		return getString(cfg.name());
	}
}
