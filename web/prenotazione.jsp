<%@page import="ClassiDB.Utente"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="ClassiDB.Spettacolo"%>
<%@page import="GestioneClassi.Spettacoli"%>
<%-- 
    Document   : prenotazione
    Created on : 23-lug-2015, 21.35.09
    Author     : Utente
--%>

<%@page import="ClassiDB.Film"%>
<%@page import="ClassiDB.Sala"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="ClassiDB.Posto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    boolean privacy = false;
    Cookie[] cookies ;        
    Utente user;
    Boolean sess = false;
    int id_spettacolo = Integer.parseInt(request.getParameter("id"));
    Sala sala = new Sala(id_spettacolo);
    Spettacolo spett = new Spettacolo(id_spettacolo);
%>


<%
    user = (Utente)request.getSession().getAttribute("user");
    if(user == null){ //non è loggato
        sess = false;
    }else{
        sess = true;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
        <!--Import index.css-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/prenotazione.css"/>
    </head>
    <body>
        <nav>
            <div class="nav-wrapper">
                <a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
                <a href="index.jsp" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                
                <ul class="right hide-on-med-and-down">
                    <%
                        if(sess){
                            out.println("<li id=\"logout\"><div><a class='dropdown-button btn' href='#' data-activates='user'>Io</a>"
                                +"<ul id='user' class='dropdown-content'>"
                                +"<li><a href=\"#!\">Acquisti</a></li>"
                                +"<li class=\"divider\"></li>"
                                +"<li><a href=\"#!\">Profilo</a></li>"
                                +"<li><a id=\"btn_logout\">Log out</a></li>"
                                +"</ul></div></li>");
                            out.println("<li id=\"login\" class=\"off\"><a class=\"waves-effect waves-light modal-trigger btn\" data-target=\"form\"><i class=\"material-icons right\"></i>Sign in</a></li>");
                        }else{
                                String redirectURL = "http://whatever.com/myJSPFile.jsp";
                                response.sendRedirect(redirectURL);
                        }
                    %>
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
        <div id="list_con" class="container">
            <div class="row" id="titolo">
                <h3><%out.println(spett.getFilm().getTitolo());%></h3>
            </div>
            <div>
                <ul>
                    <li class="row">
                        <div class="campo col offset-m1">Posto  : 1A</div>
                        <div class="input-field col">
                            <select>
                              <option value="0" selected>Normale</option>
                              <option value="1">Studente*</option>
                              <option value="2">Militare*</option>
                              <option value="3">Anziano*</option>
                              <option value="4">Disabile</option>
                            </select>
                        </div>
                    </li>
                    <li class="row">
                        <div class="campo col offset-m1">Posto  : 2A</div>
                        <div class="input-field col">
                            <select>
                              <option value="0" selected>Normale</option>
                              <option value="1">Studente</option>
                              <option value="2">Militare</option>
                              <option value="3">Anziano</option>
                              <option value="4">Disabile</option>
                            </select>
                        </div>
                    </li>
                </ul>
                
            </div>
            <div id="btn_paga">
                <a class='dropdown-button btn'>Paga</a>
            </div>
            
        </div>
        
        <div id="map_cont" class="container">
            
        </div>
                <div class="container" id="leggenda">
                <blockquote>
                    *Studente All'entrata sarà richiesta la tessera universitaria<br>
                    *Mlitare All'entrata sarà richiesta la tessera dell'arma di appartenenza<br>
                    *Anziano Vengono considerati in questa categoria tutti coloro >65anni <br>
                </blockquote>
        </div>
        
    </body>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
    <script type="text/javascript" src="js/prenotazione.js"></script>
</html>