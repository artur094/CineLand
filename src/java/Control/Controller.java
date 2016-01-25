/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Film;
import ClassiDB.Sala;
import ClassiDB.Utente;
import Database.DBManager;
import GestioneClassi.Films;
import GestioneClassi.Prenotazioni;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

/**
 * Servlet principale del backend. Tutte le funzioni sono richiamate tramite richiesta GET o POST. I parametri accettati sono indicati nel sorgente.
 * @author ivanmorandi
 * @author Paolo
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
        String posti = (String)request.getParameter("posti");
        String nome_sala = (String)request.getParameter("sala");
        String code = (String)request.getParameter("codice");
        //Integer id_prenotazione = Integer.parseInt(request.getParameter("prenotazione"));
        
                
        
        // Operation (op) deve assumere uno dei seguenti valori:
        // login
        // signup
        // logout
        // enable
        // pswdimenticata
        // paginaresetpsw --> usata per redirezionare da email a controller
        // resetpsw
        // prenota
        // paga --> da cancellare
        // creabuco --> solo admin, posti dati in input: riga,colonna riga,colonna...
        // test
        // script
        // vettore_posti_sala
        // vettore_posti_occupati
        // admin_sala
        // admin_posti_prenotati
        
        switch(operation)
        {
            case "script":
                try{
                    int x = Integer.parseInt(request.getParameter("x"));
                    Control.script(x);
                }
                catch(SQLException ex){
                    try(PrintWriter out = response.getWriter()){
                        out.println("SQL Exception");
                    }
                }
                catch(ClassNotFoundException cl)
                {
                    try(PrintWriter out = response.getWriter()){
                        out.println("ClassNotFound Exception");
                    }
                }
                break;
            case "test_qrcode":
                QRCode qrcode = new QRCode("CINELAND");
                byte[] array = qrcode.getQrcode().toByteArray();
                response.setContentType("image/jpg");
                response.setContentLength(array.length);
                response.getOutputStream().write(array);
                
                break;
            case "test_qrcode_html":
                try (PrintWriter out = response.getWriter()) {
                    out.print("<img src='");
                    out.print(request.getRequestURL()+ "?op=test_qrcode");
                    out.print("' />");
                    
                }
                break;
            case "test_posti":
                //eliminare questo blocco case se funziona tutto correttamente
                try{
                DBManager d = DBManager.getDBManager();
                Sala s = d.getSala(1);
                response.getWriter().print(s.getVettorePostiSala()[5]);
                }catch(Exception e)
                {
                    System.out.println(e.toString());
                }
                break;
            case "test":
                try{
                    DBManager dbm = DBManager.getDBManager();
                    PdfBiglietto pdf = new PdfBiglietto(dbm.getPrenotazione(2));
                    response.setContentType("application/pdf");
                    pdf.costruisciPdf("Biglietto", response.getOutputStream());
                  
                    /*  Sala s = new Sala(1);
                    out.println("TOSTRING");
                    out.println(s.toString());
                    out.println("STRING[][]");
                    String[][] mappa = s.getStringMappa();
                    
                    for(int i = 0;i < mappa.length;i++)
                    {
                        for(int j=0;j<mappa[i].length; j++)
                        {
                            out.print(mappa[i][j]);
                        }
                        out.println();
                    }*/
                    
                    
                }catch(Exception e)
                {
                    System.out.println("DAMMIT");
                    System.out.println(e.getMessage());
                }
                break;
            case "test_pren":
                try{
                    Prenotazioni pr = new Prenotazioni();
                    pr = pr.getPrenotazioniUtente(0);
                    List<Film> lista = Films.getAllFilms().getListaFilm();
                }
                catch(Exception ex)
                {
                    
                }
            case "login":
                // Controllo per sicurezza, se è loggato
                // Se non lo è, controllo se email e pass sono giusti
                // Se si, nessun problema, altrimenti vado alla pagina di errore
                // Se è loggato, non faccio niente
                // Ritorna una pagina con un codice di errore (AJAX)
                // CODICI DI RITORNO:
                // ADMIN --> 920
                // LOGGATO --> 910
                // ERRORE --> 900
                int codice = 900;
                String nome ="";
                double credito = 0;
                
                response.setContentType("application/json");
                user = (Utente)request.getSession().getAttribute("user");
                
                if(user == null)
                {
                    user = Control.logIn(email, password);
                    
                    if(user != null)
                    {
                        codice = 910;
                        if(user.getRuolo().equals("admin"))
                        {
                            try{
                                request.getSession().setAttribute("admin", new Admin());
                                codice = 920;
                            }
                            catch(Exception ex)
                            {
                                RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                                dispatcher.forward(request, response);
                            }
                        }
                        if(user.getRuolo().equals("verificare"))
                            codice = 900;
                        request.getSession().setAttribute("user", user);
                        nome = user.getNome();
                        credito = user.getCredito();   
                    }
                }
                try (PrintWriter out = response.getWriter()) {
                    String json = "{ "+
                            "\"codice\": "+codice+", "+
                            "\"nome\": \""+nome+"\", "+
                            "\"credito\": "+credito+
                            "}";
                    out.println(json);
                }
                break;
            // Controllo per sicurezza, se è loggato
            // Se non lo è, provo a inserire mail, nome e password
            // Se tutto va bene, nessun problema, altrimenti vado alla pagina di errore
            // Se è loggato, non faccio niente
            case "signup":
                user = (Utente)request.getSession().getAttribute("user");
                if(user == null)
                {
                    if(Control.signUp(email,name, password, request.getRequestURL().toString()))
                    {
                        //REDIRECT
                    }
                    else 
                    {
                        error("signup");
                    }
                }
                break;
            // Gestisco il logout, prima controllo se è loggato
            // Se si, tolgo l'attributo, altrimenti niente
            case "logout":
                user = (Utente)request.getSession().getAttribute("user");
                if(user != null)
                {
                    request.getSession().removeAttribute("user");
                    Admin admin = (Admin)request.getSession().getAttribute("admin");
                    if(admin != null)
                        request.getSession().removeAttribute("admin");
                }
                break;
            // Gestione del reset della password
            case "pswdimenticata":
                if(Control.passwordDimenticata(email, request.getRequestURL().toString()))
                {
                    // andata a buon fine, quindi redirezionare ad una pagina
                    // o nemmeno, comunque avvertendo che la email è stata inviata
                }
                else
                    error("pswdimenticata");
                break;
            case "paginaresetpsw":
            {
                //REDIRECT PAGINA PER CAMBIO PASS
            }
            case "resetpsw":
                if(password == null)
                {
                    //ERRORE
                }
                else if(password == "")
                {
                    // ERRORE
                }
                else 
                {
                    if(Control.resetPassword(email, password, code))
                    {
                        //pass cambiata
                    }
                    else
                    {
                        //ERRORE
                    }
                }
                break;
            case "enable":
                if(Control.enableAccount(email, code))
                {
                    //account attivato
                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                    dispatcher.forward(request, response);
                }
                else
                {
                    //errore
                }
                break;
            case "prenota":
                user = (Utente)request.getSession().getAttribute("user");
                if(user != null)
                {
                    Integer id_spettacolo = Integer.parseInt(request.getParameter("spettacolo"));
                    Control.prenotaFilm(id_spettacolo, user.getId(), posti);
                }
                else
                {
                    //redirect to login
                }
            case "paga":
                break;
            
            case "creabuco":
                Admin admin = (Admin) request.getSession().getAttribute("admin");
                if(admin != null)
                {
                    int ris = Control.creaBuchiSala(nome_sala, posti);
                }
                break;
            
            case "vettore_posti_occupati":
                try{
                int id_spett = Integer.parseInt(request.getParameter("id_spett"));
                Sala s = Sala.getSalaBySpett(id_spett);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8"); 
                Serializer serializer = new JsonSerializer();
                Object jsonVettore = serializer.serialize(s.getVettorePostiOccupati());
                response.getWriter().write(jsonVettore.toString());
                }catch(Exception e){
                    System.out.println(e.toString());
                }
                break;
            
            case "vettore_posti_sala":
                try{
                int id_spett = Integer.parseInt(request.getParameter("id_spett"));
                Sala s = Sala.getSalaBySpett(id_spett);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8"); 
                Serializer serializer = new JsonSerializer();
                Object jsonMatrice = serializer.serialize(s.getVettorePostiSala());
                response.getWriter().write(jsonMatrice.toString());
                }catch(Exception e){
                    System.out.println(e.toString());
                }
                break;
                
            case "admin_postiprenotati":
                try{
                    int id_sala = Integer.parseInt(request.getParameter("id_sala"));
                    Admin amministratore = (Admin)request.getSession().getAttribute("admin");
                    if(amministratore!=null)
                    {
                        Sala s = amministratore.getSala(id_sala);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8"); 
                        Serializer serializer = new JsonSerializer();
                        Object jsonVettore = serializer.serialize(s.getVettorePostiOccupati());
                        response.getWriter().write(jsonVettore.toString());
                    }
                }catch(Exception e){
                    System.out.println(e.toString());
                }
                break;
            
            case "admin_sala":
                try{
                    int id_sala = Integer.parseInt(request.getParameter("id_sala"));
                    Admin amministratore = (Admin)request.getSession().getAttribute("admin");
                    if(amministratore!=null)
                    {
                        Sala s = amministratore.getSala(id_sala);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8"); 
                        Serializer serializer = new JsonSerializer();
                        Object jsonMatrice = serializer.serialize(s.getVettorePostiSala());
                        response.getWriter().write(jsonMatrice.toString());
                    }
                }catch(Exception e){
                    System.out.println(e.toString());
                }
                    break;
                default:
                    break;
        }
    }
    
    /**
     * Funzione per la gestione degli errori
     * @param error 
     */
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
