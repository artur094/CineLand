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
<!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import local style-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>

        <link type="text/css" rel="stylesheet" href="css/profilo.css"  media="screen,projection"/>

      <!--Let browser know website is optimized for mobile-->
      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <%!
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
            sess = false;
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
                    <div class="container_profilo">
                        <h3>Riepilogo profilo</h3>
                        <br>
                        <table>
                        <% 
                            out.println("<tr><td>Nome:</td><td>"+user.getNome()+"</td></tr>");
                            out.println("<tr><td>Email:</td><td>"+user.getEmail()+"</td></tr>");
                            out.println("<tr><td>Saldo rimborso:</td><td>"+user.getCredito()+"&euro;</td></tr>");
                        %>
                        </table>
                        <hr>
                        <br>
                        <h3>Cambio password</h3>
                        <br>
                        <form action="Controller">
                            <input type="hidden" value="resetpsw" name="op"/>
                            <div class="row">
                                  <div class="input-field">
                                        <input id="ex_password" type="password" class="validate pass">
                                        <label for="ex_password">Vecchia password</label>
                                  </div>
                            </div>
                            <div class="row">
                                  <div class="input-field">
                                        <input id="new_password" type="password" class="pass validate">
                                        <label for="new_password">Nuova password</label>
                                  </div>
                            </div>
                            <div class="row">
                                  <div class="input-field">
                                        <input id="conf_password" type="password" class="pass">
                                        <label for="conf_password">Conferma nuova password</label>
                                  </div>
                            </div>
                            <div class="row">
                                <div class="col s12">
                                    <input type="button" class="btn" id="btn_cambiaPassword" disabled="disabled" value="Cambia password"/>
                                </div>
                            </div>
                        </form>
                    </div>
    </body>
	 
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script> 
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
    <script>
        $(document).ready(function(){
            $(".pass").keyup(function() {              
                if ($("#new_password").val() !== $("#conf_password").val()){
                    $("#conf_password").removeClass("valid");        
                    $("#conf_password").addClass("invalid");
                } 
                else{
                    if($("#new_password").val().length > 0){
                        $("#conf_password").removeClass("invalid");
                        $("#conf_password").addClass("valid");
                    }
                }
                if($("#ex_password").val().length > 0 && $("#new_password").val() === $("#conf_password").val() && $("#new_password").val().length > 0)
                {
                    $('#btn_cambiaPassword').removeAttr("disabled");
                }
                else
                {
                    $('#btn_cambiaPassword').prop("disabled", true);
                }
            });
        });
    </script>
  </html>