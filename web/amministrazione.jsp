  <!DOCTYPE html>
  <html>
    <head>
        <!--Import Google Icon Font-->
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize/materialize.min.css"  media="screen,projection"/>
        <!--Import local style-->
        <link type="text/css" rel="stylesheet" href="css/master.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/amministrazione.css"  media="screen,projection"/>

      <!--Let browser know website is optimized for mobile-->
      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>

    <body>
		 <!-- Navigatio Bar -->
		 <nav>
			 <div class="nav-wrapper">
				<a href="index.jsp" class="brand-logo center" id="nav_logo"></a>
				<a href="index.jsp" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
				<ul class="right hide-on-med-and-down">
					<li><a class="btn" id="btn_logout">Log out</a></li>"
					<li><a href="index.jsp">Film</a></li>
					<li><a href="sale.jsp">Le nostre sale</a></li>
				  	<li><a href="">About us</a></li>
				</ul>
				<ul class="side-nav" id="mobile-demo">
					<li><a id="btn_logout">Log out</a></li>"
					<li><a href="index.jsp">Film</a></li>
					<li><a href="sale.jsp">Le nostre sale</a></li>
					<li><a href="aboutus.jsp">About us</a></li>
				</ul>
			 </div>
  		</nav>
		 <div class="row">
			 <div class="col s12">
				<ul class="tabs">
				  <li class="tab col s3"><a class="active" href="#tab_programmazione">Programmazione</a></li>
				  <li class="tab col s3"><a href="#tab_incassi">Incassi film</a></li>
				  <li class="tab col s3 "><a href="#tab_clienti">Top clienti</a></li>
				  <li class="tab col s3"><a href="#tab_prenotazioni">Annulla prenotazione</a></li>
				</ul>
			 </div>
		 </div>	 
			 <div id="tab_programmazione" class="container mtab">Programmazione</div>
			 <div id="tab_incassi" class="container mtab">Incassi film</div>
			 <div id="tab_clienti" class="container mtab">Top clienti</div>
			 <div id="tab_prenotazioni" class="container mtab">Annulla prenotazione</div>   
    </body>
	 
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
    <script type="text/javascript" src="js/master.js"></script>
  </html>