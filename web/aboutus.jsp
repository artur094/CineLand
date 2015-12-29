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
    %>
    <%
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
                <li><a class=""><i class="material-icons right"></i>Sign in/out</a></li>
                <li><a href="index.jsp">Film</a></li>
                <li><a href="aboutus.jsp">About us</a></li>
            </ul>
        </div>
    </nav>
                    
    <div id="form" class="modal">
            <div class="modal-content">
                    <div class="row">
                        <div class="col s12">
                            <ul class="tabs">
                                <li class="tab col s6"><a class="active" href="#in">Sign IN</a></li>
                                <li class="tab col s6"><a href="#up">Sign UP</a></li>
                            </ul>
                        </div>
                        <div id="in" class="col s12">
                            <form class="col s12" action="login.js">
                                <div class="row">
                                  <div class="input-field col s12 offset-m3 m6  ">
                                        <input id="email" type="email" class="validate">
                                        <label for="email">Email</label>
                                  </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 offset-m3 m6 ">
                                        <input id="password" type="password" class="validate">
                                        <label for="password">Password</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s8 offset-m4 m4 ">
                                        <div class="btn" id="btn_login">Log in</div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div id="up" class="col s12">
                            <div class="row">
                                <form class="col s12">
                                    <div class="row">
                                        <div class="input-field col s12 m6">
                                            <input id="first_name" type="text" class="validate">
                                            <label for="first_name">First Name</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="last_name" type="text" class="validate">
                                            <label for="last_name">Last Name</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s12 m6 offset-m3">
                                            <input id="email" type="email" class="validate">
                                            <label for="email">Email</label>
                                        </div>
                                    </div>
                                      <div class="row">
                                      <div class="input-field col s12 m6">
                                        <input id="password" type="password" class="validate">
                                        <label for="password">Password</label>
                                      </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    <!-- Main central content -->
    <div id="index_container">
        <div class="row">
            <h5 class="white-text">Dove siamo</h5>
            <div class="col s12 m6">
                <p class="grey-text text-lighten-4"><iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1388.7129075311707!2d11.020149873016887!3d45.88279612832539!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47820eec18ff6f33%3A0x12423f50a6d6989e!2sVia+alla+Moia%2C+38068+Rovereto+TN!5e0!3m2!1sit!2sit!4v1435409420597" width="100%" height="500px" frameborder="0" style="border:0; z-index:11;" allowfullscreen=""></iframe></p>
            </div>
            <div class="col s12 m6">
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi varius facilisis mi quis lobortis. Praesent consectetur eros sit amet laoreet blandit. 
                Maecenas vulputate lectus vulputate velit viverra hendrerit. Nam interdum libero sed rhoncus pellentesque. Nam a posuere justo. Donec suscipit, est ac pretium congue, quam
                eros vehicula odio, quis lobortis eros orci eu mauris. Quisque tempor erat eros, vel rutrum orci mollis nec. Suspendisse metus lacus, ultricies non rutrum eget, finibus quis mi. 
                Etiam volutpat nulla nulla, et efficitur justo elementum et.
                Etiam maximus ipsum vulputate, faucibus leo a, eleifend metus. Nulla maximus ornare tempus. Donec volutpat lorem eget purus congue, ac hendrerit dolor rhoncus. Nullam tempus 
                nulla eu efficitur
                elementum. Nam lacinia ante at nisl feugiat rutrum. Nullam feugiat, felis eget interdum laoreet, est felis accumsan ex, id iaculis sapien ligula eget lectus. Sed a molestie turpis, 
                ac bibendum eros. Cras convallis nisi vel nulla ornare suscipit. Maecenas aliquet pretium ipsum sit amet scelerisque. Donec ipsum leo, aliquet nec lorem nec, ornare porta metus. 
                In et turpis erat. Nulla facilisi. Phasellus efficitur tortor viverra malesuada iaculis. Nulla sodales mi ornare massa tristique, a facilisis tortor eleifend. Nulla facilisi.
                Etiam diam magna, facilisis eget dictum accumsan, euismod et mi. Ut lobortis nibh ac enim blandit lacinia. Morbi diam tortor, ullamcorper at neque in, fermentum suscipit est. 
                Ut sollicitudin ante tortor. Phasellus id finibus tellus. Phasellus egestas nibh in mauris feugiat, a viverra magna rutrum. Proin condimentum lectus eu turpis viverra hendrerit. 
                Nam fringilla magna a odio placerat, sit amet fringilla turpis consectetur. Aenean vulputate congue tortor, vitae commodo nibh imperdiet sodales. Sed commodo sem id efficitur 
                . Nam viverra ligula nec quam laoreet elementum. Curabitur a elit diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vivamus elementum 
                est accumsan, ultricies elit id, tristique est.
                Quisque at sagittis magna, id bibendum magna. Nulla vitae fermentum erat, sed viverra quam. Pellentesque purus nisi, consequat nec lorem ultrices, consequat pretium risus. Vivamus
                sagittis nunc urna, non vulputate lorem tempor blandit. Vestibulum odio felis, congue vel efficitur vitae, accumsan eget enim. Ut vel mollis mauris, eu rhoncus ligula. 
                Sed ac eros sit amet mi condimentum dignissim vel sit amet urna. In vitae justo velit. Fusce bibendum velit metus, finibus laoreet nisl iaculis eget
                Suspendisse ut turpis sed massa fringilla auctor eu sit amet justo. Morbi quis consectetur nisi. Interdum et malesuada fames ac ante ipsum primis in faucibus.</p>
            </div>
        </div>
        
        <h5 class="white-text">Contatti telefonici</h5>            
        <div class="row">
            <div class="col l4 offset-l2 s12">
                <ul>
                    <li><a class="grey-text text-lighten-3" href="#!">Ufficio: 000202022 </a></li>
                    <li><a class="grey-text text-lighten-3" href="#!">Assistenza: 21212121</a></li>
                </ul>
          </div>
        </div>

    </div>
    </body>
	 
          
	 <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize/materialize.min.js"></script>
        <script type="text/javascript" src="js/master.js"></script>
	 
  </html>