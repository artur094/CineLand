<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ClassiDB.Film"%>
<%@page import="ClassiDB.Spettacolo"%>
<%@page import="ClassiDB.Utente"%>
<%@page import="GestioneClassi.Spettacoli"%>
<%@page import="GestioneClassi.Films"%>
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
        <link type="text/css" rel="stylesheet" href="css/slider.css" />

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <%!
            boolean privacy = false;
            Cookie[] cookies ;        
            List<Film> films;
            List<Spettacolo> spett_per_film;
            Utente user;
            Boolean sess = false;
        %>

        <%
            films = (Films.getFutureFilms()).getListaFilm();
            user = (Utente)request.getSession().getAttribute("user");
            if(user == null){ //non è loggato
                sess = false;
            }else{
                sess = true;
            }
        %>  
    </head>

    <body>
        <!-- Navigatio Bar -->
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
                            out.println("<li  id=\"logout\" class=\"off\"><div><a class='dropdown-button btn' href='#' data-activates='user'>Io</a>"
                                +"<ul id='user' class='dropdown-content'>"
                                +"<li><a href=\"#!\">Acquisti</a></li>"
                                +"<li class=\"divider\"></li>"
                                +"<li><a href=\"#!\">Profilo</a></li>"
                                +"<li><a id=\"btn_logout\">Log out</a></li>"
                                +"</ul></div></li>");
                            out.println("<li id=\"login\"><a class=\"waves-effect waves-light modal-trigger btn\" data-target=\"form\">Sign in</a></li>");
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
        <!-- Slider -->
        <div class="slider-container">  <!-- dimensione   width: 100%;  height: 400px; -->
            <div class="slider">
                    <div class="slider-img" id="slider1"></div>    
                    <div class="slider-img" id="slider2"></div>    
                    <div class="slider-img" id="slider3"></div>    
                    <div class="slider-img" id="slider4"></div>    
                    <div class="slider-img" id="slider5"></div> 
            </div>
            <div class="opacita"></div>
        </div>  <!-- end slider-->
                    
        <!-- Modal Structure -->
        <div id="form" class="modal">
            <div class="modal-content">
                    <div class="row">
                        <div class="col s12">
                            <ul class="tabs">
                                <li class="tab col s6"><a class="" href="#in">Sign IN</a></li>
                                <li class="tab col s6"><a href="#up">Sign UP</a></li>
                            </ul>
                        </div>
                        <div id="in" class="col s12">
                            <form class="col s12" action="login.js">
                                <div class="row">
                                  <div class="input-field col s12 offset-m3 m6  ">
                                        <input id="email" type="email" class="validate">
                                        <label for="email">Email</label>
                                  </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 offset-m3 m6 ">
                                        <input id="password" type="password" class="validate">
                                        <label for="password">Password</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s8 offset-m4 m4 ">
                                        <div class="btn" id="btn_login">Log in</div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div id="up" class="col s12">
                            <div class="row">
                                <form class="col s12">
                                    <div class="row">
                                        <div class="input-field col s12 m6">
                                            <input id="first_name" type="text" class="validate">
                                            <label for="first_name">First Name</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12 m6 offset-m3">
                                            <input id="email" type="email" class="validate">
                                            <label for="email">Email</label>
                                        </div>
                                    </div>
                                      <div class="row">
                                      <div class="input-field col s12 m6">
                                        <input id="password" type="password" class="validate">
                                        <label for="password">Password</label>
                                      </div>
                                    </div>
                                    <div class="row">
                                    <div class="col s8 offset-m4 m4 ">
                                        <div class="btn" id="btn_signup">Sign up</div>
                                    </div>
                                </div>
                                </form>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
        <!-- Main central content -->
        <div id="index_container">
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
                out.println("<span class=\"card-title activator grey-text text-darken-4\">"+ films.get(i).getTitolo() +"<a class=\"btn right\"><i class=\"material-icons\">theaters</i></a></span>");
                out.println("<div>");
                out.println("<span class=\"grey-text text-darken-4\"><a href=\"scheda_film.jsp?id="+films.get(i).getId()+"\">Scheda Film</a></span>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"card-reveal\">");
                out.println("<span class=\"card-title grey-text text-darken-4\">"+ films.get(i).getTitolo() +"<i class=\"material-icons right\">close</i></span>");
                //lista spettacoli
                spett_per_film = (Spettacoli.getSpettacoliFuturiFromFilm(films.get(i).getId())).getListaSpettacoli();
                Date ex_data_spett = spett_per_film.get(0).getData_ora().getTime();
                for(int k = 0; k < spett_per_film.size(); k++){
                    Date data_spett = spett_per_film.get(k).getData_ora().getTime();
                    SimpleDateFormat giornata = new SimpleDateFormat("EEEE dd/MM/YYYY"); 
                    SimpleDateFormat ora = new SimpleDateFormat("hh:mm");
                    if(k==0||(giornata.format(data_spett).toString()).compareTo(giornata.format(ex_data_spett).toString())!=0)
                    {
                        ex_data_spett = data_spett;
                        out.println("<p class=\"giornoData\"><span>"+giornata.format(data_spett)+ "</span></p>");
                    }
                    out.println("<p class=\"orarioSpett\"><i class=\"tiny material-icons crono\">query_builder</i>"+ora.format(data_spett)+"");
                    out.println("<a href=\"prenotazione.jsp?id="+spett_per_film.get(k).getId()+"\" class=\"btn right\"><i class=\"material-icons\">shopping_cart</i></a></p>");
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
    <script src="js/rect.js"></script>
    <script src="js/slider2.js"></script>
  </html>
