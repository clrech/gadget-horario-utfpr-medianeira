package br.edu.utfpr.md.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.utfpr.md.enums.Configuracao;
import br.edu.utfpr.md.utils.Messages;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

/**
 * Servlet para recuperar o horário de uma turma da UTFPR Campus Medianeira.
 * Após recuperado, o horário é armazenado em cache.
 * 
 * @author Cleber
 * @see Configuracao#SEGUNDOS_CACHE
 */
@SuppressWarnings("serial")
public class HorarioServlet extends HttpServlet {
	/**
	 * Nome do cache que guarda os horários.
	 */
	private static final String CACHE_HORARIOS = "horarios"; //$NON-NLS-1$
	/**
	 * Nome do parâmetro passado para o servlet com a turma da qual vai ser
	 * recuperado o horário.
	 */
	private static final String P_TURMA = "turma"; //$NON-NLS-1$
	private Cache cache;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			// Cria o cache
			Map<String, Integer> props = new HashMap<String, Integer>();
			props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
			cache = CacheManager.getInstance().getCacheFactory().createCache(
					props);
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain"); //$NON-NLS-1$

		String turma = req.getParameter(P_TURMA);
		if (turma == null || turma.isEmpty()) {
			resp.getWriter().print(
					Messages.getString("servlet.erroParametroTurma"));
			return;
		}

		if (cache.containsKey(CACHE_HORARIOS)) {
			resp.getWriter().print(cache.get(turma));
		} else {
			String horario = getHorario(turma);
			if (horario.isEmpty()) {
				cache.put(turma, horario);
			}
			resp.getWriter().print(horario);
		}
	}

	/**
	 * Retorna o horário de uma turma.
	 * 
	 * @param turma
	 *            a turma da qual vai ser recuperado o horário.
	 * @return o horário da turma ou uma mensagem de erro.
	 */
	public String getHorario(String turma) {
		StringBuffer texto = new StringBuffer();

		try {
			URL url = new URL(Messages.getString(Configuracao.URL_BASE)
					+ turma.toLowerCase() + "."
					+ Messages.getString(Configuracao.EXTENSAO_URL));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String linha;

			while ((linha = reader.readLine()) != null) {
				if (linha != null) {
					texto.append(linha);
				}
			}

			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return Messages.getString("servlet.erroHorario");
		} catch (IOException e) {
			e.printStackTrace();
			return Messages.getString("servlet.erroHorario");
		}

		Pattern padrao = Pattern.compile(Messages
				.getString(Configuracao.REGEXP_TABLE));
		Matcher matcher = padrao.matcher(texto.toString());
		return (matcher.find() ? matcher.group() : "");
	}
}
