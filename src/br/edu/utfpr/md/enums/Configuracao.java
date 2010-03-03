package br.edu.utfpr.md.enums;

import br.edu.utfpr.md.utils.Messages;

/**
 * Enum com as propriedades presentes no arquivo <code>cfg.properties</code>.
 * 
 * @author Cleber
 * @see {@link Messages#getString(Configuracao)}
 */
public enum Configuracao {
	/**
	 * Extens�o da p�gina dos hor�rios.
	 */
	EXTENSAO_URL,
	/**
	 * In�cio da linha que cont�m um elemento {@code <option />}.
	 */
	OPTION_VALUE,
	/**
	 * Express�o regular para extrair o valor do elemento {@code <option />}.
	 */
	REGEXP_OPTION,
	/**
	 * Express�o regular para extrair a tabela de hor�rios.
	 */
	REGEXP_TABLE,
	/**
	 * Tempo de vida do cache em segundos.
	 */
	SEGUNDOS_CACHE,
	/**
	 * URL que cont�m tanto a lista de turmas quanto os hor�rios.
	 */
	URL_BASE,
	/**
	 * Nome do arquivo que cont�m a lista de turmas.
	 */
	ARQUIVO_TURMAS;
}
