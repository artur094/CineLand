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
        <link type="text/css" rel="stylesheet" href="css/privacy.css"  media="screen,projection"/>

        <title>Privacy</title>
    </head>
    <%!
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
            if(cookies[cookiePos].getValue().compareTo("true")!=0)
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
            <p>
Informativa Decreto legislativo 30 giugno 2003, n. 196
<br>Tutela della PrivacyGentile Signore/a:<br>
Ai sensi dell'articolo 13 del D.lgs. n.196/2003, La informiamo che:<br>
I dati personali da Lei comunicati sono trattati per l'invio di informazioni sui servizi del CINELAND, con modalità anche automatizzate e strettamente necessarie a tale scopo.<br>
Il conferimento dei dati è facoltativo: tuttavia in loro mancanza non sarà possibile inviare alcuna informazione. <br>
I dati non saranno comunicati ad altri soggetti, né saranno oggetto di diffusione.
<br>Titolare del trattamento è<br>
CINELAND<br>
Via alla Moia, 30<br>
ROVERETO (TN)<br>
P.Iva 1234567890<br>
REA: TN-121165<br>
Tel. 0464 123123<br>
E-mail: info@cineland.it<br><br>
Lei potrà esercitare i diritti di cui all'articolo 7 del d.lgs. n. 196/2003 (accesso, correzione, cancellazione, opposizione al trattamento, ecc.) dandocene comuniazione tramite e-mail.
            </p>
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
                                <a href="recuperoPassword.jsp">Hai dimenticato la password?</a>
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

