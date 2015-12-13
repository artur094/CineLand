    <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.SQLException"%>
<%@page import="GestioneClassi.Spettacoli"%>
<%@page import="ClassiDB.Utente"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ClassiDB.Film"%>
<%@page import="GestioneClassi.Films"%>
<%@page import="ClassiDB.Spettacolo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>  

<!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
                  <!--Import index.css-->
                  <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
                  <link type="text/css" rel="stylesheet" href="css/index.css"  />

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <%!
            boolean privacy = false;
            Cookie[] cookies ;        
            List<Film> films;
            List<Spettacolo> spett_per_film;
        %>

        <%
            films = (Films.getFutureFilms()).getListaFilm();
        %>  
    </head>

    <body>
        <!-- Navigatio Bar -->
        <nav>
            <div class="nav-wrapper">
                <a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
                <a href="index.jsp" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                <ul class="right hide-on-med-and-down">
                    <li><a class="waves-effect waves-light btn"><i class="material-icons right"></i>Sign in/out</a></li>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="sale.jsp">Le nostre sale</a></li>
                    <li><a href="">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">
                    <li><a class=""><i class="material-icons right"></i>Sign in/out</a></li>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="sale.jsp">Le nostre sale</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
            </div>
        </nav>
        <!-- Main central content -->
        <div class="container" id="index_container">

            <%
                for(int i = 0; i < films.size(); i++){		
                out.println("<div class=\"card\">");
                out.println("<div class=\"card-image\">");
                out.println("<div class=\"rect-video\">");
                out.println("<video muted poster=\"img/locandine/" + films.get(i).getTitolo().replaceAll("\\s+","") +".jpg\">");
                out.println("<source src=\"\" type=\"video/mp4\">");
                out.println("</video>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"card-content\">");
                out.println("<span class=\"card-title activator grey-text text-darken-4\">"+ films.get(i).getTitolo() +"<btn class=\"btn right\"><i class=\"material-icons\">shopping_cart</i></btn></span>");
                out.println("<div>");
                out.println("<span class=\"grey-text text-darken-4\"><a href=\"scheda_film.jsp?id="+films.get(i).getId()+"\">Scheda Film</a></span>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"card-reveal\">");
                out.println("<span class=\"card-title grey-text text-darken-4\">"+ films.get(i).getTitolo() +"<i class=\"material-icons right\">close</i></span>");
                //lista spettacoli
                spett_per_film = (Spettacoli.getSpettacoliFuturiFromFilm(films.get(i).getId())).getListaSpettacoli();
                for(int k = 0; k < spett_per_film.size(); k++){
                    Date dataSito = spett_per_film.get(k).getData_ora().getTime();
                    SimpleDateFormat giornata = new SimpleDateFormat("dd-MM-YY"); 
                    SimpleDateFormat ora = new SimpleDateFormat("hh mm");
                    
                    out.println("<p>Spettacolo il giorno "+giornata.format(dataSito)+ " alle ore "+ ora.format(dataSito) +"</p>");
                    out.println("<p>"+spett_per_film.get(k).getSala().getNome()+"</p>");
                    out.println("<a href=\"prenotazione.jsp?id="+spett_per_film.get(k).getId()+"class=\"btn right\"><i class=\"material-icons\">shopping_cart</i></a></span>");
                }
                out.println("</div>");
                out.println("</div>");
                }
            %>
        </div>
    </body>
    <footer class="page-footer">
        <div class="container">
            <div class="row">
                <div class="col l6 s12">
                    <h5 class="white-text">Dove siamo</h5>
                    <p class="grey-text text-lighten-4"><iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1388.7129075311707!2d11.020149873016887!3d45.88279612832539!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47820eec18ff6f33%3A0x12423f50a6d6989e!2sVia+alla+Moia%2C+38068+Rovereto+TN!5e0!3m2!1sit!2sit!4v1435409420597" width="400" height="250" frameborder="0" style="border:0; z-index:11;" allowfullscreen=""></iframe></p>
                </div>
                <div class="col l4 offset-l2 s12">
                    <h5 class="white-text">Con il prezioso contributo di:</h5>
                    <ul>
                        <li><a class="grey-text text-lighten-3" href="#!">Marco</a></li>
                        <li><a class="grey-text text-lighten-3" href="#!">Mattia</a></li>
                        <li><a class="grey-text text-lighten-3" href="#!">Paolo</a></li>
                        <li><a class="grey-text text-lighten-3" href="#!">Ivan</a></li>
                        <li><a class="grey-text text-lighten-3" href="#!">Luca</a></li>
                        <li><a class="grey-text text-lighten-3" href="#!">Linda</a></li>
                    </ul>
                </div>
            </div>
        </div>
     <div class="footer-copyright">
       <div class="container">
                                   © 2016 Copyright Cineland
           <a class="grey-text text-lighten-4 right" href="#!">More Links</a>
       </div>
     </div>
     </footer>
	  <!--Import jQuery before materialize.js-->
      <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script type="text/javascript" src="js/materialize.min.js"></script>
		<script type="text/javascript" src="js/master.js"></script>
      <script type="text/javascript" src="js/jquery-ui.min.js"></script>
      <script type="text/javascript" src="js/jquery.min.js"></script>
	   <script src="js/rect.js"></script>
  </html>