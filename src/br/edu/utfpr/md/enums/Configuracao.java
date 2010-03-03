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
	 * Extensão da página dos horários.
	 */
	EXTENSAO_URL,
	/**
	 * Início da linha que contém um elemento {@code <option />}.
	 */
	OPTION_VALUE,
	/**
	 * Expressão regular para extrair o valor do elemento {@code <option />}.
	 */
	REGEXP_OPTION,
	/**
	 * Expressão regular para extrair a tabela de horários.
	 */
	REGEXP_TABLE,
	/**
	 * Tempo de vida do cache em segundos.
	 */
	SEGUNDOS_CACHE,
	/**
	 * URL que contém tanto a lista de turmas quanto os horários.
	 */
	URL_BASE,
	/**
	 * Nome do arquivo que contém a lista de turmas.
	 */
	ARQUIVO_TURMAS;
}
