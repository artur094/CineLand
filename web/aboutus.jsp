<%@page import="ClassiDB.Utente"%>
<%@page import="ClassiDB.Film"%>
<!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import index.css-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/aboutus.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <%!
        boolean privacy = false;
        Cookie[] cookies ;        
        Utente user;
        Boolean sess = false;
        Cookie cookie;
        int cookiePos=-1;
    %>
    <%
            cookiePos = 0;
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
            if(cookies != null)
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
                        if(user!=null){
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
                                    <div class="col s12 center">
                                        <div class="btn" id="btn_signup">Sign up</div>
                                    </div>
                                </div>
                                </form>
                            </div>
                        </div>
            </div>
        </div>
    <!-- Main central content -->
    <div class="first-container container">
        <div class="row">
            <div class="col s12 m6">
                <p class="grey-text text-lighten-4"><iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1388.7129075311707!2d11.020149873016887!3d45.88279612832539!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47820eec18ff6f33%3A0x12423f50a6d6989e!2sVia+alla+Moia%2C+38068+Rovereto+TN!5e0!3m2!1sit!2sit!4v1435409420597" frameborder="0" style="border:0; z-index:11;" allowfullscreen=""></iframe></p>
            </div>
            <div class="col s12 m6">
                <div class="row">
                    <div class="col s12">
                        <h5 class="white-text">Contatti</h5>            
                        <ul>
                            <li class="grey-text text-lighten-3">Ufficio: +00 0202022</li>
                            <li class="grey-text text-lighten-3">Annullare prenotazioni: +21 212121</li>
                        </ul>    
                        <h5 class="white-text">Orario Apertura</h5>            
                        <ul>
                            <li class="grey-text text-lighten-3">Lunedì-Giovedì: 10.00-23.00</li>
                            <li class="grey-text text-lighten-3">Venerdì-Domenica: 16.00-02.30</li>
                        </ul>
                        <h5 class="white-text">Prezzi</h5>            
                        <ul>
                            <li class="grey-text text-lighten-3">Intero: 8&euro;</li>
                            <li class="grey-text text-lighten-3">Militare: 6&euro;</li>
                            <li class="grey-text text-lighten-3">Studente: 5&euro;</li>
                            <li class="grey-text text-lighten-3">Disabile: 5&euro;</li>
                            <li class="grey-text text-lighten-3">Anziano: 5&euro;</li>
                  </div>
                </div>
            </div>
        </div>
    </div>
    
    <main></main>
        <footer class="page-footer">
            <p>&copy; 2016 Cineland - via alla Moia 30 Rovereto (TN) - Tel. 0464 123123 - P.Iva 1234567890 &nbsp;|&nbsp; <a href="privacy.jsp">Privacy</a> &nbsp;&nbsp; <a href="cookies.jsp">Informativa cookies</a></p>
        </footer>
    </body>
	 
          
	 <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
        <script type="text/javascript" src="js/master.js"></script>
	 
  </html>