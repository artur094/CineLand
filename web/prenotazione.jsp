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

<%!
    boolean privacy = false;
    Cookie[] cookies ;        
    Utente user;
    Boolean sess = false;
    Spettacolo spett;
    Sala sala;
    
    
%>


<%
    user = (Utente)request.getSession().getAttribute("user");
    if(user == null){ //non è loggato
        sess = false;
    }else{
        sess = true;
        int id_spettacolo = Integer.parseInt(request.getParameter("id"));
        sala = new Sala(id_spettacolo);
        spett = new Spettacolo(id_spettacolo);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import index.css-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/prenotazione.css"/>
        <link type="text/css" rel="stylesheet" href="css/seats.css"/>
    </head>
    <body>
        <nav>
            <div class="nav-wrapper">
                <a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
                <a href="index.jsp" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                
                <ul class="right hide-on-med-and-down">
                    <%
                        if(sess){
                            if(user.getRuolo().equals("admin")){
                                out.println("<li id=\"logout\"><div><a class='dropdown-button btn' href='#' data-activates='user'>"+user.getNome()+"</a>"
                                    +"<ul id='user' class='dropdown-content'>"
                                    +"<li><a href=\"amministrazione.jsp\">Pannello</a></li>"
                                    +"<li class=\"divider\"></li>"
                                    +"<li><a id=\"btn_logout\">Log out</a></li>"
                                    +"</ul></div></li>");    
                            }else{
                                out.println("<li id=\"logout\"><div><a class='dropdown-button btn' href='#' data-activates='user'>"+user.getNome()+"</a>"
                                    +"<ul id='user' class='dropdown-content'>"
                                    +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                    +"<li><a href=\"profilo.jsp\">Profilo</a></li>"
                                    +"<li class=\"divider\"></li>"
                                    +"<li><a id=\"btn_logout\">Log out</a></li>"
                                    +"</ul></div></li>");
                            }
                           
                        }else{
                            String redirectURL = "sess_error.jsp";
                            response.sendRedirect(redirectURL);
                        }
                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">
                    <%
                        if(sess){
                            if(user.getRuolo().equals("admin")){
                                out.println("<li id=\"logout\"><a class='center' href='#'>"+user.getNome()+"</a></li>"
                                    +"<li><a href=\"amministrazione.jsp\">Pannello</a></li>"
                                    +"<li class=\"divider\"></li>"
                                    +"<li><a id=\"side_btn_logout\">Log out</a></li>");
                            }else{
                                out.println("<li id=\"logout\"><a class='center' href='#'>"+user.getNome()+"</a></li>"
                                    +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                    +"<li><a href=\"profilo.jsp\">Profilo</a></li>"
                                    +"<li class=\"divider\"></li>"
                                    +"<li><a id=\"side_btn_logout\">Log out</a></li>");
                                        }
                           
                        }else{
                            out.println("<li id=\"login\"><a class=\" modal-trigger btn\" data-target=\"form\">Sign in</a></li>");
                        }
                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
            </div>
        </nav>
        <div id="list_con" class="container">
            <div class="row">
                <div class="col s12 m3">
                    <div class="card-image">
                        <%out.println("<img src='img/locandine/" + spett.getFilm().getTitolo().replaceAll("\\s+","") + ".jpg' alt='"+ spett.getFilm().getTitolo() +"'/>");%>
                    </div>
                </div>
                <div class="col s12 m9">
                    <div class="row">
                        <!--<div class="col s12 m2"><p>Film: <%out.println(spett.getFilm().getTitolo());%></p></div>-->
                        <div class="col s12 m2"><p>Tickets: <span id="counter">0</span></div>
                        <div class="col s12 m7"><p>Time: <span id="now"></span></p></div>
                    </div>
                    <div class=" row">
                        <div class="col s12">
                            <p>Seat:</p>
                            <ul id="selected-seats"></ul>
                        </div>
                    </div>
                    <div class="row">
                        <ul class="riduzioni">
                            <li class="col s2">
                                <div class="campo col offset-m1">Studenti: </div>
                                <div class="input-field col">
                                    <select class="selStudenti">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                            <li class="row col s2">
                                <div class="campo col offset-m1">Militari: </div>
                                <div class="input-field col">
                                    <select class="selMilitari">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                            <li class="row col s2">
                                <div class="campo col offset-m1">Anziani: </div>
                                <div class="input-field col">
                                    <select class="selAnziani">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                            <li class="row col s2">
                                <div class="campo col offset-m1">Disabili: </div>
                                <div class="input-field col">
                                    <select class="selDisabili">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col check_ridotti">
                    <input type="checkbox" id="ridotti" />
                    <label for="ridotti">Seleziona ridotti</label>
                </div>    
            </div>
            <div id="btn_paga">
                <a class='dropdown-button btn'>Paga:<b>$<span id="total">0</span></b></a>
            </div>

        </div>
                
        

            
        
        <div class="demo container">
   		<div id="seat-map">
			<div class="front">SCREEN</div>					
		</div>
		<div class="booking-details">
			<div id="legend"></div>
		</div>
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
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
    <script type="text/javascript" src="js/prenotazione.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script> 
    <script type="text/javascript" src="js/seats_map.js"></script> 

</html>