<%@page import="ClassiDB.Utente"%>
<%@page import="GestioneClassi.Films"%> 
<%@page import="ClassiDB.Film" %>
<!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import index.css-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/schedaFilm.css"  media="screen,projection"/>

        

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <%!
        boolean privacy = false;
        Cookie[] cookies ;        
        Utente user;
        Boolean sess = false;
    %>
    <% 
        int id_film = Integer.parseInt(request.getParameter("id"));
        Film film = new Film(id_film);
        user = (Utente)request.getSession().getAttribute("user");
        if(user == null){ //non è loggato
            sess = false;
        }else{
            sess = true;
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
                            out.println("<li id=\"login\"><a class=\"waves-effect waves-light modal-trigger btn\" data-target=\"form\">Sign in</a></li>");
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
                                <form class="col s12">
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
                                    <div class="col s12 center ">
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
        <div class="container" id="index_container">
            <div class="row">
                <div class="col s12 m6">
                    <div class="">
                        <div class="card-image">
                            <%out.println("<img src='img/locandine/" + film.getTitolo().replaceAll("\\s+","") + ".jpg' alt='"+ film.getTitolo() +"'/>");%>
                        </div>
                    </div>
                </div>
                <div class="col s12 m6">
                    <div>
                        <p class="left">Genere:<div class="chip"><%= film.getGenere()%></div></p>
                        <p>Durata: <span><%= film.getDurata()%></span></p>                 
                        <p>Trailer: <span><a href="<%= film.getUrl_trailer() %>">Guarda</a></span></p>           
                    </div>
                </div>   			
            </div>
        </div>
   
	<footer class="page-footer">
            <div class="footer-copyright">
                <div class="container">
                    Â© 2016 Copyright Cineland<a class="grey-text text-lighten-4 right" href="#!">More Links</a>
                </div>
            </div>
	</footer>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
        <script type="text/javascript" src="js/master.js"></script>
     </body>
  </html>