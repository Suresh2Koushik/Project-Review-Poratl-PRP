/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vishnu S Mani
 */
@WebServlet(urlPatterns = {"/readsubdoc"})
public class readsubdoc extends HttpServlet {

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
       final String JDBC_DRIVER="com.mysql.jdbc.Driver";
 final String DB_URL="jdbc:mysql://localhost:3306/18mis1016";
 final String USER="root";
 final String PASS="root";
 String reviewid = request.getParameter("subfile");
  try{
 Class.forName("com.mysql.jdbc.Driver");
 Connection conn=DriverManager.getConnection(DB_URL,USER,PASS);
 Statement stmt=conn.createStatement();
 ResultSet rs = stmt.executeQuery("select file from submission where idsubmission='"+reviewid+"';");
 if(rs != null) {
if(rs.next()) {
Blob b = rs.getBlob("file");
response.setContentType("application/pdf");
response.setContentLength( (int) b.length());
InputStream is = b.getBinaryStream();
OutputStream os = response.getOutputStream();
byte buf[] = new byte[(int) b.length()];
is.read(buf);
os.write(buf);
os.close();
}
}
 
 }catch(SQLException | ClassNotFoundException ex)
 {
 Logger.getLogger(readsubdoc.class.getName()).log(Level.SEVERE, null, ex);
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
