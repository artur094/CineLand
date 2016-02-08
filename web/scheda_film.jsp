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
    <%
        boolean privacy = false;
        Cookie[] cookies ;        
        Utente user;
        Boolean sess = false;
        Film film;
        int id_film;
        Cookie cookie;
        int cookiePos=-1;
    %>
    <% 
        id_film = Integer.parseInt(request.getParameter("id"));
        film = new Film(id_film);
        user = (Utente)request.getSession().getAttribute("user");
       
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
                                    +"<li><a href=\"profilo.jsp\">Profilo</a></li>"
                                    +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                    +"<li><a href=\"amministrazione.jsp\">Pannello</a></li>"
                                    +"<li class=\"divider\"></li>"
                                    +"<li><a id=\"btn_logout\">Log out</a></li>"
                                    +"</ul></div></li>");    
                            }else{
                                out.println("<li id=\"logout\"><div><a class='dropdown-button btn' href='#' data-activates='user'>"+user.getNome()+"</a>"
                                    +"<ul id='user' class='dropdown-content'>"
                                    +"<li><a href=\"profilo.jsp\">Profilo</a></li>"
                                    +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
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
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                    <%
                        if(sess){
                            if(user.getRuolo().equals("admin")){
                                out.println("<li><a class=\"center\" href=\"profilo.jsp\">"+user.getNome()+"</a></li>"
                                    +"<li class=\"divider\"></li>"
                                    +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                    +"<li><a href=\"amministrazione.jsp\">Amministrazione</a></li>"
                                    +"<li><a id=\"side_btn_logout\">Log out</a></li>");
                            }else{
                                out.println("<li><a class=\"center\"href=\"profilo.jsp\">"+user.getNome()+"</a></li>"
                                    +"<li class=\"divider\"></li>"
                                    +"<li><a href=\"acquisti.jsp\">Acquisti</a></li>"
                                    +"<li><a id=\"side_btn_logout\">Log out</a></li>");
                            }
                           
                        }else{
                            out.println("<li id=\"login\"><a class=\" modal-trigger\" data-target=\"form\">Sign in</a></li>");
                        }
                    %>
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
                                    <a href="recuperoPassword.jsp">Hai dimenticato la password?</a>
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
                        <p>Regista: <span><%= film.getRegista()%></span></p>
                        <p>Durata: <span><%= film.getDurata()%>'</span></p>                 
                        <p>Trailer: <span><a href="<%= film.getUrl_trailer() %>">Guarda</a></span></p>
                        <p>Trama: <span><%= film.getTrama()%></span></p>
                    </div>
                </div>   			
            </div>
        </div>
  
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
        <script type="text/javascript" src="js/master.js"></script>
        
        <main></main>
        <footer class="page-footer">
            <p>&copy; 2016 Cineland - via alla Moia 30 Rovereto (TN) - Tel. 0464 123123 - P.Iva 1234567890 &nbsp;|&nbsp; <a href="privacy.jsp">Privacy</a> &nbsp;&nbsp; <a href="cookies.jsp">Informativa cookies</a></p>
        </footer>
     </body>
  </html>