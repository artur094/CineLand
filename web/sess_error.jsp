<%-- 
    Document   : sess_error
    Created on : 17-dic-2015, 23.31.07
    Author     : Utente
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link type="text/css" rel="stylesheet" href="css/error.css"  media="screen,projection"/>

        <title>JSP Page</title>
    </head>
    <body>
        <nav>
            <div class="nav-wrapper">
                <a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
                <a href="index.jsp" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                
                <ul class="right hide-on-med-and-down">
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="sale.jsp">Le nostre sale</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">  
                    <li><a class=""><i class="material-icons right"></i>Sign in/out</a></li>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="sale.jsp">Le nostre sale</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
            </div>
        </nav>
        <div class="row">
            <li class=" col offset-m2 material-icons error">error_outline</li>
            <p class="m5 flow-text err_text">Purtroppo non sei autenticato, prova a fare <a>Login</a> o se non sei registrato <a>Registrati</a></p>
        </div>
    </body>
</html>
