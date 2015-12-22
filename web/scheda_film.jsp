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
        
        
        <!-- Main central content -->
        <div class="container" id="index_container">
            <div class="row">
              <div class="col s12 m5 offset-m5 ">
                <div class="card-panel">
                    <p>Genere: <span><%= film.getGenere()%></span></p>
                    <p>Durata: <span><%= film.getDurata()%></span></p>                 
                    <p>Trailer: <span><a href="<%= film.getUrl_trailer() %>">Guarda</a></span></p>           
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col s12 m8 offset-m2">
                <div class="card">
                    <div class="card-image">
                    <%out.println("<img src='img/locandine/" + film.getTitolo().replaceAll("\\s+","") + ".jpg' alt='"+ film.getTitolo() +"'/>");%>
                    <span class="card-title"><%= film.getTitolo() %></span>
                  </div>
                </div>
              </div>
            </div>
                 			
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
					Â© 2016 Copyright Cineland
            	<a class="grey-text text-lighten-4 right" href="#!">More Links</a>
            </div>
          </div>
	  </footer>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
        <script type="text/javascript" src="js/master.js"></script>
	 
  </html>