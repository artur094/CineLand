<%@page import="ClassiDB.Utente"%>
<%@page language="Java" contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import index.css-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/cookies.css"  media="screen,projection"/>

        <title>Informativa cookie</title>
    </head>
    <%
        boolean privacy = false;
        Cookie[] cookies ;        
        Utente user;
        Boolean sess = false;
        Cookie cookie;
        int cookiePos=-1;
    %>
    <% 
        cookiePos = 0;
        user = (Utente)request.getSession().getAttribute("user");
        cookies = request.getCookies();
        if(cookies != null)
        {
            for(int i = 0; i < cookies.length; i++)
            {
                cookie = cookies[i];
                if(cookies[i].getName().compareTo("accettoCookies")==0)
                {
                    cookie = cookies[i];
                    cookiePos=i;
                }
            }
        }
    %> 
    <body>
        <%
        if(cookies != null)
            {
                if(cookies[cookiePos].getValue().compareTo("true")!=0)
                {
                    out.println("<div class=\"divCookies\">Informazione importante sui cookie. Utilizzando questo sito acconsenti all'uso dei cookie in conformità alla nostra <a href=\"cookies.jsp\">Politica sui cookies</a>. <span class=\"btnCookies btn\">Accetto</span></div>");
                }
            }
            else
            {
                out.println("<div class=\"divCookies\">Informazione importante sui cookie. Utilizzando questo sito acconsenti all'uso dei cookie in conformità alla nostra <a href=\"cookies.jsp\">Politica sui cookies</a>. <span class=\"btnCookies btn\">Accetto</span></div>");
            }
        %>
        <nav>
        <div class="nav-wrapper">
            <a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
            <a href="index.jsp" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
            <ul class="right hide-on-med-and-down">
                <%
                    if(user == null){
                           out.println("<li id=\"login\"><a class=\"waves-effect waves-light modal-trigger btn\" data-target=\"form\">Sign in</a></li>");
                    }else{
                        if(user.getRuolo().equals("admin")){
                            out.println("<li id=\"logout\"><div><a class='dropdown-button btn' href='#' data-activates='user'>"+user.getNome()+"</a>"
                                +"<ul id='user' class='dropdown-content'>"
                                +"<li><a href=\"profilo.jsp\">Profilo</a></li>"
                                +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                +"<li><a href=\"amministrazione.jsp\">Pannello</a></li>"
                                +"<li class=\"divider\"></li>"
                                +"<li><a id=\"btn_logout\">Log out</a></li>"
                                +"</ul></div></li>");    
                        }else{
                            out.println("<li id=\"logout\"><div><a class='dropdown-button btn' href='#' data-activates='user'>"+user.getNome()+"</a>"
                                +"<ul id='user' class='dropdown-content'>"
                                +"<li><a href=\"profilo.jsp\">Profilo</a></li>"
                                +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                +"<li class=\"divider\"></li>"
                                +"<li><a id=\"btn_logout\">Log out</a></li>"
                                +"</ul></div></li>");                     
                        }
                    }
                %>
                <li><a href="index.jsp">Film</a></li>
                <li><a href="aboutus.jsp">About us</a></li>
            </ul>
            <ul class="side-nav" id="mobile-demo">
                <li><a href="index.jsp">Film</a></li>
                <li><a href="aboutus.jsp">About us</a></li>
                <%
                    if(user!=null){
                        if(user.getRuolo().equals("admin")){
                            out.println("<li><a class=\"center\" href=\"profilo.jsp\">"+user.getNome()+"</a></li>"
                                +"<li class=\"divider\"></li>"
                                +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                +"<li><a href=\"amministrazione.jsp\">Amministrazione</a></li>"
                                +"<li><a id=\"side_btn_logout\">Log out</a></li>");
                        }else{
                            out.println("<li><a class=\"center\"href=\"profilo.jsp\">"+user.getNome()+"</a></li>"
                                +"<li class=\"divider\"></li>"
                                +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                +"<li><a id=\"side_btn_logout\">Log out</a></li>");
                        }

                    }else{
                        out.println("<li id=\"login\"><a class=\" modal-trigger\" data-target=\"form\">Sign in</a></li>");
                    }
                %>
            </ul>
        </div>
    </nav>
        <main>
            <h3>TUTELA DELLA PRIVACY </h3>
            <div>
                <div><br>
                    <h4>INFORMATIVA ESTESA COOKIE</h4>
                    <br>
                    <br>
                    <h5> CHE COSA SONO I COOKIES? </h5>
                    <p>Un “cookie” è un piccolo file di testo che i siti Web/Applicazioni inviano al computer o a un altro dispositivo connesso a Internet del visitatore per identificare univocamente il browser del visitatore stesso, per salvare informazioni, impostazioni e personalizzazioni nel browser.</p>
                    <h4><br>
                    COOKIE PER CUI È RICHIESTO IL CONSENSO
                    </h4>
                    Tutti i cookie diversi da quelli tecnici vengono installati o attivati solo a seguito del&nbsp;<strong>consenso espresso</strong>&nbsp;dall’utente la prima volta che visita il sito. Il consenso può essere espresso in maniera generale, interagendo con il banner di informativa breve presente sulla pagina di approdo del sito, secondo le modalità indicate in esso (chiudendo il banner, o cliccando sul tasto ACCETTA); oppure può essere fornito in maniera selettiva, secondo le modalità di seguito indicate. Di questo consenso viene tenuta traccia in occasione delle visite successive. <strong>L’utente ha sempre la possibilità di revocare in tutto o in parte il consenso già espresso.</strong><br>
                    <br>
                    <br>
                    <h4> COOKIE UTILIZZATI DA QUESTO SITO</h4>
                    <br>
                    <strong>Cookies tecnici&nbsp;</strong><br>
                                 Utilizziamo solamente cookies tecnici anonimi indispensabili per il corretto funzionamento del sito, per la gestione della sessione, per le funzionalità interne del nostro sistema o per la personalizzazione di alcune parti del sito.<br>
                                 Questo tipo di cookie non richiede il consenso dell'utente in quanto non raccoglie dati sensibili.<br>
                    <br>
                    <strong>Cookies di profilazione generati dal sito</strong><br>
                                 Questo tipo di cookie &egrave; necessario per la gestione del profilo<br>
                    <br>
                    <strong>Cookies di terze parti </strong><br>
                                 I cookie di terze parti sono impostati da un sito web diverso da questo Sito e risiedono su server diversi.  Questi cookie non vengono controllati direttamente da questo Sito. <br>
                                 Di seguito i servizi (di terze parti) che possono  generare cookies:<br>
                    <br>
                    <table width="100%" border="1" cellspacing="0" cellpadding="3" bordercolor="#999999">
                      <tbody><tr>
                        <td>GOOGLE MAPS </td>
                        <td>Google Maps è un servizio di visualizzazione di mappe gestito da Google Inc. che permette a questo Sito di indicare il luogo dove si trova l'azienda.&nbsp;<br>
                          &nbsp;<a href="http://www.google.it/intl/it/policies/privacy/" target="_blank">Privacy Policy - Cookie</a></td>
                      </tr>
                    </tbody></table>
                    <p>&nbsp; </p>
                    <h4>GESTIONE DEI COOKIES</h4>
                    <br>
                                 I cookies possono essere eliminati, modificati e disattivati direttamente dal browser.<br>
                                 Il visitatore ha il pieno controllo dei cookies e sul loro utilizzo.<br>
                                 Disabilitando i cookies è possibile che alcune parti/servizi di questo Sito non siano correttamente visualizzati.<br>
                    <br>
                                 Qui di seguito le guide dei browser più usati per conoscere le varie modalità di gestione dei cookies:
                    <p></p>
                    <ul>
                      <li><a title="guida cookies - chrome" target="_blank" href="http://support.google.com/chrome/bin/answer.py?hl=en&amp;answer=95647">Chrome</a></li>
                      <li><a title="guida cookies - firefox" target="_blank" href="http://support.mozilla.org/en-US/kb/cookies-information-websites-store-on-your-computer?redirectlocale=en-US&amp;redirectslug=Cookies">Firefox</a></li>
                      <li><a title="guida cookies - safari" target="_blank" href="http://support.apple.com/kb/HT1677">Safari</a></li>
                      <li><a title="guida cookies - internet explorer" target="_blank" href="http://support.microsoft.com/kb/196955">Internet Explorer</a></li>
                    </ul>
                    <p><strong><br>
                      Ricordiamo inoltre all’utente che molti browser permettono la navigazione in incognito.<br>
                      </strong><br>
                      Ulteriori informazione sui cookie possono essere reperite nei seguenti siti:</p>
                    <ul>
                      <li><a href="http://it.wikipedia.org/wiki/Cookie" target="_blank">Cookie (Wikipedia)</a>&nbsp;&nbsp;</li>
                      <li><a href="http://www.allaboutcookies.org/" target="_blank">www.allaboutcookies.org</a>&nbsp;&nbsp;</li>
                      <li><a href="http://www.youronlinechoices.eu/" target="_blank">www.youronlinechoices.eu</a></li>
                    </ul><br><br>
                </div>

            </div>
        </main>
        <footer class="page-footer">
            <p>&copy; 2016 Cineland - via alla Moia 30 Rovereto (TN) - Tel. 0464 123123 - P.Iva 1234567890 &nbsp;|&nbsp; <a href="privacy.jsp">Privacy</a> &nbsp;&nbsp; <a href="cookies.jsp">Informativa cookies</a></p>
        </footer>
        <div id="form" class="modal">
            <div class="modal-content">
                <div class="">
                    <ul class="tabs">
                        <li class="tab col s6"><a href="#in">Sign IN</a></li>
                        <li class="tab col s6"><a href="#up">Sign UP</a></li>
                    </ul>
                </div>
                <div id="in" class="">
                    <form class="formlogin">
                        <div class="row">
                          <div class="input-field col s12 center">
                                <input id="email_lgn" type="email" class="validate">
                                <label for="email">Email</label>
                          </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12 center">
                                <input id="password_lgn" type="password" class="validate">
                                <label for="password">Password</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 center ">
                                <div class="btn" id="btn_login">Log in</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 center ">
                                <a>Hai dimenticato la password?</a>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="up" class="col s12">
                    <div class="row">
                        <form class="formregistra">
                            <div class="row">
                                <div class="input-field col s12 center">
                                    <input id="first_name" type="text" class="validate">
                                    <label for="first_name">First Name</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12 center">
                                    <input id="email_sgnup" type="email" class="validate">
                                    <label for="email">Email</label>
                                </div>
                            </div>
                                <div class="row">
                                    <div class="input-field col s12 center">
                                      <input id="password_sgnup" type="password" class="validate">
                                      <label for="password">Password</label>
                                    </div>
                                </div>
                            <div class="row">
                                <div class="col s12 center">
                                    <div class="btn" id="btn_signup">Sign up</div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
</html>