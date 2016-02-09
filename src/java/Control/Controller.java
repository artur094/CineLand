/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import ClassiDB.Film;
import ClassiDB.Prenotazione;
import ClassiDB.Sala;
import ClassiDB.Spettacolo;
import ClassiDB.Utente;
import Database.DBManager;
import GestioneClassi.Films;
import GestioneClassi.Prenotazioni;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerError;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
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
        String password_nuova = (String)request.getParameter("newpwd");
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
                catch(Exception ex)
                {
                    throw new ServletException(ex);
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
                    pdf.aggiungiPrenotazione(dbm.getPrenotazione(5));
                    pdf.aggiungiPrenotazione(dbm.getPrenotazione(7));
                    pdf.aggiungiPrenotazione(dbm.getPrenotazione(182));  //prenotazione nulla
                    pdf.aggiungiPrenotazione(dbm.getPrenotazione(1));
                    pdf.aggiungiPrenotazione(dbm.getPrenotazione(2));
                    pdf.aggiungiPrenotazione(dbm.getPrenotazione(3));

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
                    try{
                        user = Control.logIn(email, password);
                    }
                    catch(Exception ex)
                    {
                        throw new ServletException(ex);
                    }
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
                                throw new ServletException(ex);
                            }
                        }
                        if(user.getRuolo().equals("verificare"))
                            codice = 900;
                        else
                        {
                            request.getSession().setAttribute("user", user);
                        }
                        
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
                if(!Control.checkEmail(email))
                    throw new ServletException("Formato email sbagliato");
                user = (Utente)request.getSession().getAttribute("user");
                if(user == null)
                {
                    try{
                        if(Control.signUp(email,name, password, request.getRequestURL().toString()))
                        {
                            //REDIRECT
                        }
                        else 
                        {
                            error("signup");
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new ServletException(ex);
                    }
                }
                break;
            // Gestisco il logout, prima controllo se è loggato
            // Se si, tolgo l'attributo, altrimenti niente
            case "logout":
                Enumeration attrs = request.getSession().getAttributeNames();
                String attrName;
                while(attrs.hasMoreElements())
                {
                    attrName = (String)attrs.nextElement();
                    request.getSession().removeAttribute(attrName);
                }
                
                break;
            // Gestione del reset della password
            case "pswdimenticata":
                if(!Control.checkEmail(email)){
                    response.getWriter().write("0");
                    throw new ServletException("Errore email");
                }
                try{
                    if(Control.passwordDimenticata(email, request.getRequestURL().toString()))
                    {
                        response.getWriter().write("1");
                        
                        return;
                        // andata a buon fine, quindi redirezionare ad una pagina
                        // o nemmeno, comunque avvertendo che la email è stata inviata
                    }
                    else
                        response.getWriter().write("0");
                }
                catch(Exception ex)
                {
                    //throw new ServletException("Errore recupero password");
                    response.getWriter().write("0");
                }
                
                break;
            case "paginaresetpsw":
                //REDIRECT PAGINA PER CAMBIO PASS
                if(!Control.getEmailFromCode_ForgottenPassword(code).equals(""))
                {
                    request.getSession().setAttribute("codice", code);
                    RequestDispatcher disp = request.getRequestDispatcher("nuovaPassword.jsp");
                    disp.forward(request, response);
                }
                else
                {
                    RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
                    disp.forward(request, response);
                    return;
                    //throw new ServletException("Errore codice inserito per il reset password");
                }
                //return;
                break;     
            // da testare
            case "cambio_password":
                try{
                    user = (Utente)request.getSession().getAttribute("user");
                    Utente test = Control.logIn(user.getEmail(), password);
                    
                    if(test.getId() == user.getId() && test.getEmail().equals(user.getEmail()) && test.getNome().equals(user.getNome()))
                    {
                        if(Control.cambiaPassword(user.getEmail(),user.getNome(), password,password_nuova))
                        {
                            response.getWriter().write("1");
                        }
                        else
                        {
                            response.getWriter().write("0");
                        }
                    }
                    else
                        response.getWriter().write("0");
                }catch(Exception ex)
                {
                    response.getWriter().write("0");
                }
                
                break;
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
                    try{
                        code = (String)request.getSession().getAttribute("codice");
                        email = Control.getEmailFromCode_ForgottenPassword(code);
                        if(code == null || email.equals(""))
                        {
                            //errore
                            response.getWriter().write("1");
                        }
                        else if(Control.resetPassword(email, password, code))
                        {
                            response.getWriter().write("1");
                            //pass cambiata
                            /*RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                            dispatcher.forward(request, response);
                            return;*/
                        }
                        else
                        {
                            //ERRORE
                            response.getWriter().write("0");
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new ServletException(ex);
                    }
                }
                break;
            case "enable":
                try{
                    if(Control.enableAccount(email, code))
                    {
                        //account attivato
                        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                        dispatcher.forward(request, response);
                    }
                    else
                    {
                        //errore
                        throw new ServletException("Errore nell'abilitazione dell'account");
                    }
                }
                catch(Exception ex)
                {
                    throw new ServletException(ex);
                }
                break;
            case "prenota":
                //aggiungere pagamento
               
                try{
                    String titolare = (String)request.getParameter("titolare");
                    long numero_carta = Long.parseLong(request.getParameter("numero_carta"));
                    int cvv = Integer.parseInt(request.getParameter("cvv"));
                    int mese = Integer.parseInt(request.getParameter("mese"));
                    int anno = Integer.parseInt(request.getParameter("anno"));
                    
                    if(titolare==null || titolare == "" || 
                            mese < 1 || mese > 12 || 
                            anno < 2016 ||
                            String.valueOf(cvv).length()!=3 || String.valueOf(numero_carta).length()!=16)
                    {
                        response.getWriter().write("0");
                        return;
                    }
                }
                catch(Exception ex)
                {
                    response.getWriter().write("0");
                    return;
                }
                
                
                user = (Utente)request.getSession().getAttribute("user");
                if(user != null)
                {
                    Integer id_spettacolo = Integer.parseInt(request.getParameter("spettacolo"));
                    try{
                       boolean esitoOperazione = Control.prenotaFilms(id_spettacolo, user, posti);
                       if(esitoOperazione){
                           response.getWriter().write("1");
                       }else{
                           response.getWriter().write("0");
                       }
                    }catch(Exception ex)
                    {
                        response.getWriter().write(ex.toString());
                        throw new ServletException(ex);
                    }
                }
                else
                {
                    // ERRORE
                    //throw new ServletException("Non sei loggato");
                }
                break;
            case "paga":
                break;
            
            case "creabuco":
                Admin admin = (Admin) request.getSession().getAttribute("admin");
                if(admin != null)
                {
                    try{
                        int ris = Control.creaBuchiSala(nome_sala, posti);
                    }catch(Exception ex)
                    {
                        throw new ServletException(ex);
                    }
                }
                else
                {
                    throw new ServletException("Accesso non autorizzato");
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
            case "admin_prenotazioni":
                try{
                    Admin amm = (Admin)request.getSession().getAttribute("admin");
                    if(amm!=null)
                    {
                        List<Prenotazione> listPren = amm.getPrenotazioniRimborsabili(email);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        
                        List<datiJSONPrenotazioni> tmp = new ArrayList<>();
                        for(Prenotazione p : listPren){
                            tmp.add(new datiJSONPrenotazioni(p.getSpettacolo().getId(), p.getSpettacolo().getFilm().getTitolo(), p.getSpettacolo().getData_ora(),p.getSala().getNome(),p.getPosto().getRiga(), p.getPosto().getColonna(),p.getPrezzo()));
                        }
                        
                        Serializer ser = new JsonSerializer();
                        Object result = ser.serialize(tmp.toArray());
                      
                        response.getWriter().write(result.toString());
                    }
                    
                }catch(Exception e)
                {
                    throw new ServletException(e);
                }
                break;
            case "annulla_prenotazione":
                Admin amm = (Admin)request.getSession().getAttribute("admin");
                if(amm!=null)
                {
                    int id_spett = Integer.parseInt(request.getParameter("id_spett"));
                    int riga = Integer.parseInt(request.getParameter("riga"));
                    int colonna = Integer.parseInt(request.getParameter("colonna"));
                    
                    
                    try
                    {
                        DBManager dbm = DBManager.getDBManager();
                        int id_utente = (dbm.getUtente(email)).getId();
                        Spettacolo s = dbm.getSpettacolo(id_spett);
                        int id_posto = dbm.getIDPosto(s.getSala().getId(), riga, colonna);
                        Prenotazione p = dbm.getPrenotazione(id_spett, id_utente, id_posto);
                        amm.rimborsaPrenotazione(p.getId());
                        response.getWriter().write("1");
                    }
                    catch(Exception ex)
                    {
                        response.getWriter().write("1");   
                        //throw new ServerException("Errore Server Interno");
                    }
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

    class datiJSONPrenotazioni {
       int id_spettacolo;
       String titolo_film;
       long data_ora_spettacolo;
       String nome_sala;
       int riga_posto;
       int colonna_posto;
       double prezzo;
       
        public datiJSONPrenotazioni() {}
        
       
        public datiJSONPrenotazioni(int id_spettacolo, String titolo_film, Calendar spettacolo, String nome_sala, int riga_posto, int colonna_posto, double prezzo) {
                        
            this.id_spettacolo = id_spettacolo;
            this.titolo_film = titolo_film;
            this.data_ora_spettacolo = spettacolo.getTimeInMillis();
            this.nome_sala = nome_sala;
            this.riga_posto = riga_posto;
            this.colonna_posto = colonna_posto;
            this.prezzo = prezzo;
        }

        public int getId_spettacolo() {
            return id_spettacolo;
        }

        public String getTitolo_film() {
            return titolo_film;
        }

        public long getData_ora_spettacolo() {
            return data_ora_spettacolo;
        }

        public String getNome_sala() {
            return nome_sala;
        }

        public int getRiga_posto() {
            return riga_posto;
        }

        public int getColonna_posto() {
            return colonna_posto;
        }

        public double getPrezzo() {
            return prezzo;
        }

        public void setId_spettacolo(int id_spettacolo) {
            this.id_spettacolo = id_spettacolo;
        }

        public void setTitolo_film(String titolo_film) {
            this.titolo_film = titolo_film;
        }

        public void setData_ora_spettacolo(long data_ora_spettacolo) {
            this.data_ora_spettacolo = data_ora_spettacolo;
        }

        public void setNome_sala(String nome_sala) {
            this.nome_sala = nome_sala;
        }

        public void setRiga_posto(int riga_posto) {
            this.riga_posto = riga_posto;
        }

        public void setColonna_posto(int colonna_posto) {
            this.colonna_posto = colonna_posto;
        }

        public void setPrezzo(double prezzo) {
            this.prezzo = prezzo;
        }
        
        
       
       
    }
    
}
