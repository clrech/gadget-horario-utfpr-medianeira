package br.edu.utfpr.md.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

import br.edu.utfpr.md.enums.Configuracao;
import br.edu.utfpr.md.utils.Messages;

/**
 * Servlet para recuperar a lista de turmas da UTFPR Campus Medianeira da
 * página. Após recuperada, a lista é armazenada em cache.
 * 
 * @author Cleber
 * @see Configuracao#SEGUNDOS_CACHE
 */
@SuppressWarnings("serial")
public class TurmaServlet extends HttpServlet {
	/**
	 * Nome do cache que guarda a lista de turmas.
	 */
	private static final String CACHE_TURMAS = "turmas"; //$NON-NLS-1$
	/**
	 * Nome do parâmetro passado para o servlet com as turmas (separadas por
	 * vírgula) que não serão retornadas.
	 */
	private static final String P_TURMAS = "turmas"; //$NON-NLS-1$
	private Cache cache;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			// Cria o cache
			Map<String, Integer> props = new HashMap<String, Integer>();
			props.put(GCacheFactory.EXPIRATION_DELTA, Integer.valueOf(Messages
					.getString(Configuracao.SEGUNDOS_CACHE)));
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
		List<String> turmasParametro = new ArrayList<String>();

		if (req.getParameter(P_TURMAS) != null) {
			for (String turma : req.getParameter(P_TURMAS).split(",")) {
				turmasParametro.add(turma);
			}
		}

		if (!cache.containsKey(CACHE_TURMAS)) //$NON-NLS-1$
			carregarTurmas();

		ArrayList<String> todasTurmas = (ArrayList<String>) cache
				.get(CACHE_TURMAS);
		todasTurmas.removeAll(turmasParametro);

		resp.getWriter().print("<select id=\"turmas\">");
		for (String turma : todasTurmas) {
			resp.getWriter().print(
					"<option value=\"" + turma + "\">" + turma + "</option>");
		}
		resp.getWriter().print("</select>");
	}

	/**
	 * Coloca a lista de turmas no cache.
	 */
	@SuppressWarnings("unchecked")
	public void carregarTurmas() {
		List<String> turmas = new ArrayList<String>();
		Pattern padrao = Pattern.compile(Messages
				.getString(Configuracao.REGEXP_OPTION));

		try {
			URL url = new URL(Messages.getString(Configuracao.URL_BASE)
					+ Messages.getString(Configuracao.ARQUIVO_TURMAS));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String linha;
			Matcher matcher;

			while ((linha = reader.readLine()) != null) {
				if (linha != null
						&& linha.startsWith(Messages
								.getString(Configuracao.OPTION_VALUE))) {
					matcher = padrao.matcher(linha);
					if (matcher.find()) {
						turmas.add(matcher.group(2));
					}
				}
			}

			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!turmas.isEmpty()) {
			Collections.sort(turmas);
			cache.put(CACHE_TURMAS, turmas);
		}
	}
}
