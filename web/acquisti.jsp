<%@page import="java.util.Calendar"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="GestioneClassi.Prenotazioni"%>
<%@page import="ClassiDB.Prenotazione"%>
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


<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="sess_error.jsp"%>

<!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import local style-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/acquisti.css"  media="screen,projection"/>

      <!--Let browser know website is optimized for mobile-->
      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <%
        boolean privacy = false;
        Cookie[] cookies ;        
        Utente user;
        List<Utente> topten;
        List<Film> incassi_film;
        List<Film> films;
        List<Spettacolo> spett_per_film;
        Boolean sess = false;
        Prenotazioni pr = new Prenotazioni();
        ArrayList<Prenotazione> pr_utente;
    %>


    <%
        user = (Utente)request.getSession().getAttribute("user");
        if(user == null) //non è loggato
            throw new RuntimeException();
           /* Admin admin = new Admin();
            topten = admin.getTopClienti();
            incassi_film = admin.getIncassiFilm();
            films = (Films.getFutureFilms()).getListaFilm();*/
            pr_utente = new ArrayList<>();
        pr_utente = pr.getPrenotazioniUtente(user.getId()).getListaPrenotazioni();
        //pr_utente.addAll(pr.getPrenotazioniUtente(user.getId()).getListaPrenotazioni());
    %>

    <body>
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
                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
                <ul class="side-nav" id="mobile-demo">
                    <%
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
                    %>
                    <li><a href="index.jsp">Film</a></li>
                    <li><a href="aboutus.jsp">About us</a></li>
                </ul>
            </div>
        </nav>
        <main class="container_acquisti">
            <h3>I tuoi acquisti</h3>
            <br>

            <% 
                Collections.sort(pr_utente, new Comparator<Prenotazione>(){
                    public int compare(Prenotazione p2, Prenotazione p1){
                        return (p1.getSpettacolo().getData_ora().getTime().toString()+p1.getSpettacolo().getFilm().getTitolo().toString()+p1.getId()).compareTo(p2.getSpettacolo().getData_ora().getTime().toString()+p2.getSpettacolo().getFilm().getTitolo().toString()+p2.getId());
                    }
                });
                int last_id=-1;
                //last_id = pr_utente.get(0).getSpettacolo().getId();
                boolean before_now=false;
                boolean after_now=false;
                int count_b=0;
                int count_a=0;
                for(int i=0; i < pr_utente.size(); i++)
                {
                    if(pr_utente.get(i).getSpettacolo().getData_ora().getTime().after(Calendar.getInstance().getTime()))
                    {
                        if(after_now==false)
                        {
                            //stampa in progress
                            after_now=true;
                            out.println("<h4>Da vedere</h4><ul class=\"collapsible\" data-collapsible=\"expandable\">");

                        }
                        if(pr_utente.get(i).getSpettacolo().getId()==last_id)
                        {
                            //non creo nuovo spoiler
                            // aggiungo 
                            out.println("<p><b>Sala</b> "+pr_utente.get(i).getSpettacolo().getSala().getNome()+" Fila "+pr_utente.get(i).getPosto().getRiga()+" Posto "+pr_utente.get(i).getPosto().getColonna()+" Costo: "+pr_utente.get(i).getPosto().getPrezzoPagato()+"&euro;</p>");
                        }
                        else
                        {
                            last_id = pr_utente.get(i).getSpettacolo().getId();
                            if(count_a==1)
                               out.println("</div></li>");
                            // creo nuovo spoiler e aggiungo lui stesso
                            out.println("<li><div class=\"collapsible-header\"><i class=\"material-icons\">receipt</i><b>"+pr_utente.get(i).getSpettacolo().getFilm().getTitolo()+"</b> <span class=\"material-icons\">schedule</span> "+(new SimpleDateFormat("hh:mm - dd MM yyyy").format(pr_utente.get(i).getSpettacolo().getData_ora().getTime())).toString()+"</div>");
                            out.println("<div class=\"collapsible-body\"><p><b>Sala</b> "+pr_utente.get(i).getSpettacolo().getSala().getNome()+" Fila "+pr_utente.get(i).getPosto().getRiga()+" Posto "+pr_utente.get(i).getPosto().getColonna()+" Costo: "+pr_utente.get(i).getPosto().getPrezzoPagato()+"&euro;</p>");
                            count_a=1;
                        }
                    }
                    else
                    {
                        if(before_now==false)
                        {
                            //stampa esauriti
                            before_now=true;
                            if(after_now)
                                out.println("</li></ul>");
                            out.println("<br><hr><br><h4>Visti</h4><ul class=\"collapsible\" data-collapsible=\"expandable\">");
                        }
                        if(pr_utente.get(i).getSpettacolo().getId()==last_id)
                        {
                            out.println("<p><b>Sala</b> "+pr_utente.get(i).getSpettacolo().getSala().getNome()+" Fila "+pr_utente.get(i).getPosto().getRiga()+" Posto "+pr_utente.get(i).getPosto().getColonna()+" Costo: "+pr_utente.get(i).getPosto().getPrezzoPagato()+"&euro;</p>");
                        }
                        else
                        {
                            last_id = pr_utente.get(i).getSpettacolo().getId();
                            if(count_b==1)
                               out.println("</div></li>");
                            // creo nuovo spoiler e aggiungo lui stesso
                            out.println("<li><div class=\"collapsible-header\"><i class=\"material-icons\">receipt</i><b>"+pr_utente.get(i).getSpettacolo().getFilm().getTitolo()+"</b> <span class=\"material-icons\">schedule</span> "+(new SimpleDateFormat("hh:mm - dd MM yyyy").format(pr_utente.get(i).getSpettacolo().getData_ora().getTime())).toString()+"</div>");
                            out.println("<div class=\"collapsible-body\"><p><b>Sala</b> "+pr_utente.get(i).getSpettacolo().getSala().getNome()+" Fila "+pr_utente.get(i).getPosto().getRiga()+" Posto "+pr_utente.get(i).getPosto().getColonna()+" Costo: "+pr_utente.get(i).getPosto().getPrezzoPagato()+"&euro;</p>");
                            count_b=1;
                        }
                    }
                }
                /*if(before_now==false)
                {
                    out.println("</li></ul>");
                }
                else
                {*/ 
                    //chiude ul sopra forse manca </div>
                    out.println("</div></li></ul>");
                //}

            %>
        </main>
        
        
        <footer class="page-footer">
            <p>&copy; 2016 Cineland - via alla Moia 30 Rovereto (TN) - Tel. 0464 123123 - P.Iva 1234567890 &nbsp;|&nbsp; <a href="privacy.jsp">Privacy</a> &nbsp;&nbsp; <a href="cookies.jsp">Informativa cookies</a></p>
        </footer>
    </body>
	 
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script> 
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
    <script>
        $(document).ready(function(){
            /*$('.collapsible').collapsible({
                //accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
            });*/
        });
    </script>
  </html>