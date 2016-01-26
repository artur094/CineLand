<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<%--<jsp:useBean id="user" scope="session" class="ClassiDB.Utente"/>--%>

<!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import index.css-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/index.css"  />
        <link type="text/css" rel="stylesheet" href="css/slider.css" />

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <%!
            Utente user = null;
            boolean privacy = false;
            Cookie[] cookies ;        
            List<Film> films;
            List<Spettacolo> spett_per_film;
        %>
        <%
            user = (Utente)request.getSession().getAttribute("user");           
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
                    <%
                        if(user == null){
                               out.println("<li id=\"login\"><a class=\"waves-effect waves-light modal-trigger btn\" data-target=\"form\">Sign in</a></li>");
                        }else{
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
                        }
                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">
                    <%
                        if(user != null){
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
               
        <div class="slider-container">  <!-- dimensione   width: 100%;  height: 400px; -->
            <div class="slider">
                    <div class="slider-img" id="slider1"></div>    
                    <div class="slider-img" id="slider2"></div>    
                    <div class="slider-img" id="slider3"></div>    
                    <div class="slider-img" id="slider4"></div>        
                    <div class="slider-img" id="slider5"></div>        
                    <div class="slider-img" id="slider6"></div>            
            </div>
        </div>
        <!-- Main central content -->            
        <ul class="wrapper">  <!-- dimensione   width: 100%;  height: 400px; -->
            <%
                for(int i = 0; i < films.size(); i++){    
                out.println("<li class=\"card showcase\">");
                out.println("<div class=\"card-image\">");
                //out.println("<div class=\"rect-video img-big\"><video muted=\"\" poster=\"img/Slider/"+films.get(i).getTitolo().replaceAll("\\s+","") +".jpg\"><source src=\"video/"+films.get(i).getTitolo().replaceAll("\\s+","")+".mp4\" type=\"video/mp4\"></video></div>");
                out.println("<img class=\"img-small\" src=\"img/locandine/" + films.get(i).getTitolo().replaceAll("\\s+","") +".jpg\">");
                //out.println("<img class=\"img-big\" src=\"img/Slider/" + films.get(i).getTitolo().replaceAll("\\s+","") +".jpg\">");
                out.println("<div class=\"rect-video img-big\"><video width=\"580px\" height=\"380px\" muted=\"\" poster=\"img/Slider/"+films.get(i).getTitolo().replaceAll("\\s+","") +".jpg\"><source src=\"video/"+films.get(i).getTitolo().replaceAll("\\s+","")+".mp4\" type=\"video/mp4\"></video></div>");
                out.println("</div>");
                out.println("<div class=\"card-content\">");
                out.println("<span class=\"card-title activator grey-text text-darken-4\">"+ films.get(i).getTitolo() +"<a class=\"btn right activator\"><i class=\"material-icons\">shopping_cart</i></a></span>");
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
                    out.println("<p class=\"orarioSpett\"><i class=\"tiny material-icons crono\">query_builder</i>"+ora.format(data_spett)+" Sala: "+spett_per_film.get(k).getSala().getNome()+"");
                    out.println("<a href=\"prenotazione.jsp?id="+spett_per_film.get(k).getId()+"&x?sala="+spett_per_film.get(k).getSala().getNome()+"\" class=\"btn right\"><i class=\"material-icons\">shopping_cart</i></a></p>");
                }
                out.println("</div>");
                out.println("</li>");
                }
            %>                    
        </ul>  <!-- end slider-->


        <!-- Modal Structure -->
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
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
    <script src="js/rect.js"></script>
    <script src="js/slider2.js"></script>
  </html>
