<%@page import="ClassiDB.Utente"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="ClassiDB.Spettacolo"%>
<%@page import="GestioneClassi.Spettacoli"%>
<%@page import="ClassiDB.Film"%>
<%@page import="ClassiDB.Sala"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="ClassiDB.Posto"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="sess_error.jsp"%>



<%!
    boolean privacy = false;
    Cookie[] cookies;        
    Spettacolo spett;
    Cookie cookie;
    int cookiePos=-1;
%>
<%
    Utente user = (Utente)request.getSession().getAttribute("user");
    int id_spettacolo = Integer.parseInt(request.getParameter("id"));    
    spett = new Spettacolo(id_spettacolo);
    if(request.getSession().getAttribute("user") == null) //non è loggato
        throw new RuntimeException();
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
<!DOCTYPE html>
<html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import index.css-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/prenotazione.css"/>
        <link type="text/css" rel="stylesheet" href="css/seats.css"/>
    </head>
    <body>
        <%
            if(cookies[cookiePos].getValue().compareTo("true")!=0)
            {
                out.println("<div class=\"divCookies\">Informazione importante sui cookie. Utilizzando questo sito acconsenti all'uso dei cookie in conformità alla nostra <a href=\"cookies.jsp\">Politica sui cookies</a>. <span class=\"btnCookies btn\">Accetto</span></div>");
            }
        %>
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
        <div id="list_con" class="container">
            <div class="row">
                <div class="col s12" id="id_spett" data-id="<%out.println(id_spettacolo);%>">
                    <h4><%out.println(spett.getFilm().getTitolo());%></h4>
                </div>
            </div>
            <div class="row">
                <div class="col s12 m4">
                    <div class="card-image">
                        <%out.println("<img src='img/locandine/" + spett.getFilm().getTitolo().replaceAll("\\s+","") + ".jpg' alt='"+ spett.getFilm().getTitolo() +"'/>");%>
                    </div>
                </div>
                <div class="col s12 m8">
                    <div class="row">
                        <div class="col s12"><p>Orario: <span id="exnow"><%out.println((new SimpleDateFormat("hh:mm - dd/MM/yyyy").format(spett.getData_ora().getTime())).toString());%></span></p></div>
                    </div>
                    <div class="row">
                        <div class="col s12"><p>Biglietti: <span id="counter">0</span></div>
                    </div>
                    <div class=" row">
                        <div class="col s12">
                            <p>Posti selezionati:</p>
                            <ul id="selected-seats"></ul>
                        </div>
                    </div>
                    <div class="row wrap-riduzioni">
                        <div class="col s12 m6 check_ridotti">
                            <input type="checkbox" id="ridotti" />
                            <label for="ridotti">*Seleziona ridotti</label>
                        </div>
                        <ul class="riduzioni col s12 m4">
                            <li>
                                <div class="campo">Studenti: </div>
                                <div class="input-field col">
                                    <select class="selStudenti">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                            <li>
                                <div class="campo">Militari: </div>
                                <div class="input-field col">
                                    <select class="selMilitari">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                            <li>
                                <div class="campo">Anziani: </div>
                                <div class="input-field col">
                                    <select class="selAnziani">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                            <li>
                                <div class="campo">Disabili: </div>
                                <div class="input-field col">
                                    <select class="selDisabili">
                                      <option value="0" selected>0</option>
                                    </select>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                  
            </div>
            <div id="btn_paga" class='btn  modal-trigger' href="#buy">
                <a>Compra:<b>$<span id="total">0</span></b></a>
            </div>

        </div>
        <div class="demo container">
            <div class="booking-details">
			<div id="legend"></div>
            </div>
            <div id="seat-map">
                    <div class="front">SCREEN</div>					
            </div>
		
        </div>	

        <div class="container" id="leggenda">
                <blockquote>
                    *Studente All'entrata sarà richiesta la tessera universitaria<br>
                    *Mlitare All'entrata sarà richiesta la tessera dell'arma di appartenenza<br>
                    *Anziano Vengono considerati in questa categoria tutti coloro >65anni <br>
                </blockquote>
        </div>
                    
        <!-- Modal Structure -->
        <div id="buy" class="modal">
            <fieldset class="form-field options" id="payment-method">
                <div class="row">
                    <h5>Paga con</h5>
                </div>
                <label>
                    <ul class="creditcards">
                        <li class="list-item">
                            <input class="with-gap" name="creditcard" type="radio" id="visa" checked />
                            <label for="visa">
                                <img alt="Visa" height="34" src="img/assets/icon-visa.svg" width="50">
                            </label>
                        </li>
                        <li>
                            <input class="with-gap" name="creditcard" type="radio" id="mastercard" checked />
                            <label for="mastercard">
                                <img alt="Mastercard" height="34" src="img/assets/icon-mastercard.svg" width="50">
                            </label>
                        </li>                        
                    </ul>
                </label>
                <div class="row">
                    <h5>Informazioni carta di credito</h5>
                </div>
                <div class="row">
                    <div class="input-field col s12 m6">
                        <input id="first_name" type="text" class="validate">
                        <label for="first_name">Nome titolare carta</label>
                    </div>
                    <div class="input-field col s12 m6">
                        <input id="number_card" type="text" class="validate">
                        <label for="number_card">Numero carta</label>
                    </div>
                </div> 
                <div class="row">
                    <div class="input-field col s12 m2">
                        <input id="cvv" type="text" class="validate">
                        <label for="cvv">CVV</label>
                    </div>
                    <div class="input-field col s12 m2">
                        <input id="scadenzaMese" type="text" class="validate">
                        <label for="scadenzaMese">Mese</label>
                    </div>
                    <div class="input-field col s12 m2">
                        <input id="scadenzaAnno" type="text" class="validate">
                        <label for="scadenzaAnno">Anno</label>
                    </div>
                </div>
                <div id="btn_conferma" class="btn">
                    <a>CONFERMA</a>
                </div>
            </fieldset>
            <div class="waiting">
                <div>
                    <span class="balls">&nbsp;</span><span class="balls">&nbsp;</span><span class="balls">&nbsp;</span><span class="balls">&nbsp;</span>
                </div>
            </div>
            <div class="msgPagamento"></div>
            <div class="msgErrore"></div>
        </div>
        
        <footer class="page-footer">
            <p>&copy; 2016 Cineland - via alla Moia 30 Rovereto (TN) - Tel. 0464 123123 - P.Iva 1234567890 &nbsp;|&nbsp; <a href="privacy.jsp">Privacy</a> &nbsp;&nbsp; <a href="cookies.jsp">Informativa cookies</a></p>
        </footer>
    </body>
    <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
    <script type="text/javascript" src="js/prenotazione.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script> 
</html>