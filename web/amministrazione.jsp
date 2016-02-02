<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="GestioneClassi.Spettacoli"%>
<%@page import="GestioneClassi.Films"%>
<%@page import="ClassiDB.Film"%>
<%@page import="java.util.List"%>
<%@page import="Control.Admin"%>
<%@page import="ClassiDB.Spettacolo"%>
<%@page import="ClassiDB.Sala"%>
<%@page import="ClassiDB.Utente"%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="sess_error.jsp"%>

<!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import local style-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/seats.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/amministrazione.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/acquisti.css"  media="screen,projection"/>

      <!--Let browser know website is optimized for mobile-->
      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <%
        boolean privacy = false;
        Cookie[] cookies ;        
        Utente user;
        List<Utente> topten;
        List<Film> incassi_film;
        List<Film> films;
        List<Spettacolo> spett_per_film;
        Boolean sess = false;
    %>


    <%
        user = (Utente)request.getSession().getAttribute("user");
        if(user == null){ //non è loggato
            throw new RuntimeException();
        }else{
            sess = true;
            Admin admin = new Admin();
            topten = admin.getTopClienti();
            incassi_film = admin.getIncassiFilm();
            films = (Films.getFutureFilms()).getListaFilm();
        }
    %>

    <body>
        <!-- Navigatio Bar -->
        <nav>
            <div class="nav-wrapper">
                <a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
                <a href="index.jsp" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                <ul class="right hide-on-med-and-down">
                    <%
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
                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">
                    <%
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

                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
            </div>
        </nav>
        <div class="tabhead">
            <div class="col s12">
                   <ul class="tabs">
                       <li class="tab col s2"><a href="#tab_programmazione">Spettacoli</a></li>
                       <li class="tab col s2"><a href="#tab_incassi">Incassi film</a></li>
                       <li class="tab col s2 "><a href="#tab_clienti">Top clienti</a></li>
                       <li class="tab col s2"><a href="#tab_piùprenotati">Più prenotati</a></li>
                       <li class="tab col s2"><a href="#tab_prenotazioni">Annulla prenotazione</a></li>
                   </ul>
            </div>
        </div>
        <!--Tab programmazione-->
        <div id="tab_programmazione" class="mtab">
            <div class="row">
                <div class="col s12 m2 lista_spett">
                    <ul>
                        <% 
                            for(int i=0;  i < films.size();i++){
                               spett_per_film = (Spettacoli.getSpettacoliFuturiFromFilm(films.get(i).getId())).getListaSpettacoli();
                               for(int k=0; k<spett_per_film.size(); k++){
                                   Spettacolo s = spett_per_film.get(k);
                                   Date data_spett = s.getData_ora().getTime();
                                   SimpleDateFormat ora = new SimpleDateFormat("hh:mm");
                                   out.println("<li class=\"item_spett\" data-sala=\""+s.getSala().getNome()+"\" data-id_spett=\""+s.getId()+"\"><a>"+s.getId()+" "+s.getFilm().getTitolo()+" "+ora.format(data_spett)+"</a></li>");
                               }
                            }
                        %>
                    </ul>
                </div>
                <div class="col s12 m10 ">
                    <div id="message1">Seleziona uno spettacolo per una panoramica delle prenotazioni</div>
                    <div class="demo container">
                        <div class="booking-details">
                            <div id="legend"></div>
                        </div>
                        <div class="front">SCREEN</div>					
                    </div>	
                </div>
            </div>
        </div>
        <div id="tab_incassi" class="container mtab">
            <table class="highlight responsive-table">
                <thead>
                    <tr>
                      <th data-field="id">ID</th>
                      <th data-field="name">Titolo</th>
                      <th data-field="name">Incasso</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        for(int i=0; i<incassi_film.size();i++){
                            out.println("<tr>");
                                out.println("<td>"+incassi_film.get(i).getId()+"</td>");
                                out.println("<td>"+incassi_film.get(i).getTitolo()+"</td>");
                                out.println("<td>"+incassi_film.get(i).getTotaleIncassi()+"&euro;</td>");
                            out.println("</tr>");
                        }
                    
                    %>
                </tbody>
            </table>
        </div>
        <!--Tab top clienti-->
        <div id="tab_clienti" class="container mtab">
            <table class="highlight responsive-table">
                <thead>
                    <tr>
                        <th data-field="id">Nome</th>
                        <th data-field="name">Email</th>
                        <th data-field="id">Nome</th>

                    </tr>
                </thead>
                <tbody>
                    <% 
                        for(int i=0; i<topten.size();i++){
                            out.println("<tr>");
                                out.println("<td>"+topten.get(i).getNome()+"</td>");
                                out.println("<td>"+topten.get(i).getEmail()+"</td>");
                                out.println("<td>"+topten.get(i).getTotalePagato()+"&euro;</td>");
                            out.println("</tr>");
                        }
                    
                    %>
                </tbody>
            </table>
        </div>
        
        <div id="tab_piùprenotati" class="container mtab">
            <div class="row">
                <div class="col s12 m2 lista_spett">
                    <ul>
                        <li class="item_sala" data-sala="1"><a>Sala 1</a></li>
                        <li class="item_sala" data-sala="2"><a>Sala 2</a></li>
                        <li class="item_sala" data-sala="3"><a>Sala 3</a></li>
                        <li class="item_sala" data-sala="4"><a>Sala 4</a></li>
                    </ul>
                </div>
                <div class="col s12 m10 ">
                    <div id="message2">Seleziona una sala</div>
                    <div class="mappa_piùprenotati container">
                        <div class="booking-details">
                            <div id="legend_piùprenotati"></div>
                        </div>
                        <div class="front">SCREEN</div>					
                    </div>	
                </div>
            </div>

        </div>
        <div id="tab_prenotazioni" class="container mtab">
            <h4>Annulla prenotazione</h4>
            <br>
            <form>
                <div class="row">
                    <div class="input-field col s10 center">
                        <input id="cerca_user" type="email" class="validate" autocomplete="off">
                        <label for="cerca_user">Email utente</label>
                    </div>
                    <div class="col s2 center">
                        <div class="btn" id="btn_find"><i class="material-icons">search</i></div>
                    </div>
                </div>
            </form>
            <br>
            <div class="container_acquisti">
                
            </div>
        </div> 
        

    </body>
	 
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script> 
    <script type="text/javascript" src="js/amministrazione.js"></script> 
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
  </html>