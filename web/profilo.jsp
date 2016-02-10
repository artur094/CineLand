<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="GestioneClassi.Spettacoli"%>
<%@page import="GestioneClassi.Films"%>
<%@page import="ClassiDB.Film"%>
<%@page import="java.util.List"%>
<%@page import="Control.Admin"%>
<%@page import="ClassiDB.Spettacolo"%>
<%@page import="ClassiDB.Sala"%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="sess_error.jsp"%>
<jsp:useBean id="user" scope="session" class="ClassiDB.Utente"/>

<%
        boolean privacy = false;
        Cookie[] cookies;
//        List<> topten;
//        List<Film> incassi_film;
//        List<Film> films;
//        List<Spettacolo> spett_per_film;      
        if(request.getSession().getAttribute("user") == null) //non è loggato
            throw new RuntimeException();
        
        Cookie cookie;
        int cookiePos=-1;
%>

<!DOCTYPE html>
  <html>
    <head>
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import local style-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/profilo.css"  media="screen,projection"/>
    </head>
    
    <%
        cookiePos = 0;
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
                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                    <%
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
                    %>
                </ul>
            </div>
        </nav>
        <main class="container_profilo">
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
        </main>
            
            
        <footer class="page-footer">
            <p>&copy; 2016 Cineland - via alla Moia 30 Rovereto (TN) - Tel. 0464 123123 - P.Iva 1234567890 &nbsp;|&nbsp; <a href="privacy.jsp">Privacy</a> &nbsp;&nbsp; <a href="cookies.jsp">Informativa cookies</a></p>
        </footer>
    </body>
	 
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
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
            
            $('#btn_cambiaPassword').on('click',function(){
                $.ajax({
                    type : 'POST',
                    url : 'Controller',           
                    data: {
                        op : "cambio_password",
                        pwd:$("#ex_password").val(),
                        newpwd:$("#new_password").val()
                    },
                    success:function (data) {
                        if(data==="1")
                        {
                            Materialize.toast('Password cambiata!', 4000);
                        }
                        else{
                            Materialize.toast('Errore cambio password!', 4000);
                        }
                    },
                    error: function(){
                            Materialize.toast('Errore cambio password!', 4000);
                    }
                });
            });
        });
    </script>
  </html>