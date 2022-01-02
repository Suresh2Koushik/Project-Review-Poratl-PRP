/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
/**
 *
 * @author Vishnu S Mani
 */
@WebServlet(urlPatterns = {"/submit"})
@MultipartConfig(maxFileSize = 16177215) 
public class submit extends HttpServlet {

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
        final String JDBC_DRIVER="com.mysql.jdbc.Driver";
        final String DB_URL="jdbc:mysql://localhost:3306/18mis1016";
        final String USER="root";
        final String PASS="root";
        PrintWriter out = response.getWriter();
        String user = request.getParameter("user");
        out.println("before text");
        String text = request.getParameter("text");
        out.println("after text"+text+"space");
        String tab = request.getParameter("table");
        out.println("before file");
        Part filePart = request.getPart("file");
        out.println("after file");
        InputStream pdfFileBytes = null; 
        pdfFileBytes = filePart.getInputStream();
                try{
            Class.forName("com.mysql.jdbc.Driver");
 Connection conn=DriverManager.getConnection(DB_URL,USER,PASS);
 Statement stmt=conn.createStatement();
 ResultSet r = stmt.executeQuery("select name from student where idlogin='"+user+"'");
 r.next();
 String name = r.getString("name");
 String table= "task"+tab;
 int rs=0;
 out.println(text);
  if(pdfFileBytes==null){
      out.println("inside 2");
      String sql = "insert into submission (idsubmission,stuname,stuid,text) " //
                + " values (?,?,?,?) ";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, table);
        pstm.setString(2, name);
        pstm.setString(3, user);
        pstm.setString(4, text);
        rs = pstm.executeUpdate();
        pstm.close();
  }
  else if(text.isEmpty()){
       out.println("inside 3");
    String sql = "insert into submission (idsubmission,stuname,stuid,file) " //
                + " values (?,?,?,?) ";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, table);
        pstm.setString(2, name);
        pstm.setString(3, user);
        pstm.setBlob(4, pdfFileBytes);
        rs = pstm.executeUpdate();
        pstm.close();   
  }
   else
   {
     out.println("inside 1");
     String sql = "insert into submission (idsubmission,stuname,stuid,text,file) " //
                + " values (?,?,?,?,?) ";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, table);
        pstm.setString(2, name);
        pstm.setString(3, user);
        pstm.setString(4, text);
        pstm.setBlob(5, pdfFileBytes);
        rs = pstm.executeUpdate();
        pstm.close();
   }
   if(rs>0)
   {
   request.getRequestDispatcher("studentdash.jsp").forward(request, response);
   }
                }
catch(SQLException | ClassNotFoundException ex)
 {
 Logger.getLogger(submit.class.getName()).log(Level.SEVERE, null, ex);
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

