var prefs = new gadgets.Prefs();
var msg = new gadgets.MiniMessage();
var abas = new gadgets.TabSet();
var msgEscolherTurma;

var utfpr = {
	SERVLET_TURMA: "http://gadgets-utfpr.appspot.com/turmas",
	SERVLET_HORARIO: "http://gadgets-utfpr.appspot.com/horario",
	
	// Cria as linhas da tabela com as turmas escolhidas
	atualizarTabelaTurmas: function() {
		// Pega as turmas salvas pelo usuário
		var turmas = prefs.getString("turmas").split(",");
		var totalTurmas = 0;
		var linhas = "";
		
		linhas = "<thead><th colspan=\"2\">" + prefs.getMsg("minhasTurmas") + "</th></thead>";
		for (var i = 0; i < turmas.length; i++) {
			if (turmas[i] != "" && turmas[i] != ",") {
				linhas += "<tr><td>" + turmas[i] + "</td><td><button onclick=\"utfpr.removerTurma('" + turmas[i] + "')\" title='" + prefs.getMsg("removerTurma") + "'>X</button></td></tr>";
				totalTurmas++;
			}
		}
		
		if (totalTurmas == 0) {
			msgEscolherTurma = msg.createStaticMessage(prefs.getMsg("escolherTurma"));
		} else {
			msg.dismissMessage(msgEscolherTurma);
		}
		
		$("#tabelaTurmas").html(linhas);
	},
	
	listarTurmas: function() {
		var url = utfpr.SERVLET_TURMA + "?turmas=" + prefs.getString("turmas");
		var parametros = {};  
		parametros[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.TEXT;
		gadgets.io.makeRequest(url, this._listarTurmas, parametros);
	},
	
	_listarTurmas: function(retorno) {
		if (retorno.errors != null && retorno.errors != "") {
			msg.createDismissibleMessage(prefs.getMsg("erroListarTurmas") + ": " + retorno.errors + "<br />" + prefs.getMsg("tenteMaisTarde"));
			$("#controles").hide();
			gadgets.window.adjustHeight();
			return;
		}
		
		$("#selectTurmas").html(retorno.text);
		gadgets.window.adjustHeight();
	},
	
	criarAbaTurma: function(turma, foco) {
		var url = utfpr.SERVLET_HORARIO + "?turma=" + turma;
		gadgets.io.makeRequest(url, _IG_Callback(this._criarAbaTurma, turma, foco));
	},
	
	_criarAbaTurma: function(retorno, turma, foco) {
		if (retorno.erros != null && retorno.errors != "") {
			msg.createDismissibleMessage(prefs.getMsg("erroRecuperarHorario") + " " + turma + ": " + retorno.errors + "<br />" + prefs.getMsg("tenteMaisTarde"));
			gadgets.window.adjustHeight();
			return;
		}
		
		$("#horarios").html("<div id=\"turma-" + turma + "\" class=\"horario\">" + retorno.text + "</div>");
		abas.addTab(turma, "turma-" + turma, function() { gadgets.window.adjustHeight() });
		if (foco) {
			abas.setSelectedTab(abas.getTabs().length - 1);
		}
		$("#ajaxLoader").hide();
	},
	
	// Cria as abas das turmas salvas pelo usuário
	atualizarAbas: function() {
		abas.addTab("Turmas", "abaTurmas", function() { gadgets.window.adjustHeight() });
		var turmas = prefs.getString("turmas").split(",");
		for (var i = 0; i < turmas.length; i++) {
			if (turmas[i] != "" && turmas[i] != ",") {
				this.criarAbaTurma(turmas[i], false);
			}
		}
	},
	
	removerTurma: function(turma) {
		if (!confirm(prefs.getMsg("confirmacaoRemoverTurma"))) {
			return;
		}
		
		// Salva a lista de turmas do usuário
		if (prefs.getString("turmas").indexOf(",") != -1) {
			prefs.set("turmas", prefs.getString("turmas").replace(turma + ",", "").replace("," + turma, ""));
		} else {
			prefs.set("turmas", prefs.getString("turmas").replace(turma, ""));
		}
		
		// Cria um elemento '<option />' com a turma removida
		$("#turmas").prepend("<option value=\"" + turma + "\">" + turma + "</option>");
		$("#turmas").val(turma);
		
		this.excluirAbaTurma(turma);
		this.atualizarTabelaTurmas();
		gadgets.window.adjustHeight();
	},
	
	adicionarTurma: function() {
		$("#ajaxLoader").show();
		var turma = $("#turmas").val();
		// Salva a lista de turmas do usuário
		prefs.set("turmas", prefs.getString("turmas") + "," + turma);
		$("#turmas :selected").remove();
		this.criarAbaTurma(turma, true);
		this.atualizarTabelaTurmas();
	},
	
	excluirAbaTurma: function(turma) {
		for (var i = 0; i < abas.getTabs().length; i++) {
			if (abas.getTabs()[i].getName() == turma) {
				abas.removeTab(i);
			}
		}
	}
}

// Método chamado no carregamento do gadget
function iniciar() {
	if (prefs.getString("turmas") != "") {
		utfpr.atualizarTabelaTurmas();
	} else {
		msgEscolherTurma = msg.createStaticMessage(prefs.getMsg("escolherTurma"));
	}
	
	utfpr.listarTurmas();
	utfpr.atualizarAbas();
	
	var ga = new _IG_GA('UA-19177153-3');
	ga.reportPageview('/horario-utfpr-medianeira/' + gadgets.views.getCurrentView().getName().toLowerCase());
}

function _IG_Callback(callbackFunction, parameters) {
	var args = arguments;
	return function() {
		var combinedArgs = Array.prototype.slice.call(arguments);
		callbackFunction.apply(null, combinedArgs.concat(Array.prototype.slice.call(args, 1)))
	}
}