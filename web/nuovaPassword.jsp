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
        <link type="text/css" rel="stylesheet" href="css/recuperoPassword.css"  media="screen,projection"/>

        <title>JSP Page</title>
    </head>
    <%!
        boolean privacy = false;
        Cookie[] cookies ;
        Boolean sess = false;
        Cookie cookie;
        int cookiePos=-1;
    %>
    <%
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
            if(cookies[cookiePos].getValue().compareTo("true")!=0)
            {
                out.println("<div class=\"divCookies\">Informazione importante sui cookie. Utilizzando questo sito acconsenti all'uso dei cookie in conformità alla nostra <a href=\"cookies.jsp\">Politica sui cookies</a>. <span class=\"btnCookies btn\">Accetto</span></div>");
            }
        %>
        <nav>
            <div class="nav-wrapper">
                <a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
                <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                
                <ul class="right hide-on-med-and-down">
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">  
                    <li><i class="material-icons right"></i>Sign in/out</li>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
            </div>
        </nav>
        <section>
            <p>Cambio password</p>
            <div id="form">
                <form class="form">
                    <div class="row">
                        <div class="input-field col s12 m5 center">
                            <input id="pass" type="password" class="validate">
                            <label for="pass">nuova password</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 m5 center">
                            <input id="npass" type="password" class="validate">
                            <label for="npass">conferma nuova password</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="btn" id="btn_send">Conferma cambio password</div>
                    </div>
                </form>
            </div>
        </section>

        <main></main>
        <footer class="page-footer">
            <p>&copy; 2016 Cineland - via alla Moia 30 Rovereto (TN) - Tel. 0464 123123 - P.Iva 1234567890 &nbsp;|&nbsp; <a href="privacy.jsp">Privacy</a> &nbsp;&nbsp; <a href="cookies.jsp">Informativa cookies</a></p>
        </footer>
    </body>
    <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
    <script type="text/javascript" src="js/nuovaPassword.js"></script>
</html>


