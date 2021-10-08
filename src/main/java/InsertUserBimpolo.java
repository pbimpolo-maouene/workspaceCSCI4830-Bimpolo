
/**
 * @file InsertUserBimpolo.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertUserBimpolo")
public class InsertUserBimpolo extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertUserBimpolo() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userName = request.getParameter("userName");
      String email = request.getParameter("email");
      String phone = request.getParameter("phone");
      String address = request.getParameter("address");
      String book = request.getParameter("book");

      Connection connection = null;
      String insertSql = " INSERT INTO MyTableUsers (id,MYUSER,EMAIL,PHONE,ADDRESS,BOOK) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionBimpolo.getDBConnection(getServletContext());
         connection = DBConnectionBimpolo.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, userName);
         preparedStmt.setString(2, email);
         preparedStmt.setString(3, phone);
         preparedStmt.setString(4, address);
         preparedStmt.setString(5, book);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>User Name</b>: " + userName + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "  <li><b>Phone</b>: " + phone + "\n" + //
            "  <li><b>Address</b>: " + address + "\n" + //
            "  <li><b>Book</b>: " + book + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-T3-1006-Bimpolo/search_BookBimpolo.html>Search Book</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/search_UserBimpolo.html>Search User</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/searchBookInUser.html>Search Book Rent</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/insert_BookBimpolo.html>Insert Book</a> <br>");
      out.println("<a href=/webproject-T3-1006-Bimpolo/insert_UserBimpolo.html>Insert User</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
