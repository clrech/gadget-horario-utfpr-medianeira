

# Usuário #

## Em quais recipientes este gadget pode ser usado? ##
Ele foi testado no [IGoogle](http://www.google.com/ig?hl=pt_BR) ([clique aqui para adicionar](http://www.google.com/ig/adde?moduleurl=gadgets-utfpr.appspot.com/gadget.xml&hl=pt-BR)) e no Gmail.

## Como eu adiciono o gadget no Gmail? ##
  * Vá até o [Gmail Labs](https://mail.google.com/mail/?shva=1#settings/labs).
  * Habilite o **Adicionar qualquer gadget pelo URL** (Add any gadget by URL).
  * Vá até a [aba de Gadgets](https://mail.google.com/mail/?shva=1#settings/gadgets).
  * Adicione a URL **http://gadgets-utfpr.appspot.com/gadget.xml**
  * Pronto! Clicando no título do gadget você pode maximizá-lo.

## Os horários são atualizados em tempo real? ##
**Não**. Eles são armazenados por uma hora no servidor do gadget. Por tanto, qualquer alteração nos horários de aulas pode levar até uma hora para que seja atualizado no gadget. Você pode verificar o **[horário atualizado das aulas](http://www.md.utfpr.edu.br/Professores2/hor12010/turmas/turmas.htm)** no site da UTFPR Campus Medianeira.

## A UTFPR Campus Medianeira endossa este gadget? ##
Não. A UTFPR Campus Medianeira não possui nenhuma ligação com este gagdet. Ele foi desenvolvido por um aluno da universidade.

## Quais navegadores são suportados? ##
Este gadget foi testado nos navegadores Firefox 3.6, Google Chrome 4.1 (beta) e Windows Internet Explorer 8.

# Desenvolvedores #

## Como faço para contribuir com o gadget? ##
Você pode entrar em contato [aqui](http://cleber.net.br/contato) ou [aqui](http://www.google.com.br/profiles/clrech/contactme?continue=http%3A%2F%2Fwww.google.com.br%2Fprofiles%2Fclrech). O seu e-mail será adicionado a lista de [membros do projeto](http://code.google.com/p/gadget-horario-utfpr-medianeira/people/list) e também na lista de desenvolvedores da aplicação no [Google App Engine](https://appengine.google.com/).

## Como o gadget funciona? ##
Todos os arquivos acessados pelos usuários do gadget estão hospedados em uma aplicação Java no [Google App Engine](https://appengine.google.com/) sob o domínio http://gadgets-utfpr.appspot.com/. Esta aplicação também possui os servlets responsáveis por recuperar a lista de turmas e o horário de uma turma.

## Por onde eu começo ##
  * Estude a [API de gadgets](http://code.google.com/intl/pt-BR/apis/gadgets/) do Google e/ou o [Google App Engine](https://appengine.google.com/).
  * É desejável conhecimento em:
    * XML
    * HTML
    * JavaScript
    * CSS
    * Java
  * Baixe o [Eclipse IDE](http://www.eclipse.org/downloads/).
  * Instale os plugins do [Google](http://code.google.com/intl/pt-BR/eclipse/) e do [SVN](http://subclipse.tigris.org/servlets/ProjectProcess?pageID=p4wYuA).
  * Faça o check-out do código em https://gadget-horario-utfpr-medianeira.googlecode.com/svn/trunk (o código fonte possui 40 MB, pois estão incluídas as bibliotecas junto).
  * Altere e código e faça o commit.
  * Faça o [deploy](http://code.google.com/intl/pt-BR/eclipse/docs/appengine_deploy.html) do código no Google App Engine utilizando o plugin do Google para o Eclipse.