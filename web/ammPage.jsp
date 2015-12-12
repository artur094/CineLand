<%-- 
    Document   : ammPage
    Created on : 10-dic-2015, 11.23.42
    Author     : Utente
--%>

<%@page import="ClassiDB.Utente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <link rel="stylesheet" href="css/login.css" type="text/css">
        <title>Amministrazione</title>
    </head>
    <body>
        <header>
            <div class="container-logo">
                <div class="logo" style="PADDING-TOP: 20px"> <a href="index.jsp" class="a_logo"></a></div>
            </div>
            <div class="login">
                <%
                    Utente u = (Utente) request.getSession().getAttribute("user");
                    if(u == null)
                        out.println("<div id='btnAccedi' class='btnAccedi'>Accedi / Iscriviti</div>");
                    else
                        out.println("<div id='btnAccedi' class='btnAccedi'>Disconnetti</div>");
                %>
            </div>
        </header>
    </body>
</html>
