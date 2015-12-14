/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Film;
import ClassiDB.Spettacolo;
import ClassiDB.Utente;
import GestioneClassi.Films;
import GestioneClassi.Spettacoli;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ivanmorandi
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Utente user;
        String operation = (String)request.getParameter("op");
        String email = (String)request.getParameter("email");
        String password = (String)request.getParameter("pwd");
        String name = (String)request.getParameter("name");
        Integer id_spettacolo = Integer.parseInt(request.getParameter("prenotazione"));
        
                
        
        // Operation deve assumere uno dei seguenti valori:
        // login
        // signup
        // logout
        // resetpsw
        // prenota
        // paga
        
        switch(operation)
        {
            case "login":
                // Controllo per sicurezza, se è loggato
                // Se non lo è, controllo se email e pass sono giusti
                // Se si, nessun problema, altrimenti vado alla pagina di errore
                // Se è loggato, non faccio niente
                user = (Utente)request.getSession().getAttribute("user");
                if(user != null)
                {
                    user = Control.logIn(email, password);
                    if(user == null)
                    {
                        error("login");
                        return;
                    }
                    else if(user.getRuolo().equals("admin"))
                    {
                        try{
                            request.getSession().setAttribute("admin", new Admin());
                        }
                        catch(SQLException ex)
                        {
                            //redirect to an error page
                        }
                        catch(ClassNotFoundException ex)
                        {
                            //redirect to an error page
                        }
                    }
                    request.getSession().setAttribute("user", user);
                }
                break;
            // Controllo per sicurezza, se è loggato
            // Se non lo è, provo a inserire mail, nome e password
            // Se tutto va bene, nessun problema, altrimenti vado alla pagina di errore
            // Se è loggato, non faccio niente
            case "signup":
                user = (Utente)request.getSession().getAttribute("user");
                if(user != null)
                {
                    user = Control.signUp(email,name, password);
                    if(user == null)
                    {
                        error("signup");
                        return;
                    }
                    request.getSession().setAttribute("user", user);
                }
                break;
            // Gestisco il logout, prima controllo se è loggato
            // Se si, tolgo l'attributo, altrimenti niente
            case "logout":
                user = (Utente)request.getSession().getAttribute("user");
                if(user != null)
                {
                    request.getSession().removeAttribute("user");
                }
                break;
            // Gestione del reset della password
            case "resetpsw":
                if(Control.resetPassword(email))
                {
                    // andata a buon fine, quindi redirezionare ad una pagina
                    // o nemmeno, comunque avvertendo che la email è stata inviata
                }
                else
                    error("resetpwd");
                break;
            case "prenota":
                user = (Utente)request.getSession().getAttribute("user");
                if(user != null)
                {
                    
                }
                else
                {
                    //redirect to login
                }
                    
            default:
                break;
        }
    }
    
    protected void error(String error)
    {
        switch(error)
        {
            case "login":
                // Redirezionare ad una pagina, segnalando l'errore di login
                break;
            case "signup":
                // Redirezionare ad una pagina, segnalando l'errore di registrazione
                break;
            case "resetpwd":
                // Redirezionare ad una pagina, segnalando che l'email non esiste nel DB
                break;
            default:
                // Redirezionare alla pagina di errore, segnalando errore generico
                break;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
